package ru.itmo.is_lab1.domain.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import ru.itmo.is_lab1.domain.dao.StudioDAO;
import ru.itmo.is_lab1.domain.entity.Studio;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetStudioByAddressException;

@ApplicationScoped
public class StudioDAOImpl extends AbstractDAOImpl<Studio, Integer> implements StudioDAO {
    @Inject
    private Session session;

    public StudioDAOImpl() {
        super(Studio.class);
    }

    @Override
    public Studio findByAddress(String address) throws CanNotGetStudioByAddressException {
        try{
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Studio> query = criteriaBuilder.createQuery(Studio.class);
            var root = query.from(Studio.class);
            var predicate = criteriaBuilder.equal(root.get(Studio.Columns.ADDRESS), address);
            query.where(predicate);
            return session.createQuery(query).getSingleResult();
        } catch (RuntimeException e){
            throw new CanNotGetStudioByAddressException();
        }
    }
}
