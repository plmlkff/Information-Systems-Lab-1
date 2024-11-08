package ru.itmo.is_lab1.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import ru.itmo.is_lab1.domain.entity.EntityChangeHistory;
import ru.itmo.is_lab1.domain.entity.MusicBand;
import ru.itmo.is_lab1.domain.filter.TableColumn;
import ru.itmo.is_lab1.domain.filter.QueryFilter;

@ApplicationScoped
public class MusicBandCriteriaUtil extends CriteriaUtil{
    @Override
    public <T> From<T, ?> makeNeededJoins(TableColumn column, Root<T> rootQuery){
        return switch (column){
            case MUSIC_BAND_COORDINATE_X, MUSIC_BAND_COORDINATE_Y -> rootQuery.join(MusicBand.Attributes.COORDINATES);
            case MUSIC_BAND_BEST_ALBUM_NAME, MUSIC_BAND_BEST_ALBUM_SALES -> rootQuery.join(MusicBand.Attributes.BEST_ALBUM);
            case MUSIC_BAND_STUDIO_ADDRESS -> rootQuery.join(MusicBand.Attributes.STUDIO);
            case MUSIC_BAND_USER_LOGIN -> rootQuery.join(MusicBand.Attributes.OWNER);
            case ENTITY_CHANGE_HISTORY_USER_LOGIN -> rootQuery.join(EntityChangeHistory.Attributes.USER);
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
