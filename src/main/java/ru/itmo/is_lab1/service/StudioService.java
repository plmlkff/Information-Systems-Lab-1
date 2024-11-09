package ru.itmo.is_lab1.service;

import ru.itmo.is_lab1.domain.entity.Studio;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;

public interface StudioService {
    Studio getById(Integer id) throws CanNotGetByIdEntityException;
}
