package com.kszamosy.model.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class NbaCommentedMatchResource extends NbaMatchResource {

    public NbaCommentedMatchResource(NbaMatchResource nbaMatchResource, List<String> comments,
                                     Map<String, Integer> playerScores) {
        this.id = nbaMatchResource.id;
        this.date = nbaMatchResource.date;
        this.homeTeam = nbaMatchResource.homeTeam;
        this.homeScore = nbaMatchResource.homeScore;
        this.awayTeam = nbaMatchResource.awayTeam;
        this.awayScore = nbaMatchResource.awayScore;
        this.comments = comments;
        this.playerScores = playerScores;
    }

    @JsonInclude(Include.NON_EMPTY)
    private List<String> comments;
    private Map<String, Integer> playerScores;

}
