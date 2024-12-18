package ru.itmo.is_lab1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.itmo.is_lab1.domain.dao.FunctionsDAO;
import ru.itmo.is_lab1.domain.dao.MusicBandDAO;
import ru.itmo.is_lab1.domain.dao.UserDAO;
import ru.itmo.is_lab1.domain.entity.*;
import ru.itmo.is_lab1.exceptions.domain.CanNotDeleteEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotExecuteFunctionException;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;
import ru.itmo.is_lab1.interceptor.annotation.HistoryLog;
import ru.itmo.is_lab1.interceptor.annotation.WithWebsocketNotification;
import ru.itmo.is_lab1.rest.websocket.NotificationWebSocket;
import ru.itmo.is_lab1.service.ExtraFunctionsService;

@ApplicationScoped
public class ExtraFunctionsServiceImpl implements ExtraFunctionsService {
    @Inject
    private FunctionsDAO functionsDAO;
    @Inject
    private UserDAO userDAO;
    @Inject
    private MusicBandDAO musicBandDAO;

    @Override
    public int calculateTotalAlbumsCount() throws CanNotExecuteFunctionException {
        return functionsDAO.calculateTotalAlbumsCount();
    }

    @Override
    public int countAlbumsGreaterThan(int value) throws CanNotExecuteFunctionException {
        return functionsDAO.countAlbumsGreaterThan(value);
    }

    @Override
    @HistoryLog(operationType = EntityChangeHistory.OperationType.UPDATE)
    @WithWebsocketNotification(NotificationWebSocket.class)
    public MusicBand increaseParticipants(int id, String userLogin) throws CanNotExecuteFunctionException {
        try{
            MusicBand musicBand = musicBandDAO.findById(id);
            User user = userDAO.findById(userLogin);
            if (user == null) throw new CanNotExecuteFunctionException("Owner does not exist!");
            if (musicBand == null) throw new CanNotExecuteFunctionException();
            if (user.getRole() != UserRole.ADMIN && !musicBand.getOwner().getLogin().equals(userLogin)) {
                throw new CanNotExecuteFunctionException("Permissions denied!");
            }
            functionsDAO.increaseParticipants(id);
            return musicBandDAO.findById(id);
        } catch (CanNotGetByIdEntityException e) {
            throw new CanNotExecuteFunctionException(e.getMessage());
        }
    }

    @Override
    @HistoryLog(operationType = EntityChangeHistory.OperationType.DELETE)
    @WithWebsocketNotification(NotificationWebSocket.class)
    public MusicBand deleteMusicBand(int id, String userLogin) throws CanNotExecuteFunctionException {
        try{
            MusicBand musicBand = musicBandDAO.findById(id);
            User user = userDAO.findById(userLogin);
            if (user == null) throw new CanNotExecuteFunctionException("Owner does not exist!");
            if (musicBand == null) throw new CanNotExecuteFunctionException();
            if (user.getRole() != UserRole.ADMIN && !musicBand.getOwner().getLogin().equals(userLogin)) {
                throw new CanNotDeleteEntityException("Permissions denied!");
            }
            functionsDAO.deleteMusicBand(id);
            return musicBand;
        } catch (CanNotGetByIdEntityException | CanNotDeleteEntityException e) {
            throw new CanNotExecuteFunctionException(e.getMessage());
        }
    }

    @Override
    public int countMusicBandsByGenre(MusicGenre genre) throws CanNotExecuteFunctionException {
        return functionsDAO.countMusicBandsByGenre(genre);
    }
}
