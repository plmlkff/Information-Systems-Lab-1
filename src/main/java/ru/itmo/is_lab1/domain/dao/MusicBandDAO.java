package ru.itmo.is_lab1.domain.dao;

import ru.itmo.is_lab1.domain.entity.MusicBand;
import ru.itmo.is_lab1.domain.filter.QueryFilter;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetAllEntitiesException;

import java.util.List;

public interface MusicBandDAO extends AbstractDAO<MusicBand, Integer> {
}
