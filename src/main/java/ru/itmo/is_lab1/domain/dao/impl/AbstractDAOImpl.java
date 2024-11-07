package ru.itmo.is_lab1.domain.dao.impl;

import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import ru.itmo.is_lab1.domain.dao.AbstractDAO;
import ru.itmo.is_lab1.exceptions.domain.*;

import java.util.List;

public abstract class AbstractDAOImpl<T, ID> implements AbstractDAO<T, ID> {

    @Inject
    private Session session;

    private final Class<T> type;

    public AbstractDAOImpl(Class<T> type) {
        this.type = type;
    }

    @Override
    public T save(T entity) throws CanNotSaveEntityException {
        var trans = session.getTransaction();
        try {
            trans.begin();
            session.persist(entity);
            trans.commit();
            session.refresh(entity);
            return entity;
        } catch (Throwable e) {
            trans.rollback();
            throw new CanNotSaveEntityException(e.getMessage());
        }
    }

    @Override
    public T refresh(T entity) throws CanNotRefreshEntityException {
        try {
            session.refresh(entity);
            return entity;
        } catch (RuntimeException e) {
            throw new CanNotRefreshEntityException(e.getMessage());
        }
    }

    @Override
    public void delete(T entity) throws CanNotDeleteEntityException {
        var trans = session.getTransaction();
        try {
            trans.begin();
            session.remove(entity);
            trans.commit();
        } catch (RuntimeException e) {
            trans.rollback();
            throw new CanNotDeleteEntityException(e.getMessage());
        }
    }

    @Override
    public T findById(ID id) throws CanNotGetByIdEntityException {
        try {
            return session.get(type, id);
        } catch (RuntimeException e) {
            throw new CanNotGetByIdEntityException(e.getMessage());
        }
    }

    @Override
    public List<T> findAll() throws CanNotGetAllEntitiesException {
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
            query.from(type);
            return session.createQuery(query).getResultList();
        } catch (RuntimeException e) {
            throw new CanNotGetAllEntitiesException(e.getMessage());
        }
    }
}
