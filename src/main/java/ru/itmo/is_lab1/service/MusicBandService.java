package ru.itmo.is_lab1.service;

import ru.itmo.is_lab1.domain.entity.MusicBand;
import ru.itmo.is_lab1.domain.entity.UserRole;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetAllEntitiesException;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotSaveEntityException;

import java.util.List;

public interface MusicBandService {
    List<MusicBand> getAll() throws CanNotGetAllEntitiesException;

    MusicBand save(MusicBand musicBand) throws CanNotSaveEntityException;

    MusicBand getById(Integer id) throws CanNotGetByIdEntityException;
}
