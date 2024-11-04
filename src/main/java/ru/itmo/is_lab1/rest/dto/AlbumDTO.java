package ru.itmo.is_lab1.rest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.itmo.is_lab1.domain.entity.Album;

@Data
public class AlbumDTO {
    private Integer id;

    @NotNull
    @NotEmpty
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Min(1)
    private Integer sales; //Значение поля должно быть больше 0

    public static AlbumDTO fromDomain(Album album){
        if (album == null) return null;
        AlbumDTO albumDTO = new AlbumDTO();
        albumDTO.setId(album.getId());
        albumDTO.setName(album.getName());
        albumDTO.setSales(album.getSales());
        return albumDTO;
    }

    public static Album toDomain(AlbumDTO albumDTO){
        if (albumDTO == null) return null;
        Album album = new Album();
        album.setId(albumDTO.getId());
        album.setName(albumDTO.getName());
        album.setSales(albumDTO.getSales());
        return album;
    }
}
