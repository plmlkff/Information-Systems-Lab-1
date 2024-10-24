package ru.itmo.is_lab1.domain.dao;

import jakarta.inject.Inject;
import org.hibernate.Session;

public abstract class BasicDAO<T> {

    @Inject
    private Session session;

    public T save(T entity){

    }
}
