package com.kszamosy.model.resource;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class NbaApiResponseMetaResource {

    @JsonAlias("total_pages")
    private int totalPages;
    @JsonAlias("current_page")
    private int currentPage;
    @JsonAlias("next_page")
    private int nextPage;
    @JsonAlias("per_page")
    private int perPage;
    @JsonAlias("total_count")
    private int totalCount;
}
