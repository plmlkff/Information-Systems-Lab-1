package ru.itmo.is_lab1.rest.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.itmo.is_lab1.domain.entity.Coordinates;

@Data
public class CoordinatesDTO {
    private Integer id;

    @NotNull
    @Min(-144)
    private Integer x; //Значение поля должно быть больше -144, Поле не может быть null

    @Max(675)
    private Integer y; //Максимальное значение поля: 675

    public static CoordinatesDTO formDomain(Coordinates coordinates){
        if (coordinates == null) return null;
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
        coordinatesDTO.setId(coordinates.getId());
        coordinatesDTO.setX(coordinates.getX());
        coordinatesDTO.setY(coordinates.getY());
        return coordinatesDTO;
    }

    public static Coordinates toDomain(CoordinatesDTO coordinatesDTO){
        if (coordinatesDTO == null) return null;
        Coordinates coordinates = new Coordinates();
        coordinates.setId(coordinatesDTO.getId());
        coordinates.setX(coordinatesDTO.getX());
        coordinates.setY(coordinatesDTO.getY());
        return coordinates;
    }
}
