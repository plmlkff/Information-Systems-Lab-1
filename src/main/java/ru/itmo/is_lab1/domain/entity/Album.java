package ru.itmo.is_lab1.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "album")
@Data
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotNull
    @NotEmpty
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Column
    @Min(1)
    private int sales; //Значение поля должно быть больше 0
}
