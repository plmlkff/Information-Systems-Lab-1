package ru.itmo.is_lab1.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "coordinates")
@Data
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotNull
    @Min(-144)
    private Integer x; //Значение поля должно быть больше -144, Поле не может быть null

    @Column
    @Max(675)
    private Integer y; //Максимальное значение поля: 675

    public void merge(Coordinates newCoordinates){
        x = newCoordinates.getX();
        y = newCoordinates.getY();
    }
}
