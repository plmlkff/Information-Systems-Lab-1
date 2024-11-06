package ru.itmo.is_lab1.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.itmo.is_lab1.domain.entity.UserRole;

@Data
public class AuthDTO {
    @NotNull
    private String login;

    @NotNull
    private String password;

    private UserRole role;

    private String token;
}
