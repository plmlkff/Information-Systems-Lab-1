package ru.itmo.is_lab1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Validation;
import ru.itmo.is_lab1.exceptions.service.CanNotSaveFromFileException;
import ru.itmo.is_lab1.interceptor.annotation.Transactional;
import ru.itmo.is_lab1.interceptor.annotation.WithWebsocketNotification;
import ru.itmo.is_lab1.rest.dto.MusicBandDTO;
import ru.itmo.is_lab1.rest.websocket.NotificationWebSocket;
import ru.itmo.is_lab1.service.FileSaverService;
import ru.itmo.is_lab1.service.MusicBandService;
import ru.itmo.is_lab1.util.JsonParser;

import java.sql.Connection;

@ApplicationScoped
public class JsonFileSaverServiceImpl implements FileSaverService {
    @Inject
    private MusicBandService musicBandService;

    @Transactional(Connection.TRANSACTION_SERIALIZABLE)
    @WithWebsocketNotification(NotificationWebSocket.class)
    @Override
    public int save(byte[] fileBytes, String ownerLogin) throws CanNotSaveFromFileException {
        JsonParser jsonParser = new JsonParser();
        try(var validatorFactory = Validation.buildDefaultValidatorFactory()) {
            var musicBandDTOs = jsonParser.parseList(fileBytes);
            var validator = validatorFactory.getValidator();

            for (var musicBandDTO : musicBandDTOs) {
                var violations = validator.validate(musicBandDTO);
                if (!violations.isEmpty()) throw new CanNotSaveFromFileException("Music band is not valid!");
                musicBandService.save(MusicBandDTO.toDomain(musicBandDTO), ownerLogin);
            }
            return musicBandDTOs.size();
        } catch (Exception e) {
            throw new CanNotSaveFromFileException(e.getMessage());
        }
    }
}