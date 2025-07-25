package org.example.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class UserDto {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
    @Min(value = 1)
    @Max(value = 100)
    private int age;
    @NotNull
    private LocalDateTime created_at;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;
        return email.equals(userDto.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
