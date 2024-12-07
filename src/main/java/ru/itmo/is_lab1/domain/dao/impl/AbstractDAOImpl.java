package ru.itmo.is_lab1.domain.dao.impl;

import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.itmo.is_lab1.domain.dao.AbstractDAO;
import ru.itmo.is_lab1.domain.filter.QueryFilter;
import ru.itmo.is_lab1.exceptions.domain.*;
import ru.itmo.is_lab1.interceptor.annotation.Transactional;
import ru.itmo.is_lab1.util.CriteriaUtil;

import java.util.List;
import java.util.Set;

public abstract class AbstractDAOImpl<T, ID> implements AbstractDAO<T, ID> {

    @Inject
    private Session session;

    private final Class<T> type;

    @Inject
    private CriteriaUtil criteriaUtil;

    public AbstractDAOImpl(Class<T> type) {
        this.type = type;
    }

    @Transactional
    @Override
    public T save(T entity) throws CanNotSaveEntityException {
        try {
            session.persist(entity);
            return entity;
        } catch (Throwable e) {
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

    @Transactional
    @Override
    public void delete(T entity) throws CanNotDeleteEntityException {
        try {
            session.remove(entity);
        } catch (RuntimeException e) {
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

    @Override
    public List<T> findAll(QueryFilter queryFilter) throws CanNotGetAllEntitiesException {
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<T> query = criteriaBuilder.createQuery(type);
            Root<T> rootQuery = query.from(type);

            Predicate predicate = joinPredicates(criteriaBuilder, rootQuery, queryFilter.getCriteria());
            query.where(predicate);

            makeOrderBy(query, rootQuery, queryFilter, criteriaBuilder);

            var sessionQuery = session.createQuery(query);
            makePagination(sessionQuery, queryFilter.getPageNumber(), queryFilter.getPageSize());

            return sessionQuery.getResultList();
        } catch (Throwable e){
            throw new CanNotGetAllEntitiesException("Ошибка при выполнении запроса к базе!");
        }
    }

    @Override
    public Long count(QueryFilter queryFilter) throws CanNotGetCountException {
        try{
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
            Root<T> rootQuery = query.from(type);

            Predicate predicate = joinPredicates(criteriaBuilder, rootQuery, queryFilter.getCriteria());
            query.where(predicate);

            query.select(criteriaBuilder.count(rootQuery));

            var sessionQuery = session.createQuery(query);

            return sessionQuery.getSingleResult();
        } catch (Throwable e){
            throw new CanNotGetCountException(e.getMessage());
        }
    }

    private void makeOrderBy(
            CriteriaQuery<T> query, Root<T> rootQuery,
            QueryFilter queryFilter, CriteriaBuilder criteriaBuilder
    ){
        var sortColumn = queryFilter.getSortColumn();
        var sortColumnFrom = criteriaUtil.makeNeededJoins(sortColumn, rootQuery);
        if (queryFilter.getSortDirection() == QueryFilter.SortDirection.ASC){
            query.orderBy(criteriaBuilder.asc(sortColumnFrom.get(sortColumn.toString())));
        } else {
            query.orderBy(criteriaBuilder.desc(sortColumnFrom.get(sortColumn.toString())));
        }
    }

    private void makePagination(Query<T> query, int pageNumber, int pageSize){
        query.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize);
    }

    private Predicate joinPredicates(CriteriaBuilder criteriaBuilder, Root<T> rootQuery, Set<QueryFilter.Criteria> criteriaSet){
        var predicate = criteriaBuilder.conjunction();
        for (var criteria : criteriaSet){
            var criteriaPredicate = criteriaUtil.toPredicate(criteria, criteriaBuilder, rootQuery);
            if (criteria.getAndCriteria()) predicate = criteriaBuilder.and(predicate, criteriaPredicate);
            else predicate = criteriaBuilder.or(predicate, criteriaPredicate);
        }
        return predicate;
    }
}
