package com.kszamosy.factory;

import com.kszamosy.model.entity.NbaMatchDetail;
import com.kszamosy.model.resource.NbaCommentedMatchResource;
import com.kszamosy.model.resource.NbaMatchResource;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class NbaCommentedMatchResourceFactory {

    public static NbaCommentedMatchResource createCommentedMatch(NbaMatchResource nbaMatchResource, List<String> comments,
                                                                 Map<String, Integer> playerStats) {
        return new NbaCommentedMatchResource(nbaMatchResource, comments, playerStats);
    }

    public static NbaCommentedMatchResource createCommentedMatch(NbaMatchDetail nbaMatchDetail, List<String> comments) {
        return new NbaCommentedMatchResource(nbaMatchDetail, comments);
    }
}
