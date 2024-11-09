package ru.itmo.is_lab1.domain.dao;

import ru.itmo.is_lab1.domain.entity.MusicGenre;
import ru.itmo.is_lab1.exceptions.domain.CanNotExecuteFunctionException;

public interface FunctionsDAO {
    int calculateTotalAlbumsCount() throws CanNotExecuteFunctionException;

    int countAlbumsGreaterThan(int value) throws CanNotExecuteFunctionException;

    void increaseParticipants(int id) throws CanNotExecuteFunctionException;

    void deleteMusicBand(int id) throws CanNotExecuteFunctionException;

    int countMusicBandsByGenre(MusicGenre genre) throws CanNotExecuteFunctionException;
}
