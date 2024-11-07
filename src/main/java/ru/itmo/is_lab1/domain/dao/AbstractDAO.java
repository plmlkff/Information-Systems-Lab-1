package ru.itmo.is_lab1.domain.dao;

import ru.itmo.is_lab1.domain.filter.QueryFilter;
import ru.itmo.is_lab1.exceptions.domain.*;

import java.util.List;

public interface AbstractDAO<T, ID> {
    T save(T entity) throws CanNotSaveEntityException;

    T refresh(T entity) throws CanNotRefreshEntityException;

    void delete(T entity) throws CanNotDeleteEntityException;

    T findById(ID id) throws CanNotGetByIdEntityException;
    
    List<T> findAll() throws CanNotGetAllEntitiesException;
}
