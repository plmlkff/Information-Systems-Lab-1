package ru.itmo.is_lab1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.itmo.is_lab1.domain.dao.UserDAO;
import ru.itmo.is_lab1.domain.entity.User;
import ru.itmo.is_lab1.domain.entity.UserRole;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotSaveEntityException;
import ru.itmo.is_lab1.exceptions.service.CanNotAuthUserException;
import ru.itmo.is_lab1.exceptions.service.CanNotSignUpUserException;
import ru.itmo.is_lab1.exceptions.util.CanNotCreateHashException;
import ru.itmo.is_lab1.security.service.JWTService;
import ru.itmo.is_lab1.service.AuthService;
import ru.itmo.is_lab1.util.SHA512HashUtil;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {
    @Inject
    private JWTService jwtService;
    @Inject
    private UserDAO userDAO;

    @Override
    public User auth(String login, String password) throws CanNotAuthUserException {
        try {
            User user = userDAO.findById(login);
            if (user == null) throw new CanNotAuthUserException("User does not exist");
            if (!user.isApproved()) throw new CanNotAuthUserException("User is not approved!");
            if (password == null || password.isEmpty()) throw new CanNotAuthUserException("Password must not be empty!");

            password = SHA512HashUtil.hash(password);
            if (!user.getPassword().equals(password)) throw new CanNotAuthUserException("Wrong password!");
            user.setToken(jwtService.create(login));
            return user;
        } catch (CanNotCreateHashException | CanNotGetByIdEntityException e) {
            throw new CanNotAuthUserException("Something went wrong...");
        }
    }

    @Override
    public User signUp(String login, String password, UserRole role) throws CanNotSignUpUserException {
        try {
            if (login == null || login.isEmpty()) throw new CanNotSignUpUserException("Login must not be null!");
            if (userDAO.findById(login) != null) throw new CanNotSignUpUserException("Login already exist");
            if (password == null || password.isEmpty()) throw new CanNotSignUpUserException("Password must not be empty!");
            if (role == null) throw new CanNotSignUpUserException("Role must not be null!");

            password = SHA512HashUtil.hash(password);
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setRole(role);
            user.setApproved(!UserRole.ADMIN.equals(role));
            userDAO.save(user);

            if (UserRole.ADMIN.equals(role)) throw new CanNotSignUpUserException("Wait another admin approve!");
            user.setToken(jwtService.create(login));
            return user;
        } catch (CanNotCreateHashException | CanNotGetByIdEntityException | CanNotSaveEntityException e) {
            throw new CanNotSignUpUserException("Something went wrong...");
        }
    }
}
