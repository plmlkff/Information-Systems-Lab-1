package ru.itmo.is_lab1.domain.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import ru.itmo.is_lab1.domain.dao.UserDAO;
import ru.itmo.is_lab1.domain.entity.User;

@ApplicationScoped
public class UserDAOImpl extends AbstractDAOImpl<User, String> implements UserDAO {
    public UserDAOImpl() {
        super(User.class);
    }
}
