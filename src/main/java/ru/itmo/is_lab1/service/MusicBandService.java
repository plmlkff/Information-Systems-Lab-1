package ru.itmo.is_lab1.service;

import ru.itmo.is_lab1.domain.entity.MusicBand;
import ru.itmo.is_lab1.domain.filter.QueryFilter;
import ru.itmo.is_lab1.exceptions.domain.*;

import java.util.List;

public interface MusicBandService {
    MusicBand updateById(MusicBand newMusicBand, String userLogin) throws CanNotUpdateEntityException;

    Integer deleteById(Integer id, String ownerLogin) throws CanNotDeleteEntityException;

    @Deprecated
    List<MusicBand> getAll() throws CanNotGetAllEntitiesException;

    List<MusicBand> getAll(QueryFilter queryFilter) throws CanNotGetAllEntitiesException;

    MusicBand save(MusicBand musicBand, String ownerLogin) throws CanNotSaveEntityException;

    MusicBand getById(Integer id) throws CanNotGetByIdEntityException;
}
