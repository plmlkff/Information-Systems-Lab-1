package ru.itmo.is_lab1.rest.controller;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is_lab1.domain.entity.MusicGenre;
import ru.itmo.is_lab1.exceptions.domain.CanNotExecuteFunctionException;
import ru.itmo.is_lab1.security.filter.JWTFilter;
import ru.itmo.is_lab1.service.ExtraFunctionsService;

import static ru.itmo.is_lab1.util.HttpResponse.*;

@Path("/domain/function")
public class ExtraFunctionsController {
    @Inject
    private HttpServletRequest request;
    @Inject
    private ExtraFunctionsService extraFunctionsService;

    @GET
    @Path("/calculateTotalAlbumsCount")
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculateTotalAlbumsCount(){
        try {
            return ok(extraFunctionsService.calculateTotalAlbumsCount());
        } catch (CanNotExecuteFunctionException e) {
            return error(e.getMessage());
        }
    }

    @GET
    @Path("/countAlbumsGreaterThan")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countAlbumsGreaterThan(@QueryParam("count") int count){
        try {
            return ok(extraFunctionsService.countAlbumsGreaterThan(count));
        } catch (CanNotExecuteFunctionException e) {
            return error(e.getMessage());
        }
    }

    @PATCH
    @Path("/increaseParticipants")
    @Produces(MediaType.APPLICATION_JSON)
    public Response increaseParticipants(@QueryParam("id") int id){
        try {
            String login = (String)request.getAttribute(JWTFilter.LOGIN_ATTRIBUTE_NAME);
            extraFunctionsService.increaseParticipants(id, login);
            return ok();
        } catch (CanNotExecuteFunctionException e) {
            return error(e.getMessage());
        }
    }

    @DELETE
    @Path("/deleteMusicBand")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMusicBand(@QueryParam("id") int id){
        try {
            String login = (String)request.getAttribute(JWTFilter.LOGIN_ATTRIBUTE_NAME);
            extraFunctionsService.deleteMusicBand(id, login);
            return ok();
        } catch (CanNotExecuteFunctionException e) {
            return error(e.getMessage());
        }
    }

    @GET
    @Path("/countMusicBandsByGenre")
    @Produces(MediaType.APPLICATION_JSON)
    public Response countMusicBandsByGenre(@QueryParam("genre") MusicGenre genre){
        try {
            return ok(extraFunctionsService.countMusicBandsByGenre(genre));
        } catch (CanNotExecuteFunctionException e) {
            return error(e.getMessage());
        }
    }
}
