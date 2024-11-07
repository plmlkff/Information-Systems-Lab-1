package ru.itmo.is_lab1.rest.controller;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is_lab1.domain.entity.MusicBand;
import ru.itmo.is_lab1.domain.filter.QueryFilter;
import ru.itmo.is_lab1.exceptions.domain.*;
import ru.itmo.is_lab1.rest.dto.MusicBandDTO;
import ru.itmo.is_lab1.security.filter.JWTFilter;
import ru.itmo.is_lab1.service.MusicBandService;

import static ru.itmo.is_lab1.util.HttpResponse.*;

@Path("/domain/musicband")
public class MusicBandController {
    @Inject
    private MusicBandService musicBandService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMusicBands(){
        try {
            var bands = musicBandService.getAll();
            var bandDTOs = bands.stream().map(MusicBandDTO::fromDomain).toList();
            return ok(bandDTOs);
        } catch (CanNotGetAllEntitiesException e) {
            return error(e.getMessage());
        }
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMusicBands(QueryFilter queryFilter){
        try {
            var bands = musicBandService.getAll(queryFilter);
            var bandDTOs = bands.stream().map(MusicBandDTO::fromDomain).toList();
            return ok(bandDTOs);
        } catch (CanNotGetAllEntitiesException e) {
            return error(e.getMessage());
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMusicBand(@PathParam("id") Integer id, @Context HttpServletRequest request){
        try{
            String login = (String)request.getAttribute(JWTFilter.LOGIN_ATTRIBUTE_NAME);
            musicBandService.deleteById(id, login);
            return ok(id);
        } catch (CanNotDeleteEntityException e) {
            return error(e.getMessage());
        }
    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMusicBand(MusicBandDTO musicBandDTO, @PathParam("id") Integer id, @Context HttpServletRequest request){
        try{
            String login = (String)request.getAttribute(JWTFilter.LOGIN_ATTRIBUTE_NAME);
            MusicBand musicBand = musicBandService.updateById(MusicBandDTO.toDomain(musicBandDTO), login);
            return ok(MusicBandDTO.fromDomain(musicBand));
        } catch (CanNotUpdateEntityException e) {
            return error(e.getMessage());
        }
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createMusicBand(@Valid MusicBandDTO musicBandDTO, @Context HttpServletRequest request){
        try{
            String login = (String)request.getAttribute(JWTFilter.LOGIN_ATTRIBUTE_NAME);
            MusicBand musicBand = MusicBandDTO.toDomain(musicBandDTO);
            var res = musicBandService.save(musicBand, login);
            return ok(MusicBandDTO.fromDomain(res));
        } catch (CanNotSaveEntityException e) {
            return error(e.getMessage());
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Integer id){
        try{
            var res = musicBandService.getById(id);
            return ok(MusicBandDTO.fromDomain(res));
        } catch (CanNotGetByIdEntityException e) {
            return error(e.getMessage());
        }
    }
}
