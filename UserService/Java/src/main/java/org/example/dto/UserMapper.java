package org.example.dto;

import org.example.models.User;

public class UserMapper {

    public static User toEntity (UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAge(userDto.getAge());
        user.setCreated_at(userDto.getCreated_at());
        return user;
    }

    public static UserDto toDto (User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setAge(user.getAge());
        userDto.setCreated_at(user.getCreated_at());
        return userDto;
    }
}
