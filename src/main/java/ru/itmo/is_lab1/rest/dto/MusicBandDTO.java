package ru.itmo.is_lab1.rest.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.itmo.is_lab1.domain.entity.MusicBand;
import ru.itmo.is_lab1.domain.entity.MusicGenre;

import java.time.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class MusicBandDTO {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @NotNull
    @NotEmpty
    private String name; //Поле не может быть null, Строка не может быть пустой

    @NotNull
    private CoordinatesDTO coordinates; //Поле не может быть null

    @NotNull
    private long creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Enumerated(EnumType.STRING)
    @NotNull
    private MusicGenre genre; //Поле может быть null

    @NotNull
    private Integer numberOfParticipants; //Поле может быть null, Значение поля должно быть больше 0

    @NotNull
    private int singlesCount; //Значение поля должно быть больше 0

    @NotNull
    private String description; //Поле может быть null

    @NotNull
    private AlbumDTO bestAlbum; //Поле не может быть null

    @NotNull
    @Min(1)
    private Integer albumsCount; //Поле не может быть null, Значение поля должно быть больше 0

    @NotNull
    private long establishmentDate;

    @NotNull
    private StudioDTO studio; //Поле не может быть null

    private String ownerLogin;
    
    public static MusicBandDTO fromDomain(MusicBand musicBand){
        if (musicBand == null) return null;
        MusicBandDTO musicBandDTO = new MusicBandDTO();
        musicBandDTO.setId(musicBand.getId());
        musicBandDTO.setName(musicBand.getName());
        musicBandDTO.setCoordinates(CoordinatesDTO.formDomain(musicBand.getCoordinates()));
        musicBandDTO.setCreationDate(musicBand.getCreationDate().toEpochDay());
        musicBandDTO.setGenre(musicBand.getGenre());
        musicBandDTO.setNumberOfParticipants(musicBand.getNumberOfParticipants());
        musicBandDTO.setSinglesCount(musicBand.getSinglesCount());
        musicBandDTO.setDescription(musicBand.getDescription());
        musicBandDTO.setBestAlbum(AlbumDTO.fromDomain(musicBand.getBestAlbum()));
        musicBandDTO.setAlbumsCount(musicBand.getAlbumsCount());
        musicBandDTO.setEstablishmentDate(musicBand.getEstablishmentDate().toEpochSecond(ZoneOffset.UTC));
        musicBandDTO.setStudio(StudioDTO.fromDomain(musicBand.getStudio()));
        musicBandDTO.setOwnerLogin(musicBand.getOwner() == null ? null : musicBand.getOwner().getLogin());
        return musicBandDTO;
    }

    public static MusicBand toDomain(MusicBandDTO musicBandDTO){
        if (musicBandDTO == null) return null;
        MusicBand musicBand = new MusicBand();
        musicBand.setId(musicBandDTO.getId());
        musicBand.setName(musicBandDTO.getName());
        musicBand.setCoordinates(CoordinatesDTO.toDomain(musicBandDTO.getCoordinates()));
        musicBand.setCreationDate(LocalDate.ofEpochDay(musicBandDTO.getCreationDate()));
        musicBand.setGenre(musicBandDTO.getGenre());
        musicBand.setNumberOfParticipants(musicBandDTO.getNumberOfParticipants());
        musicBand.setSinglesCount(musicBandDTO.getSinglesCount());
        musicBand.setDescription(musicBandDTO.getDescription());
        musicBand.setBestAlbum(AlbumDTO.toDomain(musicBandDTO.getBestAlbum()));
        musicBand.setAlbumsCount(musicBandDTO.getAlbumsCount());
        musicBand.setEstablishmentDate(LocalDateTime.ofInstant(Instant.ofEpochSecond(musicBandDTO.getEstablishmentDate()), ZoneOffset.UTC));
        musicBand.setStudio(StudioDTO.toDomain(musicBandDTO.getStudio()));
        return musicBand;
    }
}
