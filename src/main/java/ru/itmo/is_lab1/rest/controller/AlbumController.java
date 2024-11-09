package ru.itmo.is_lab1.rest.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;
import ru.itmo.is_lab1.rest.dto.AlbumDTO;
import ru.itmo.is_lab1.service.AlbumService;

import static ru.itmo.is_lab1.util.HttpResponse.error;
import static ru.itmo.is_lab1.util.HttpResponse.ok;

@Path("/domain/album")
public class AlbumController {
    @Inject
    private AlbumService albumService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Integer id){
        try{
            var res = albumService.getById(id);
            return ok(AlbumDTO.fromDomain(res));
        } catch (CanNotGetByIdEntityException e) {
            return error(e.getMessage());
        }
    }
}
