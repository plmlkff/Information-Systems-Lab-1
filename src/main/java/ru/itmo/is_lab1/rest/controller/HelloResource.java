package ru.itmo.is_lab1.rest.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.itmo.is_lab1.domain.dao.MusicBandDAO;
import ru.itmo.is_lab1.domain.dao.StudioDAO;
import ru.itmo.is_lab1.domain.entity.Studio;
import ru.itmo.is_lab1.exceptions.domain.CanNotSaveEntityException;

@Path("/hello-world")
public class HelloResource {

    @Inject
    StudioDAO dao;

    @GET
    @Produces("application/json")
    public String hello() throws CanNotSaveEntityException {
        Studio studio = new Studio();
        studio.setAddress("123");
//        var trans = session.getTransaction();
        dao.save(studio);
//        trans.begin();
//        trans.commit();
        return "123";
    }

}