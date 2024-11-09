package ru.itmo.is_lab1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.itmo.is_lab1.domain.dao.AlbumDAO;
import ru.itmo.is_lab1.domain.entity.Album;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;
import ru.itmo.is_lab1.service.AlbumService;

@ApplicationScoped
public class AlbumServiceImpl implements AlbumService {
    @Inject
    private AlbumDAO albumDAO;

    @Override
    public Album getById(Integer id) throws CanNotGetByIdEntityException {
        var album = albumDAO.findById(id);
        if (album == null) throw new CanNotGetByIdEntityException("Id does not exist!");
        return album;
    }
}
