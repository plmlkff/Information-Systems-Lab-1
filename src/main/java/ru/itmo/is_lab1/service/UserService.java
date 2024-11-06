package ru.itmo.is_lab1.service;

import ru.itmo.is_lab1.domain.entity.User;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotSaveEntityException;

public interface UserService {
    User save(User user) throws CanNotSaveEntityException;

    User getByLogin(String login) throws CanNotGetByIdEntityException;
}
