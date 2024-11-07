package ru.itmo.is_lab1.domain.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.itmo.is_lab1.domain.dao.MusicBandDAO;
import ru.itmo.is_lab1.domain.entity.MusicBand;
import ru.itmo.is_lab1.domain.filter.QueryFilter;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetAllEntitiesException;

import java.util.List;
import java.util.Set;

@ApplicationScoped
public class MusicBandDAOImpl extends AbstractDAOImpl<MusicBand, Integer> implements MusicBandDAO {
    @Inject
    private Session session;

    public MusicBandDAOImpl() {
        super(MusicBand.class);
    }

    @Override
    public List<MusicBand> findAll(QueryFilter queryFilter) throws CanNotGetAllEntitiesException {
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<MusicBand> query = criteriaBuilder.createQuery(MusicBand.class);
            Root<MusicBand> rootQuery = query.from(MusicBand.class);
            Predicate predicate = joinPredicates(criteriaBuilder, rootQuery, queryFilter.getCriteria());
            query.where(predicate);
            makeOrderBy(query, rootQuery, queryFilter, criteriaBuilder);
            var sessionQuery = session.createQuery(query);
            makePagination(sessionQuery, queryFilter.getPageNumber(), queryFilter.getPageSize());
            return sessionQuery.getResultList();
        } catch (Throwable e){
            throw new CanNotGetAllEntitiesException(e.getMessage());
        }
    }

    private void makeOrderBy(
            CriteriaQuery<MusicBand> query, Root<MusicBand> rootQuery,
            QueryFilter queryFilter, CriteriaBuilder criteriaBuilder
    ){
        var sortColumn = queryFilter.getSortColumn();
        var sortColumnFrom = QueryFilter.getFrom(sortColumn, rootQuery);
        if (queryFilter.getSortDirection() == QueryFilter.SortDirection.ASC){
            query.orderBy(criteriaBuilder.asc(sortColumnFrom.get(sortColumn.toString())));
        } else {
            query.orderBy(criteriaBuilder.desc(sortColumnFrom.get(sortColumn.toString())));
        }
    }

    private void makePagination(Query<MusicBand> query, int pageNumber, int pageSize){
        query.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize);
    }

    private Predicate joinPredicates(CriteriaBuilder criteriaBuilder, Root<MusicBand> rootQuery, Set<QueryFilter.Criteria> criteriaSet){
        var predicate = criteriaBuilder.conjunction();
        for (var criteria : criteriaSet){
            var criteriaPredicate = criteria.toPredicate(criteriaBuilder, rootQuery);
            if (criteria.getAndCriteria()) predicate = criteriaBuilder.and(predicate, criteriaPredicate);
            else predicate = criteriaBuilder.or(predicate, criteriaPredicate);
        }
        return predicate;
    }
}
