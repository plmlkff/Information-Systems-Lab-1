package ru.itmo.is_lab1.domain.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import ru.itmo.is_lab1.domain.dao.FileUploadHistoryDAO;
import ru.itmo.is_lab1.domain.entity.FileUploadHistory;

@ApplicationScoped
public class FileUploadHistoryDAOImpl extends AbstractDAOImpl<FileUploadHistory, Long> implements FileUploadHistoryDAO {
    public FileUploadHistoryDAOImpl() {
        super(FileUploadHistory.class);
    }
}
