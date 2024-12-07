package ru.itmo.is_lab1.rest.controller;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.itmo.is_lab1.domain.entity.FileUploadHistory;
import ru.itmo.is_lab1.exceptions.service.CanNotCreateHistoryException;
import ru.itmo.is_lab1.exceptions.service.CanNotGetHistoryByOwnerLoginException;
import ru.itmo.is_lab1.exceptions.service.CanNotSaveFromFileException;
import ru.itmo.is_lab1.rest.dto.FileDTO;
import ru.itmo.is_lab1.rest.dto.FileUploadHistoryDTO;
import ru.itmo.is_lab1.security.filter.JWTFilter;
import ru.itmo.is_lab1.service.FileSaverService;
import ru.itmo.is_lab1.service.FileUploadHistoryService;

import static ru.itmo.is_lab1.util.HttpResponse.error;
import static ru.itmo.is_lab1.util.HttpResponse.ok;

@Path("/domain/musicband")
public class FileController {
    @Inject
    private FileSaverService fileSaverService;
    @Inject
    private FileUploadHistoryService fileUploadHistoryService;

    @POST
    @Path("/file")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCount(@Valid FileDTO queryFilter, @Context HttpServletRequest request){
        String login = (String)request.getAttribute(JWTFilter.LOGIN_ATTRIBUTE_NAME);
        try {
            var res = fileSaverService.save(queryFilter.getBytes(), login);
            fileUploadHistoryService.createHistory(login, FileUploadHistory.State.UPLOADED, res);
            return ok(res);
        } catch (CanNotSaveFromFileException | CanNotCreateHistoryException e) {
            try {
                fileUploadHistoryService.createHistory(login, FileUploadHistory.State.CANCELED, 0);
            } catch (CanNotCreateHistoryException ignore){}

            return error(e.getMessage());
        }
    }

    @GET
    @Path("/file/history")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilesUploadsHistory(
            @QueryParam("pageSize") int pageSize,
            @QueryParam("pageNumber") int pageNumber,
            @Context HttpServletRequest request
    ){
        String login = (String)request.getAttribute(JWTFilter.LOGIN_ATTRIBUTE_NAME);
        try {
            var histories = fileUploadHistoryService.getAllHistoriesByUserLogin(login, pageSize, pageNumber);
            var historiesDTO = histories.stream().map(FileUploadHistoryDTO::fromDomain).toList();

            return ok(historiesDTO);
        } catch (CanNotGetHistoryByOwnerLoginException e) {
            return error(e.getMessage());
        }
    }
}
