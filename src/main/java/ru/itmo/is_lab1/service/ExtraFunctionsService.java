package ru.itmo.is_lab1.service;

import ru.itmo.is_lab1.domain.entity.MusicBand;
import ru.itmo.is_lab1.domain.entity.MusicGenre;
import ru.itmo.is_lab1.exceptions.domain.CanNotExecuteFunctionException;

public interface ExtraFunctionsService {
    int calculateTotalAlbumsCount() throws CanNotExecuteFunctionException;

    int countAlbumsGreaterThan(int value) throws CanNotExecuteFunctionException;

    MusicBand increaseParticipants(int id, String userLogin) throws CanNotExecuteFunctionException;

    MusicBand deleteMusicBand(int id, String userLogin) throws CanNotExecuteFunctionException;

    int countMusicBandsByGenre(MusicGenre genre) throws CanNotExecuteFunctionException;
}
