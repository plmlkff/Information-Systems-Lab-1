package ru.itmo.is_lab1.domain.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import ru.itmo.is_lab1.domain.dao.MusicBandDAO;
import ru.itmo.is_lab1.domain.entity.Coordinates;
import ru.itmo.is_lab1.domain.entity.MusicBand;

@ApplicationScoped
public class MusicBandDAOImpl extends AbstractDAOImpl<MusicBand, Integer> implements MusicBandDAO {

    @Inject
    private Session session;

    public MusicBandDAOImpl() {
        super(MusicBand.class);
    }

    @Override
    public long getCountByCoordinates(Coordinates coordinates) {
        String hql = "SELECT count(*) FROM Coordinates c where c.x = :x and c.y = :y";
        Query query = session.createQuery(hql, Long.class);
        query.setParameter("x", coordinates.getX());
        query.setParameter("y", coordinates.getY());
        return (Long) query.getSingleResult();
    }
}
