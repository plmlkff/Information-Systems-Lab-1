package ru.itmo.is_lab1.domain.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Session;
import ru.itmo.is_lab1.domain.dao.EntityChangeHistoryDAO;
import ru.itmo.is_lab1.domain.entity.EntityChangeHistory;


@ApplicationScoped
public class EntityChangeHistoryDAOImpl extends AbstractDAOImpl<EntityChangeHistory, Long> implements EntityChangeHistoryDAO {
    @Inject
    private Session session;

    public EntityChangeHistoryDAOImpl() {
        super(EntityChangeHistory.class);
    }
}
