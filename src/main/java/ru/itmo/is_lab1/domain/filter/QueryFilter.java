package ru.itmo.is_lab1.domain.filter;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class QueryFilter {
    private SortDirection sortDirection = SortDirection.ASC;

    private TableColumn sortColumn = TableColumn.ID;

    private int pageNumber = 1;

    private int pageSize = 50;

    private Set<Criteria> criteria = new HashSet<>();

    @Data
    public static class Criteria{
        @NotNull
        private TableColumn filteringColumn;

        @NotNull
        private String filteringValue;

        private Boolean andCriteria = true;
    }

    public enum SortDirection{
        ASC,
        DESC
    }
}
