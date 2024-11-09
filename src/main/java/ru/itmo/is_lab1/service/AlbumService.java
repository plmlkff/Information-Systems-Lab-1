package ru.itmo.is_lab1.service;

import ru.itmo.is_lab1.domain.entity.Album;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;

public interface AlbumService {
    Album getById(Integer id) throws CanNotGetByIdEntityException;
}
