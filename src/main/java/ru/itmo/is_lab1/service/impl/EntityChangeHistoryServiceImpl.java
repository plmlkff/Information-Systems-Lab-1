package ru.itmo.is_lab1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.itmo.is_lab1.domain.dao.EntityChangeHistoryDAO;
import ru.itmo.is_lab1.domain.dao.UserDAO;
import ru.itmo.is_lab1.domain.entity.EntityChangeHistory;
import ru.itmo.is_lab1.domain.entity.User;
import ru.itmo.is_lab1.domain.filter.QueryFilter;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetAllEntitiesException;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotSaveEntityException;
import ru.itmo.is_lab1.exceptions.service.CanNotCreateHistoryException;
import ru.itmo.is_lab1.service.EntityChangeHistoryService;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class EntityChangeHistoryServiceImpl implements EntityChangeHistoryService {
    @Inject
    private EntityChangeHistoryDAO entityChangeHistoryDAO;
    @Inject
    private UserDAO userDAO;

    @Override
    public EntityChangeHistory createHistory(String login, EntityChangeHistory.OperationType operationType, Integer entityId) throws CanNotCreateHistoryException {
        try{
            EntityChangeHistory entityChangeHistory = new EntityChangeHistory();

            User user = userDAO.findById(login);
            if (user == null) throw new CanNotCreateHistoryException("User does not exist!");

            entityChangeHistory.setUser(user);
            entityChangeHistory.setOperation(operationType);
            entityChangeHistory.setMusicBandId(entityId);
            entityChangeHistory.setTime(new Date());

            return entityChangeHistoryDAO.save(entityChangeHistory);
        } catch (CanNotGetByIdEntityException e) {
            throw new CanNotCreateHistoryException("User does not exist!");
        } catch (CanNotSaveEntityException e) {
            throw new CanNotCreateHistoryException(e.getMessage());
        }
    }

    @Override
    public List<EntityChangeHistory> getAllHistory(QueryFilter queryFilter) throws CanNotGetAllEntitiesException {
        return entityChangeHistoryDAO.findAll(queryFilter);
    }
}
