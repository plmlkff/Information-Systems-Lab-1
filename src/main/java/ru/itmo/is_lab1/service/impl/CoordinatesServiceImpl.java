package ru.itmo.is_lab1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.itmo.is_lab1.domain.dao.CoordinatesDAO;
import ru.itmo.is_lab1.domain.entity.Coordinates;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;
import ru.itmo.is_lab1.service.CoordinatesService;

@ApplicationScoped
public class CoordinatesServiceImpl implements CoordinatesService {
    @Inject
    private CoordinatesDAO coordinatesDAO;

    @Override
    public Coordinates getById(Integer id) throws CanNotGetByIdEntityException {
        var coordinates = coordinatesDAO.findById(id);
        if (coordinates == null) throw new CanNotGetByIdEntityException("Id does not exist!");
        return coordinates;
    }
}
