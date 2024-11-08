package ru.itmo.is_lab1.domain.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.criteria.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.itmo.is_lab1.domain.entity.MusicBand;

import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class QueryFilter {
    private SortDirection sortDirection = SortDirection.ASC;

    private FullJoinedMusicBandColumn sortColumn = FullJoinedMusicBandColumn.ID;

    private int pageNumber = 0;

    private int pageSize = 50;

    private Set<Criteria> criteria = new HashSet<>();

    public static From<?, ?> getFrom(FullJoinedMusicBandColumn column, Root<MusicBand> rootQuery){
        return switch (column){
            case COORDINATE_X, COORDINATE_Y -> rootQuery.join(MusicBand.Attributes.COORDINATES);
            case BEST_ALBUM_NAME, BEST_ALBUM_SALES -> rootQuery.join(MusicBand.Attributes.BEST_ALBUM);
            case STUDIO_ADDRESS -> rootQuery.join(MusicBand.Attributes.STUDIO);
            case USER_LOGIN -> rootQuery.join(MusicBand.Attributes.OWNER);
            default -> rootQuery;
        };
    }

    @Data
    public static class Criteria{
        @NotNull
        private FullJoinedMusicBandColumn filteringColumn;

        @NotNull
        private String filteringValue;

        private Boolean andCriteria = true;

        public Predicate toPredicate(CriteriaBuilder criteriaBuilder, Root<MusicBand> rootQuery){
            var root = QueryFilter.getFrom(filteringColumn, rootQuery);
            return criteriaBuilder.equal(root.get(filteringColumn.toString()).as(String.class), filteringValue);
        }
    }

    public enum SortDirection{
        ASC,
        DESC
    }
}
