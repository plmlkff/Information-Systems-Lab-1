package ru.itmo.is_lab1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.itmo.is_lab1.domain.dao.UserDAO;
import ru.itmo.is_lab1.domain.entity.User;
import ru.itmo.is_lab1.domain.entity.UserRole;
import ru.itmo.is_lab1.domain.filter.QueryFilter;
import ru.itmo.is_lab1.domain.filter.TableColumn;
import ru.itmo.is_lab1.exceptions.domain.CanNotDeleteEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetAllEntitiesException;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotSaveEntityException;
import ru.itmo.is_lab1.exceptions.service.CanNotApproveUserSignUpException;
import ru.itmo.is_lab1.exceptions.service.CanNotAuthUserException;
import ru.itmo.is_lab1.exceptions.service.CanNotRejectUserSignUpException;
import ru.itmo.is_lab1.exceptions.service.CanNotSignUpUserException;
import ru.itmo.is_lab1.exceptions.util.CanNotCreateHashException;
import ru.itmo.is_lab1.interceptor.annotation.Transactional;
import ru.itmo.is_lab1.interceptor.annotation.WithPrivileges;
import ru.itmo.is_lab1.security.service.JWTService;
import ru.itmo.is_lab1.service.AuthService;
import ru.itmo.is_lab1.util.SHA512HashUtil;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

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

    @Override
    public List<User> getNotApprovedSignUpRequests(int pageSize, int pageNumber) throws CanNotGetAllEntitiesException {
        QueryFilter queryFilter = new QueryFilter();
        if (pageSize > 0) queryFilter.setPageSize(pageSize);
        if (pageNumber > 0) queryFilter.setPageNumber(pageNumber);

        QueryFilter.Criteria criteria = new QueryFilter.Criteria();
        criteria.setFilteringColumn(TableColumn.USER_IS_APPROVED);
        criteria.setFilteringValue("false");
        queryFilter.setCriteria(Set.of(criteria));

        return userDAO.findAll(queryFilter);
    }

    @Override
    @WithPrivileges(UserRole.ADMIN)
    public User approveUserSignUp(String login) throws CanNotApproveUserSignUpException {
        try {
            User user = userDAO.findById(login);
            if (user == null) throw new CanNotApproveUserSignUpException("Login does not exist");
            user.setApproved(true);
            return userDAO.save(user);
        } catch (CanNotGetByIdEntityException | CanNotSaveEntityException e) {
            throw new CanNotApproveUserSignUpException(e.getMessage());
        }
    }

    @Override
    @WithPrivileges(UserRole.ADMIN)
    public User rejectUserSignUp(String login) throws CanNotRejectUserSignUpException {
        try {
            User user = userDAO.findById(login);
            if (user == null) throw new CanNotRejectUserSignUpException("Login does not exist");
            userDAO.delete(user);
            return user;
        } catch (CanNotGetByIdEntityException | CanNotDeleteEntityException e) {
            throw new CanNotRejectUserSignUpException(e.getMessage());
        }
    }
}
