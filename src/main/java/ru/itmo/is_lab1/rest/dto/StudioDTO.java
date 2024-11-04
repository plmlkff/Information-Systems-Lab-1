package ru.itmo.is_lab1.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.itmo.is_lab1.domain.entity.Studio;

@Data
public class StudioDTO {
    private Integer id;

    @NotNull
    private String address; //Поле не может быть null

    public static StudioDTO fromDomain(Studio studio){
        if (studio == null) return null;
        StudioDTO studioDTO = new StudioDTO();
        studioDTO.setId(studio.getId());
        studioDTO.setAddress(studio.getAddress());
        return studioDTO;
    }

    public static Studio toDomain(StudioDTO studioDTO){
        if (studioDTO == null) return null;
        Studio studio = new Studio();
        studio.setId(studioDTO.getId());
        studio.setAddress(studioDTO.address);
        return studio;
    }
}
