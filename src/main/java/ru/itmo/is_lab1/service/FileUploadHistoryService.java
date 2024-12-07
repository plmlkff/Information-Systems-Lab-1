package ru.itmo.is_lab1.service;

import ru.itmo.is_lab1.domain.entity.FileUploadHistory.State;
import ru.itmo.is_lab1.domain.entity.FileUploadHistory;
import ru.itmo.is_lab1.exceptions.service.CanNotCreateHistoryException;
import ru.itmo.is_lab1.exceptions.service.CanNotGetHistoryByOwnerLoginException;

import java.util.List;

public interface FileUploadHistoryService {
    FileUploadHistory createHistory(String ownerLogin, State state, Integer count) throws CanNotCreateHistoryException;

    List<FileUploadHistory> getAllHistoriesByUserLogin(String userLogin, int pageSize, int pageNumber) throws CanNotGetHistoryByOwnerLoginException;
}
