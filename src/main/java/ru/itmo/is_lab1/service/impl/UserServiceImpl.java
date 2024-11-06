package ru.itmo.is_lab1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.itmo.is_lab1.domain.dao.UserDAO;
import ru.itmo.is_lab1.domain.entity.User;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotSaveEntityException;
import ru.itmo.is_lab1.service.UserService;

@ApplicationScoped
public class UserServiceImpl implements UserService {
    @Inject
    private UserDAO userDAO;

    @Override
    public User save(User user) throws CanNotSaveEntityException {
        return userDAO.save(user);
    }

    @Override
    public User getByLogin(String login) throws CanNotGetByIdEntityException {
        return userDAO.findById(login);
    }
}
