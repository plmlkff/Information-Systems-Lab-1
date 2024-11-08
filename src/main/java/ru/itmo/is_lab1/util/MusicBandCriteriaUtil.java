package ru.itmo.is_lab1.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.itmo.is_lab1.domain.entity.MusicBand;
import ru.itmo.is_lab1.domain.filter.TableColumn;
import ru.itmo.is_lab1.domain.filter.QueryFilter;

@ApplicationScoped
public class MusicBandCriteriaUtil extends CriteriaUtil{
    @Override
    public <T> From<T, ?> makeNeededJoins(TableColumn column, Root<T> rootQuery){
        return switch (column){
            case COORDINATE_X, COORDINATE_Y -> rootQuery.join(MusicBand.Attributes.COORDINATES);
            case BEST_ALBUM_NAME, BEST_ALBUM_SALES -> rootQuery.join(MusicBand.Attributes.BEST_ALBUM);
            case STUDIO_ADDRESS -> rootQuery.join(MusicBand.Attributes.STUDIO);
            case USER_LOGIN -> rootQuery.join(MusicBand.Attributes.OWNER);
            default -> rootQuery;
        };
    }

    @Override
    public <T> Predicate toPredicate(QueryFilter.Criteria criteria, CriteriaBuilder criteriaBuilder, Root<T> rootQuery){
        var filteringColumn = criteria.getFilteringColumn();
        var filteringValue = criteria.getFilteringValue();
        var root = makeNeededJoins(filteringColumn, rootQuery);
        return criteriaBuilder.equal(root.get(filteringColumn.toString()).as(String.class), filteringValue);
    }
}
