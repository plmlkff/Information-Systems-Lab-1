package ru.itmo.is_lab1.service;

import ru.itmo.is_lab1.domain.entity.User;
import ru.itmo.is_lab1.domain.entity.UserRole;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetAllEntitiesException;
import ru.itmo.is_lab1.exceptions.service.CanNotApproveUserSignUpException;
import ru.itmo.is_lab1.exceptions.service.CanNotAuthUserException;
import ru.itmo.is_lab1.exceptions.service.CanNotRejectUserSignUpException;
import ru.itmo.is_lab1.exceptions.service.CanNotSignUpUserException;

import java.util.List;

public interface AuthService {
    User auth(String login, String password) throws CanNotAuthUserException;

    User signUp(String login, String password, UserRole role) throws CanNotSignUpUserException;

    List<User> getNotApprovedSignUpRequests(int pageSize, int pageNumber) throws CanNotGetAllEntitiesException;

    User approveUserSignUp(String login) throws CanNotApproveUserSignUpException;

    User rejectUserSignUp(String login) throws CanNotRejectUserSignUpException;
}
