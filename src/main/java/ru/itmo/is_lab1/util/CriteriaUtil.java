package ru.itmo.is_lab1.util;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.itmo.is_lab1.domain.filter.TableColumn;
import ru.itmo.is_lab1.domain.filter.QueryFilter;

public abstract class CriteriaUtil {
    public abstract <T> From<T, ?> makeNeededJoins(TableColumn column, Root<T> rootQuery);

    public abstract <T> Predicate toPredicate(QueryFilter.Criteria criteria, CriteriaBuilder criteriaBuilder, Root<T> rootQuery);
}
