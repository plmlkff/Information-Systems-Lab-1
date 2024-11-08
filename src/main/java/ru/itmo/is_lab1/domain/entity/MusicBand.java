package ru.itmo.is_lab1.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "music_band")
@Data
public class MusicBand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    @Column
    @NotNull
    @NotEmpty
    private String name; //Поле не может быть null, Строка не может быть пустой

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates_id")
    @NotNull
    private Coordinates coordinates; //Поле не может быть null

    @Column(name = "creation_date")
    @NotNull
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private MusicGenre genre; //Поле может быть null

    @Column(name = "number_of_participants")
    @NotNull
    private Integer numberOfParticipants; //Поле может быть null, Значение поля должно быть больше 0

    @Column(name = "singles_count")
    @NotNull
    private int singlesCount; //Значение поля должно быть больше 0

    @Column
    @NotNull
    private String description; //Поле может быть null

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "album_id")
    @NotNull
    private Album bestAlbum; //Поле не может быть null

    @Column(name = "albums_count")
    @NotNull
    @Min(1)
    private Integer albumsCount; //Поле не может быть null, Значение поля должно быть больше 0

    @Column(name = "establishment_date")
    private LocalDateTime establishmentDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "studio_id")
    @NotNull
    private Studio studio; //Поле не может быть null

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "owner_login")
    private User owner;

    public void merge(MusicBand newMusicBand){
        name = newMusicBand.getName();
        coordinates.merge(newMusicBand.getCoordinates());
        creationDate = newMusicBand.getCreationDate();
        genre = newMusicBand.getGenre();
        numberOfParticipants = newMusicBand.getNumberOfParticipants();
        singlesCount = newMusicBand.getSinglesCount();
        description = newMusicBand.getDescription();
        bestAlbum.merge(newMusicBand.getBestAlbum());
        albumsCount = newMusicBand.getAlbumsCount();
        establishmentDate = newMusicBand.getEstablishmentDate();
        studio.merge(newMusicBand.getStudio());
    }

    public interface Attributes{
        String COORDINATES = "coordinates";
        String BEST_ALBUM = "bestAlbum";
        String STUDIO = "studio";
        String OWNER = "owner";
    }
}
