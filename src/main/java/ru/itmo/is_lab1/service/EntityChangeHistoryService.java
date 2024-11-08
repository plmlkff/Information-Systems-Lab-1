package ru.itmo.is_lab1.service;

import ru.itmo.is_lab1.domain.entity.EntityChangeHistory;
import ru.itmo.is_lab1.domain.filter.QueryFilter;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetAllEntitiesException;
import ru.itmo.is_lab1.exceptions.service.CanNotCreateHistoryException;

import java.util.List;

public interface EntityChangeHistoryService {
    EntityChangeHistory createHistory(String login, EntityChangeHistory.OperationType operationType, Integer entityId) throws CanNotCreateHistoryException;

    List<EntityChangeHistory> getAllHistory(QueryFilter queryFilter) throws CanNotGetAllEntitiesException;
}
