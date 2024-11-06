package ru.itmo.is_lab1.rest.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is_lab1.domain.entity.User;
import ru.itmo.is_lab1.exceptions.service.CanNotAuthUserException;
import ru.itmo.is_lab1.exceptions.service.CanNotSignUpUserException;
import ru.itmo.is_lab1.rest.dto.AuthDTO;
import ru.itmo.is_lab1.service.AuthService;

import static ru.itmo.is_lab1.util.HttpResponse.*;

@Path("/user")
public class AuthController {
    @Inject
    private AuthService authService;

    @POST
    @Path("/auth")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response auth(@Valid AuthDTO authDTO){
        try {
            User user = authService.auth(authDTO.getLogin(), authDTO.getPassword());
            authDTO.setToken(user.getToken());
            authDTO.setRole(user.getRole());
            return ok(authDTO);
        } catch (CanNotAuthUserException e) {
            return error(Response.Status.UNAUTHORIZED, e.getMessage());
        }
    }

    @POST
    @Path("/signup")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response signUp(@Valid AuthDTO authDTO){
        try {
            User user = authService.signUp(authDTO.getLogin(), authDTO.getPassword(), authDTO.getRole());
            authDTO.setToken(user.getToken());
            authDTO.setRole(user.getRole());
            return ok(authDTO);
        } catch (CanNotSignUpUserException e) {
            return error(Response.Status.UNAUTHORIZED, e.getMessage());
        }
    }
}
