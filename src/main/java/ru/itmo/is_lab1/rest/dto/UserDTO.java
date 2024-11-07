package ru.itmo.is_lab1.rest.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.itmo.is_lab1.domain.entity.User;
import ru.itmo.is_lab1.domain.entity.UserRole;

import java.util.List;

@Data
public class UserDTO {
    @NotNull
    private String login;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String token;

    public static UserDTO fromDomain(User user){
        if (user == null) return null;
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(user.getLogin());
        userDTO.setRole(user.getRole());
        userDTO.setToken(user.getToken());
        return userDTO;
    }

    public static User toDomain(UserDTO userDTO){
        if (userDTO == null) return null;
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setToken(userDTO.getToken());
        return user;
    }
}
