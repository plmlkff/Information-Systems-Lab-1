package ru.itmo.is_lab1.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ru.itmo.is_lab1.domain.dao.FileUploadHistoryDAO;
import ru.itmo.is_lab1.domain.dao.UserDAO;
import ru.itmo.is_lab1.domain.entity.FileUploadHistory;
import ru.itmo.is_lab1.domain.entity.FileUploadHistory.State;
import ru.itmo.is_lab1.domain.entity.User;
import ru.itmo.is_lab1.domain.entity.UserRole;
import ru.itmo.is_lab1.domain.filter.QueryFilter;
import ru.itmo.is_lab1.domain.filter.TableColumn;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetAllEntitiesException;
import ru.itmo.is_lab1.exceptions.domain.CanNotGetByIdEntityException;
import ru.itmo.is_lab1.exceptions.domain.CanNotSaveEntityException;
import ru.itmo.is_lab1.exceptions.service.CanNotCreateHistoryException;
import ru.itmo.is_lab1.exceptions.service.CanNotGetHistoryByOwnerLoginException;
import ru.itmo.is_lab1.service.FileUploadHistoryService;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static ru.itmo.is_lab1.util.HttpResponse.error;

@ApplicationScoped
public class FileUploadHistoryServiceImpl implements FileUploadHistoryService {
    @Inject
    private FileUploadHistoryDAO fileUploadHistoryDAO;
    @Inject
    private UserDAO userDAO;

    @Override
    public FileUploadHistory createHistory(String ownerLogin, State state, Integer count, String fileName) throws CanNotCreateHistoryException {
        try {
            FileUploadHistory fileUploadHistory = new FileUploadHistory();

            User user = userDAO.findById(ownerLogin);
            if (user == null) throw new CanNotCreateHistoryException("User does not exist!");

            fileUploadHistory.setCount(count);
            fileUploadHistory.setState(state);
            fileUploadHistory.setTime(new Date());
            fileUploadHistory.setUser(user);
            fileUploadHistory.setFileName(fileName);

            return fileUploadHistoryDAO.save(fileUploadHistory);
        } catch (CanNotGetByIdEntityException e) {
            throw new CanNotCreateHistoryException("User does not exist!");
        } catch (CanNotSaveEntityException e) {
            throw new CanNotCreateHistoryException("Can not save history!");
        }
    }

    @Override
    public List<FileUploadHistory> getAllHistoriesByUserLogin(String userLogin, int pageSize, int pageNumber) throws CanNotGetHistoryByOwnerLoginException {
        try {
            User user = userDAO.findById(userLogin);
            if (user == null) throw new CanNotGetHistoryByOwnerLoginException("Owner does not exist!");
            QueryFilter queryFilter = prepareQueryByUser(user, pageSize, pageNumber);
            return fileUploadHistoryDAO.findAll(queryFilter);
        } catch (CanNotGetByIdEntityException e) {
            throw new CanNotGetHistoryByOwnerLoginException("Owner does not exist!");
        } catch (CanNotGetAllEntitiesException e) {
            throw new CanNotGetHistoryByOwnerLoginException("Can not get the history of uploads!");
        }
    }

    private QueryFilter prepareQueryByUser(User user, int pageSize, int pageNumber){
        QueryFilter queryFilter = new QueryFilter();
        queryFilter.setPageSize(pageSize);
        queryFilter.setPageNumber(pageNumber);
        if (user.getRole().equals(UserRole.USER)) {
            QueryFilter.Criteria criteria = new QueryFilter.Criteria();
            criteria.setFilteringValue(user.getLogin());
            criteria.setFilteringColumn(TableColumn.FILE_UPLOAD_HISTORY_USER_LOGIN);
            queryFilter.setCriteria(Set.of(criteria));
        }
        return queryFilter;
    }
}
