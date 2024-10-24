package ru.itmo.is_lab1.rest.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.itmo.is_lab1.domain.entity.Studio;

@Path("/hello-world")
public class HelloResource {
    @Inject
    private Session session;

    @GET
    @Produces("application/json")
    public String hello() {
        Studio studio = new Studio();
        studio.setAddress("123");
//        var trans = session.getTransaction();
//        trans.begin();
        session.persist(studio);
//        trans.commit();
        return "123";
    }

}