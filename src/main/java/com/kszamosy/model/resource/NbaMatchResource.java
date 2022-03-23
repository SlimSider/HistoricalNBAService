package com.kszamosy.model.resource;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NbaMatchResource extends NbaApiResponseBody {

    protected Long id;
    protected String date;
    protected String homeTeam;
    protected String awayTeam;
    @JsonAlias("home_team_score")
    protected int homeScore;
    @JsonAlias("visitor_team_score")
    protected int awayScore;

    @JsonProperty("home_team")
    private void unpackHomeTeam(Map<String, Object> homeTeam) {
        this.homeTeam = homeTeam.get("full_name").toString();
    }

    @JsonProperty("visitor_team")
    private void unpackAwayTeam(Map<String, Object> awayTeam) {
        this.awayTeam = awayTeam.get("full_name").toString();
    }

}
