package ru.itmo.is_lab1.domain.dao.impl;

import ru.itmo.is_lab1.domain.dao.AlbumDAO;
import ru.itmo.is_lab1.domain.entity.Album;

public class AlbumDAOImpl extends AbstractDAOImpl<Album, Integer> implements AlbumDAO {
    public AlbumDAOImpl() {
        super(Album.class);
    }
}
