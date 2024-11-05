package ru.itmo.is_lab1.rest.controller;

import jakarta.inject.Inject;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is_lab1.domain.entity.*;
import ru.itmo.is_lab1.exceptions.domain.CanNotSaveEntityException;
import ru.itmo.is_lab1.rest.dto.UserDTO;
import ru.itmo.is_lab1.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.itmo.is_lab1.util.HttpResponse.*;

@BasicAuthenticationMechanismDefinition(
        realmName="${'test realm'}" // Doesn't need to be expression, just for example
)
@Path("/hello-world")
public class HelloResource {

    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {
        User user = new User();
        user.setRole(UserRole.ADMIN);
        user.setLogin("Pasha");
        user.setPassword("123");

        MusicBand musicBand = new MusicBand();
        musicBand.setName("ABC");
        Coordinates coordinates = new Coordinates();
        coordinates.setX(1);
        coordinates.setY(2);
        musicBand.setCoordinates(coordinates);
        musicBand.setCreationDate(LocalDate.now());
        musicBand.setGenre(MusicGenre.JAZZ);
        musicBand.setNumberOfParticipants(3);
        musicBand.setSinglesCount(3);
        musicBand.setDescription("Test");
        Album album = new Album();
        album.setName("Lol");
        album.setSales(5);
        musicBand.setBestAlbum(album);
        musicBand.setAlbumsCount(5);
        musicBand.setEstablishmentDate(LocalDateTime.now());
        Studio studio1 = new Studio(), studio2 = new Studio();
        studio1.setAddress("SPB");
        studio2.setAddress("TLT");
        musicBand.setStudio(List.of(studio1, studio2));

        musicBand.setOwner(user);
        user.setMusicBands(List.of(musicBand));
        try {
            user = userService.save(user);
            user.getMusicBands();
            return ok(UserDTO.fromDomain(user));
        } catch (CanNotSaveEntityException e){
            return error(e.getMessage());
        }
    }

}