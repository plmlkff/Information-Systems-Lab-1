package ru.itmo.is_lab1.service;

import ru.itmo.is_lab1.exceptions.service.CanNotLoadFileException;
import ru.itmo.is_lab1.exceptions.service.CanNotSaveFromFileException;
import ru.itmo.is_lab1.rest.dto.FileDTO;

public interface FileService {
    int save(FileDTO fileDTO, String ownerLogin) throws CanNotSaveFromFileException;

    FileDTO load(String fileName, String ownerLogin) throws CanNotLoadFileException;
}
