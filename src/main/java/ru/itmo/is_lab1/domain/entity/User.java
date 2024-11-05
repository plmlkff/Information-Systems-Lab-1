package ru.itmo.is_lab1.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "user_table")
@Data
public class User {
    @Id
    private String login;

    @Column
    @NotNull
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ToString.Exclude
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MusicBand> musicBands;
}
