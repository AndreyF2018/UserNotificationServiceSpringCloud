package org.example.services;

import jakarta.transaction.Transactional;
import org.example.models.User;
import org.example.repos.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public Optional<User> findById(int id) {
        return userRepo.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Transactional
    public User save(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            String errorMessage = "Failed to save user. User with email + " + user.getEmail() + " already exists.";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        if (user.getEmail() == null || user.getName() == null) {
            String errorMessage = "Failed to save user. Email and name are required.";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        logger.debug("User with email = {} saved successfully.", user.getEmail());
        return userRepo.save(user);
    }

    @Transactional
    public User update(User user) {
        if (!userRepo.existsById(user.getId())) {
            String errorMessage = "Failed to update user. This user does not exist";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        if (user.getEmail() == null || user.getName() == null) {
            String errorMessage = "Failed to update user. Email and name are required.";
            logger.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
        return userRepo.save(user);
    }

    @Transactional
    public void delete(User user) {
        userRepo.delete(user);
    }
}
