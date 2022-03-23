package com.kszamosy.model.entity;

import com.kszamosy.model.resource.NbaMatchResource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NbaMatchDetail {

    @Id
    private Long id;

    private String date;
    private String homeTeam;
    private String awayTeam;
    private int homeScore;
    private int awayScore;

    @ElementCollection
    @CollectionTable(name = "player_scores_mapping",
            joinColumns = {@JoinColumn(name = "match_detail_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "player_name")
    @Column(name = "score")
    private Map<String, Integer> playerScores;

    public NbaMatchDetail(NbaMatchResource match, Map<String, Integer> playerScores) {
        this.id = match.getId();
        this.date = match.getDate();
        this.homeTeam = match.getHomeTeam();
        this.homeScore = match.getHomeScore();
        this.awayTeam = match.getAwayTeam();
        this.awayScore = match.getAwayScore();
        this.playerScores = playerScores;
    }

}
