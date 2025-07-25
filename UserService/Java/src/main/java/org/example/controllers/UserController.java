package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dto.UserDto;
import org.example.dto.UserMapper;
import org.example.models.User;
import org.example.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final KafkaTemplate <String, String> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, KafkaTemplate<String, String> kafkaTemplate) {
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable int id) {
        Optional<User> user = userService.findById(id);
        if(user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserMapper.toDto(user.get()));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> findByEmail (@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UserMapper.toDto(user.get()));
    }

    @GetMapping
    public List<UserDto> findAll() {
        List<User> users = userService.findAll();
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users) {
            usersDto.add(UserMapper.toDto(user));
        }
        return usersDto;
    }

    @PostMapping
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        User savedUser = userService.save(user);
        if (savedUser != null) {
            logger.debug("User with id = {} saved successfully.", savedUser.getId());
        }
        kafkaTemplate.send("users.service.topic", "CREATED:" + savedUser.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update (@PathVariable int id, @Valid @RequestBody UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        // Чтобы не создался дубликат
        user.setId(id);
        User updatedUser = userService.update(user);
        if (updatedUser != null) {
            logger.debug("User with id = {} updated successfully.", updatedUser.getId());
        }
        return ResponseEntity.ok(UserMapper.toDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id)
    {
        Optional<User> user = userService.findById(id);
        if(!user.isEmpty()) {
            kafkaTemplate.send("users.service.topic", "DELETED:" + user.get().getEmail());
            userService.delete(user.get());
            logger.debug("User with id = {} was successfully deleted.", id);
        }
        return ResponseEntity.noContent().build();
    }
}
