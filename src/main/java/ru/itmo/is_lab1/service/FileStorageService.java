package ru.itmo.is_lab1.service;

import ru.itmo.is_lab1.exceptions.service.CanNotLoadFileException;
import ru.itmo.is_lab1.exceptions.service.CanNotSaveFileException;
import ru.itmo.is_lab1.rest.dto.FileDTO;

public interface FileStorageService {
    String saveFile(FileDTO file) throws CanNotSaveFileException;

    FileDTO getFile(String fileName) throws CanNotLoadFileException;
}
