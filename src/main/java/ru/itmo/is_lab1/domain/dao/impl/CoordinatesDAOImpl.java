package ru.itmo.is_lab1.domain.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import ru.itmo.is_lab1.domain.dao.CoordinatesDAO;
import ru.itmo.is_lab1.domain.entity.Coordinates;

@ApplicationScoped
public class CoordinatesDAOImpl extends AbstractDAOImpl<Coordinates, Integer> implements CoordinatesDAO {
    public CoordinatesDAOImpl() {
        super(Coordinates.class);
    }
}
