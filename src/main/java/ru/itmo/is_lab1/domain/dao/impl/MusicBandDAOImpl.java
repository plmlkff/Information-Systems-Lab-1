package ru.itmo.is_lab1.domain.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import ru.itmo.is_lab1.domain.dao.MusicBandDAO;
import ru.itmo.is_lab1.domain.entity.MusicBand;

@ApplicationScoped
public class MusicBandDAOImpl extends AbstractDAOImpl<MusicBand, Integer> implements MusicBandDAO {
    public MusicBandDAOImpl() {
        super(MusicBand.class);
    }
}
