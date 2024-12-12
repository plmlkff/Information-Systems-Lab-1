package ru.itmo.is_lab1.domain.dao.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import org.hibernate.query.Query;
import ru.itmo.is_lab1.domain.dao.FileUploadHistoryDAO;
import ru.itmo.is_lab1.domain.entity.FileUploadHistory;
import org.hibernate.Session;

@ApplicationScoped
public class FileUploadHistoryDAOImpl extends AbstractDAOImpl<FileUploadHistory, Long> implements FileUploadHistoryDAO {
    @Inject
    private Session session;

    public FileUploadHistoryDAOImpl() {
        super(FileUploadHistory.class);
    }

    @Override
    public boolean checkFileOwner(String fileName, String ownerLogin) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);

        var root = query.from(FileUploadHistory.class);

        query.select(criteriaBuilder.count(root));

        Predicate loginPredicate = criteriaBuilder.equal(root.get(FileUploadHistory.Fields.USER).as(String.class), ownerLogin);
        Predicate fileNamePredicate = criteriaBuilder.equal(root.get(FileUploadHistory.Fields.FILE_NAME).as(String.class), fileName);

        Predicate andPredicate = criteriaBuilder.and(loginPredicate, fileNamePredicate);

        query.where(andPredicate);

        Query<Long> hibernateQuery = session.createQuery(query);

        return hibernateQuery.getSingleResult() == 1;
    }
}
