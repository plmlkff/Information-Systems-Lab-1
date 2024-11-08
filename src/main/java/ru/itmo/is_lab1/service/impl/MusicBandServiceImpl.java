package ru.itmo.is_lab1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Session;
import ru.itmo.is_lab1.domain.dao.MusicBandDAO;
import ru.itmo.is_lab1.domain.dao.StudioDAO;
import ru.itmo.is_lab1.domain.dao.UserDAO;
import ru.itmo.is_lab1.domain.entity.MusicBand;
import ru.itmo.is_lab1.domain.entity.Studio;
import ru.itmo.is_lab1.domain.entity.User;
import ru.itmo.is_lab1.domain.filter.QueryFilter;
import ru.itmo.is_lab1.exceptions.domain.*;
import ru.itmo.is_lab1.rest.websocket.NotificationWebSocket;
import ru.itmo.is_lab1.security.interceptor.annotation.WithWebsocketNotification;
import ru.itmo.is_lab1.service.MusicBandService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
public class MusicBandServiceImpl implements MusicBandService {
    @Inject
    private MusicBandDAO musicBandDAO;
    @Inject
    private UserDAO userDAO;
    @Inject
    private StudioDAO studioDAO;

    @WithWebsocketNotification(NotificationWebSocket.class)
    @Override
    public void deleteById(Integer id, String userLogin) throws CanNotDeleteEntityException {
        try {
            MusicBand musicBand = musicBandDAO.findById(id);
            if (musicBand == null) throw new CanNotGetByIdEntityException();
            if (!musicBand.getOwner().getLogin().equals(userLogin)) {
                throw new CanNotDeleteEntityException("Permissions denied!");
            }
            musicBandDAO.delete(musicBand);
        } catch (CanNotGetByIdEntityException e) {
            throw new CanNotDeleteEntityException("ID does not exist");
        }
    }

    @WithWebsocketNotification(NotificationWebSocket.class)
    @Override
    public MusicBand updateById(MusicBand newMusicBand, String userLogin) throws CanNotUpdateEntityException {
        try {
            MusicBand musicBand = musicBandDAO.findById(newMusicBand.getId());
            if (musicBand == null) throw new CanNotGetByIdEntityException();
            if (!musicBand.getOwner().getLogin().equals(userLogin)) {
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

    @Override
    public List<MusicBand> getAll() throws CanNotGetAllEntitiesException {
        return musicBandDAO.findAll();
    }

    @Override
    public List<MusicBand> getAll(QueryFilter queryFilter) throws CanNotGetAllEntitiesException {
        return musicBandDAO.findAll(queryFilter);
    }

    @WithWebsocketNotification(NotificationWebSocket.class)
    @Override
    public MusicBand save(MusicBand musicBand, String ownerLogin) throws CanNotSaveEntityException {
        try {
            if (musicBand.getId() != null) throw new CanNotSaveEntityException("Id must be null!");
            User owner = userDAO.findById(ownerLogin);
            if (owner == null) throw new CanNotGetByIdEntityException();
            musicBand.setOwner(owner);
            return musicBandDAO.save(musicBand);
        } catch (CanNotGetByIdEntityException e) {
            throw new CanNotSaveEntityException("User not found!");
        }
    }

    @Override
    public MusicBand getById(Integer id) throws CanNotGetByIdEntityException {
        var musicBand = musicBandDAO.findById(id);
        if (musicBand == null) throw new CanNotGetByIdEntityException("ID does not exist!");
        return musicBand;
    }
}
