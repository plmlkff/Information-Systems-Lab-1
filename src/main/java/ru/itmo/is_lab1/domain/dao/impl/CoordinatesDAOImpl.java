package ru.itmo.is_lab1.domain.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import ru.itmo.is_lab1.domain.dao.CoordinatesDAO;
import ru.itmo.is_lab1.domain.entity.Coordinates;

@ApplicationScoped
public class CoordinatesDAOImpl extends BasicDAOImpl<Coordinates, Integer> implements CoordinatesDAO {
}
