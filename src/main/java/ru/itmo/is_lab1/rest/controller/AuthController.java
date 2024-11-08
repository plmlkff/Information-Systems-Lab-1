package ru.itmo.is_lab1.rest.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is_lab1.domain.entity.User;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetAllEntitiesException;
import ru.itmo.is_lab1.exceptions.service.CanNotApproveUserSignUpException;
import ru.itmo.is_lab1.exceptions.service.CanNotAuthUserException;
import ru.itmo.is_lab1.exceptions.service.CanNotRejectUserSignUpException;
import ru.itmo.is_lab1.exceptions.service.CanNotSignUpUserException;
import ru.itmo.is_lab1.rest.dto.AuthDTO;
import ru.itmo.is_lab1.rest.dto.UserDTO;
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
            return ok(UserDTO.fromDomain(user));
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
            return ok(UserDTO.fromDomain(user));
        } catch (CanNotSignUpUserException e) {
            return error(Response.Status.UNAUTHORIZED, e.getMessage());
        }
    }

    @GET
    @Path("/signup/request")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNotApprovedSignUpRequests(
            @QueryParam("pageSize") int pageSize,
            @QueryParam("pageNumber") int pageNumber
    ){
        try{
            var users = authService.getNotApprovedSignUpRequests(pageSize, pageNumber);
            var userDTOs = users.stream().map(UserDTO::fromDomain).toList();
            return ok(userDTOs);
        } catch (CanNotGetAllEntitiesException e){
            return error(e.getMessage());
        }
    }

    @PATCH
    @Path("/approve")
    @Produces(MediaType.APPLICATION_JSON)
    public Response approveUserSignUp(@QueryParam("login") String login){
        try{
            var user = authService.approveUserSignUp(login);
            return ok(UserDTO.fromDomain(user));
        } catch (CanNotApproveUserSignUpException e) {
            return error(e.getMessage());
        }
    }

    @DELETE
    @Path("/reject")
    @Produces(MediaType.APPLICATION_JSON)
    public Response rejectUserSignUp(@QueryParam("login") String login){
        try{
            var user = authService.rejectUserSignUp(login);
            return ok(UserDTO.fromDomain(user));
        } catch (CanNotRejectUserSignUpException e) {
            return error(e.getMessage());
        }
    }
}
