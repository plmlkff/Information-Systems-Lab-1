package ru.itmo.is_lab1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Validation;
import ru.itmo.is_lab1.domain.dao.FileUploadHistoryDAO;
import ru.itmo.is_lab1.domain.entity.FileUploadHistory;
import ru.itmo.is_lab1.exceptions.service.CanNotCreateHistoryException;
import ru.itmo.is_lab1.exceptions.service.CanNotLoadFileException;
import ru.itmo.is_lab1.exceptions.service.CanNotSaveFromFileException;
import ru.itmo.is_lab1.interceptor.annotation.Transactional;
import ru.itmo.is_lab1.interceptor.annotation.WithWebsocketNotification;
import ru.itmo.is_lab1.rest.dto.FileDTO;
import ru.itmo.is_lab1.rest.dto.MusicBandDTO;
import ru.itmo.is_lab1.rest.websocket.NotificationWebSocket;
import ru.itmo.is_lab1.service.FileService;
import ru.itmo.is_lab1.service.FileStorageService;
import ru.itmo.is_lab1.service.FileUploadHistoryService;
import ru.itmo.is_lab1.service.MusicBandService;
import ru.itmo.is_lab1.util.JsonParser;

import java.sql.Connection;

@ApplicationScoped
public class JsonFileServiceImpl implements FileService {
    @Inject
    private MusicBandService musicBandService;
    @Inject
    private FileStorageService fileStorageService;
    @Inject
    private FileUploadHistoryService fileUploadHistoryService;
    @Inject
    private FileUploadHistoryDAO fileUploadHistoryDAO;

    @Transactional(Connection.TRANSACTION_SERIALIZABLE)
    @WithWebsocketNotification(NotificationWebSocket.class)
    @Override
    public int save(FileDTO fileDTO, String ownerLogin) throws CanNotSaveFromFileException {
        JsonParser jsonParser = new JsonParser();
        try(var validatorFactory = Validation.buildDefaultValidatorFactory()) {
            var musicBandDTOs = jsonParser.parseList(fileDTO.getBytes());
            var validator = validatorFactory.getValidator();

            for (var musicBandDTO : musicBandDTOs) {
                var violations = validator.validate(musicBandDTO);
                if (!violations.isEmpty()) throw new CanNotSaveFromFileException("Music band is not valid!");
                musicBandService.save(MusicBandDTO.toDomain(musicBandDTO), ownerLogin);
            }

            var fileName = fileStorageService.saveFile(new FileDTO(fileDTO.getName(), fileDTO.getBytes()));

            fileUploadHistoryService.createHistory(ownerLogin, FileUploadHistory.State.UPLOADED, musicBandDTOs.size(), fileName);

            return musicBandDTOs.size();
        } catch (Exception e) {
            try {
                fileUploadHistoryService.createHistory(ownerLogin, FileUploadHistory.State.UPLOADED, 0, null);
            } catch (CanNotCreateHistoryException ignore) {}

            throw new CanNotSaveFromFileException(e.getMessage());
        }
    }

    @Override
    public FileDTO load(String fileName, String ownerLogin) throws CanNotLoadFileException {
        if (!fileUploadHistoryDAO.checkFileOwner(fileName, ownerLogin)){
            throw new CanNotLoadFileException("Отказано в доступе!");
        }
        return fileStorageService.getFile(fileName);
    }
}
