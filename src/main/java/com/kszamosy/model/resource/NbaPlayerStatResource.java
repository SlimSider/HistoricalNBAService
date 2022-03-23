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
public class NbaPlayerStatResource extends NbaApiResponseBody {

    private String name;
    @JsonAlias("pts")
    private int points;

    @JsonProperty("player")
    private void unpackPlayerName(Map<String, Object> player) {
        this.name = player.get("first_name").toString() + " " + player.get("last_name").toString();
    }

}
