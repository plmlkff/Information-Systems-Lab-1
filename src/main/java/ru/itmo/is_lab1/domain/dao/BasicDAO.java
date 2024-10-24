package ru.itmo.is_lab1.domain.dao;

import ru.itmo.is_lab1.exceptions.domain.CanNotDeleteEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotRefreshEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotSaveEntityException;

public interface BasicDAO<T, ID> {
    public T save(T entity) throws CanNotSaveEntityException;

    public T refresh(T entity) throws CanNotRefreshEntityException;

    public void delete(T entity) throws CanNotDeleteEntityException;

    public T getById(Class<T> tClass, ID id) throws CanNotGetByIdEntityException;
}
