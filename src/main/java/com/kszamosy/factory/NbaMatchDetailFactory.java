package com.kszamosy.factory;

import com.kszamosy.model.entity.NbaMatchDetail;
import com.kszamosy.model.resource.NbaMatchResource;
import lombok.NoArgsConstructor;

import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class NbaMatchDetailFactory {

    public static NbaMatchDetail createDetail(NbaMatchResource resource, Map<String, Integer> playerScores) {
        return new NbaMatchDetail(resource, playerScores);
    }
}
