package com.kszamosy.model.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.kszamosy.model.entity.NbaMatchDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

import static com.kszamosy.factory.NbaMatchDetailFactory.createDetail;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class NbaCommentedMatchResource extends NbaMatchResource {

    public NbaCommentedMatchResource(NbaMatchDetail nbaMatchDetail, List<String> comments) {
        this.id = nbaMatchDetail.getId();
        this.date = nbaMatchDetail.getDate();
        this.homeTeam = nbaMatchDetail.getHomeTeam();
        this.homeScore = nbaMatchDetail.getHomeScore();
        this.awayTeam = nbaMatchDetail.getAwayTeam();
        this.awayScore = nbaMatchDetail.getAwayScore();
        this.playerScores = nbaMatchDetail.getPlayerScores();
        this.comments = comments;
    }

    public NbaCommentedMatchResource(NbaMatchResource nbaMatchResource, List<String> comments,
                                     Map<String, Integer> playerScores) {
        this(createDetail(nbaMatchResource, playerScores), comments);
    }

    @JsonInclude(Include.NON_EMPTY)
    private List<String> comments;
    private Map<String, Integer> playerScores;

}
