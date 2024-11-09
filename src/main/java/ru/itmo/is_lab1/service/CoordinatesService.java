package ru.itmo.is_lab1.service;

import ru.itmo.is_lab1.domain.entity.Coordinates;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;

public interface CoordinatesService {
    Coordinates getById(Integer id) throws CanNotGetByIdEntityException;
}
