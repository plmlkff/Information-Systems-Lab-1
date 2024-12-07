package ru.itmo.is_lab1.service.impl;

import jakarta.inject.Inject;
import ru.itmo.is_lab1.exceptions.domain.CanNotSaveEntityException;
import ru.itmo.is_lab1.exceptions.service.CanNotSaveFromFileException;
import ru.itmo.is_lab1.exceptions.util.JsonParserException;
import ru.itmo.is_lab1.interceptor.annotation.Transactional;
import ru.itmo.is_lab1.rest.dto.MusicBandDTO;
import ru.itmo.is_lab1.service.FileSaverService;
import ru.itmo.is_lab1.service.MusicBandService;
import ru.itmo.is_lab1.util.JsonParser;

public class JsonFileSaverServiceImpl implements FileSaverService {
    @Inject
    private MusicBandService musicBandService;

    @Transactional
    @Override
    public int save(byte[] fileBytes, String ownerLogin) throws CanNotSaveFromFileException {
        JsonParser jsonParser = new JsonParser();
        try {
            var musicBandDTOs = jsonParser.parseList(fileBytes);

            for (var musicBandDTO : musicBandDTOs){
                musicBandService.save(MusicBandDTO.toDomain(musicBandDTO), ownerLogin);
            }
            return musicBandDTOs.size();
        } catch (Exception e) {
            throw new CanNotSaveFromFileException(e.getMessage());
        }
    }
}
