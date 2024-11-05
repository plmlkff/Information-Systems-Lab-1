package ru.itmo.is_lab1.rest.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.itmo.is_lab1.domain.entity.MusicBand;
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

    private List<MusicBandDTO> musicBands;

    public static UserDTO fromDomain(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(user.getLogin());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setMusicBands(user.getMusicBands().stream().map(MusicBandDTO::fromDomain).toList());
        return userDTO;
    }

    public static User toDomain(UserDTO userDTO){
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setMusicBands(userDTO.getMusicBands().stream().map(MusicBandDTO::toDomain).toList());
        return user;
    }
}
