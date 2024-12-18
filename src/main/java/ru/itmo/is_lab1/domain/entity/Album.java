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
    private Integer id;

    @Column
    @NotNull
    @NotEmpty
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Column
    @Min(1)
    private Integer sales; //Значение поля должно быть больше 0

    public void merge(Album newAlbum){
        name = newAlbum.getName();
        sales = newAlbum.getSales();
    }
}
