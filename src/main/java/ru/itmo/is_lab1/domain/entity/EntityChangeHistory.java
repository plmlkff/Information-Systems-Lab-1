package ru.itmo.is_lab1.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "entity_change_history")
@Data
public class EntityChangeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "login")
    @NotNull
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private OperationType operation;

    @Column(name = "music_band_id")
    @NotNull
    private Integer musicBandId;

    @Column(name = "time")
    @NotNull
    private Date time;

    public interface Fields {
        String USER = "user";
    }


    public enum OperationType{
        CREATE,
        DELETE,
        UPDATE
    }
}
