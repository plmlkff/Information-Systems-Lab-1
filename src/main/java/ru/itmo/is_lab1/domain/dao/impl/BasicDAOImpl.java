package ru.itmo.is_lab1.domain.dao.impl;

import jakarta.inject.Inject;
import org.hibernate.Session;
import ru.itmo.is_lab1.domain.dao.BasicDAO;
import ru.itmo.is_lab1.exceptions.domain.CanNotDeleteEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotRefreshEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotSaveEntityException;

public abstract class BasicDAOImpl<T, ID> implements BasicDAO<T, ID> {

    @Inject
    private Session session;

    public T save(T entity) throws CanNotSaveEntityException {
        var trans = session.getTransaction();
        try{
            trans.begin();
            session.persist(entity);
            trans.commit();
            session.refresh(entity);
            return entity;
        } catch (Throwable e){
            trans.rollback();
            throw new CanNotSaveEntityException();
        }
    }

    public T refresh(T entity) throws CanNotRefreshEntityException {
        try{
            session.refresh(entity);
            return entity;
        } catch (RuntimeException e){
            throw new CanNotRefreshEntityException();
        }
    }

    public void delete(T entity) throws CanNotDeleteEntityException {
        var trans = session.getTransaction();
        try{
            trans.begin();
            session.remove(entity);
            trans.commit();
        } catch (RuntimeException e){
            trans.rollback();
            throw new CanNotDeleteEntityException();
        }
    }

    public T getById(Class<T> tClass, ID id) throws CanNotGetByIdEntityException {
        try{
            return session.get(tClass, id);
        } catch (RuntimeException e){
            throw new CanNotGetByIdEntityException();
        }
    }
}
