package ru.itmo.is_lab1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.itmo.is_lab1.domain.dao.MusicBandDAO;
import ru.itmo.is_lab1.domain.dao.UserDAO;
import ru.itmo.is_lab1.domain.entity.EntityChangeHistory;
import ru.itmo.is_lab1.domain.entity.MusicBand;
import ru.itmo.is_lab1.domain.entity.User;
import ru.itmo.is_lab1.domain.entity.UserRole;
import ru.itmo.is_lab1.domain.filter.QueryFilter;
import ru.itmo.is_lab1.exceptions.domain.*;
import ru.itmo.is_lab1.interceptor.annotation.HistoryLog;
import ru.itmo.is_lab1.interceptor.annotation.Transactional;
import ru.itmo.is_lab1.rest.websocket.NotificationWebSocket;
import ru.itmo.is_lab1.interceptor.annotation.WithWebsocketNotification;
import ru.itmo.is_lab1.service.MusicBandService;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class MusicBandServiceImpl implements MusicBandService {
    public static final int MAX_MUSIC_BANDS_WITH_SAME_COORDINATES_COUNT = 10;

    @Inject
    private MusicBandDAO musicBandDAO;
    @Inject
    private UserDAO userDAO;

    @Override
    @WithWebsocketNotification(NotificationWebSocket.class)
    @HistoryLog(operationType = EntityChangeHistory.OperationType.DELETE)
    public Integer deleteById(Integer id, String userLogin) throws CanNotDeleteEntityException {
        try {
            MusicBand musicBand = musicBandDAO.findById(id);
            User user = userDAO.findById(userLogin);
            if (user == null) throw new CanNotDeleteEntityException("Owner does not exist!");
            if (musicBand == null) throw new CanNotGetByIdEntityException();
            if (user.getRole() != UserRole.ADMIN && !musicBand.getOwner().getLogin().equals(userLogin)) {
                throw new CanNotDeleteEntityException("Permissions denied!");
            }
            musicBandDAO.delete(musicBand);
            return musicBand.getId();
        } catch (CanNotGetByIdEntityException e) {
            throw new CanNotDeleteEntityException("ID does not exist");
        }
    }

    @Override
    @WithWebsocketNotification(NotificationWebSocket.class)
    @HistoryLog(operationType = EntityChangeHistory.OperationType.UPDATE)
    public MusicBand updateById(MusicBand newMusicBand, String userLogin) throws CanNotUpdateEntityException {
        try {
            MusicBand musicBand = musicBandDAO.findById(newMusicBand.getId());
            User user = userDAO.findById(userLogin);
            if (user == null) throw new CanNotUpdateEntityException("Owner does not exist!");
            if (musicBand == null) throw new CanNotGetByIdEntityException();
            if (user.getRole() != UserRole.ADMIN && !musicBand.getOwner().getLogin().equals(userLogin)) {
                throw new CanNotUpdateEntityException("Permissions denied!");
            }
            musicBand.merge(newMusicBand);
            return musicBandDAO.save(musicBand);
        } catch (CanNotGetByIdEntityException e) {
            throw new CanNotUpdateEntityException("ID does not exist");
        } catch (CanNotSaveEntityException e) {
            throw new CanNotUpdateEntityException("Can not save entity!");
        }
    }

    @Deprecated
    @Override
    public List<MusicBand> getAll() throws CanNotGetAllEntitiesException {
        return musicBandDAO.findAll();
    }

    @Override
    public List<MusicBand> getAll(QueryFilter queryFilter) throws CanNotGetAllEntitiesException {
        return musicBandDAO.findAll(queryFilter);
    }

    @Override
    @Transactional(Connection.TRANSACTION_SERIALIZABLE)
    @WithWebsocketNotification(NotificationWebSocket.class)
    @HistoryLog(operationType = EntityChangeHistory.OperationType.CREATE)
    public MusicBand save(MusicBand musicBand, String ownerLogin) throws CanNotSaveEntityException {
        try {
            if (musicBand.getId() != null) throw new CanNotSaveEntityException("Id must be null!");
            User owner = userDAO.findById(ownerLogin);
            if (owner == null) throw new CanNotGetByIdEntityException();
            if (!checkMusicBandWithSameCoordinatesCount(musicBand)) throw new CanNotSaveEntityException("Count of music bands with this coordinates too big!");
            musicBand.setOwner(owner);
            musicBand.setCreationDate(LocalDate.now());
            return musicBandDAO.save(musicBand);
        } catch (CanNotGetByIdEntityException e) {
            throw new CanNotSaveEntityException("User not found!");
        }
    }

    private boolean checkMusicBandWithSameCoordinatesCount(MusicBand musicBand) throws CanNotSaveEntityException {
        if (musicBand.getCoordinates() == null) throw new CanNotSaveEntityException("Coordinates must no be null!");
        return musicBandDAO.getCountByCoordinates(musicBand.getCoordinates()) < MAX_MUSIC_BANDS_WITH_SAME_COORDINATES_COUNT;
    }

    @Override
    public MusicBand getById(Integer id) throws CanNotGetByIdEntityException {
        var musicBand = musicBandDAO.findById(id);
        if (musicBand == null) throw new CanNotGetByIdEntityException("ID does not exist!");
        return musicBand;
    }

    @Override
    public Long count(QueryFilter queryFilter) throws CanNotGetCountException {
        return musicBandDAO.count(queryFilter);
    }
}
