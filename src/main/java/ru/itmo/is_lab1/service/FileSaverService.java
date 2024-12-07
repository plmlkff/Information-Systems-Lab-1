package ru.itmo.is_lab1.service;

import ru.itmo.is_lab1.exceptions.service.CanNotSaveFromFileException;

public interface FileSaverService {
    int save(byte[] fileBytes, String ownerLogin) throws CanNotSaveFromFileException;
}
