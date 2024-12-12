package ru.itmo.is_lab1.domain.dao;

import ru.itmo.is_lab1.domain.entity.FileUploadHistory;

public interface FileUploadHistoryDAO extends AbstractDAO<FileUploadHistory, Long>{
    boolean checkFileOwner(String fileName, String ownerLogin);
}
