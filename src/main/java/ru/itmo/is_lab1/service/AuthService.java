package ru.itmo.is_lab1.service;

import ru.itmo.is_lab1.domain.entity.User;
import ru.itmo.is_lab1.domain.entity.UserRole;
import ru.itmo.is_lab1.exceptions.service.CanNotAuthUserException;
import ru.itmo.is_lab1.exceptions.service.CanNotSignUpUserException;

public interface AuthService {
    User auth(String login, String password) throws CanNotAuthUserException;

    User signUp(String login, String password, UserRole role) throws CanNotSignUpUserException;
}
