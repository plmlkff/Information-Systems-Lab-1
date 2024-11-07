package ru.itmo.is_lab1.domain.filter;

import jakarta.persistence.criteria.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.itmo.is_lab1.domain.entity.MusicBand;

import java.util.Set;

@Data
public class QueryFilter {
    private SortDirection sortDirection;

    private FullJoinedMusicBandColumn sortColumn;

    private int pageNumber;

    private int pageSize;

    private Set<Criteria> criteria;

    public static From<?, ?> getFrom(FullJoinedMusicBandColumn column, Root<MusicBand> rootQuery){
        return switch (column){
            case COORDINATE_X, COORDINATE_Y -> rootQuery.join("coordinates");
            case BEST_ALBUM_NAME, BEST_ALBUM_SALES -> rootQuery.join("bestAlbum");
            case STUDIO_ADDRESS -> rootQuery.join("studio");
            case USER_LOGIN -> rootQuery.join("owner");
            default -> rootQuery;
        };
    }

    @Data
    public static class Criteria{
        @NotNull
        private FullJoinedMusicBandColumn filteringColumn;

        @NotNull
        private String filteringValue;

        @NotNull
        private Boolean andCriteria;

        public Predicate toPredicate(CriteriaBuilder criteriaBuilder, Root<MusicBand> rootQuery){
            var root = QueryFilter.getFrom(filteringColumn, rootQuery);
            return criteriaBuilder.equal(root.get(filteringColumn.toString()).as(String.class), filteringValue);
        }
    }

    public enum SortDirection{
        ASC,
        DESC;
    }
}
