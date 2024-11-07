package ru.itmo.is_lab1.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "person")
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

    @Column(name = "is_approved")
    private boolean isApproved;

    @ToString.Exclude
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MusicBand> musicBands;

    @Transient
    private String token;
}
