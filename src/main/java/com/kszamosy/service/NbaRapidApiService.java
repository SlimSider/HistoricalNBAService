package com.kszamosy.service;

import com.kszamosy.exception.NotFoundException;
import com.kszamosy.model.resource.NbaApiResponseBody;
import com.kszamosy.model.resource.NbaApiResponseResource;
import com.kszamosy.model.resource.NbaMatchResource;
import com.kszamosy.model.resource.NbaPlayerStatResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.kszamosy.util.DeserializerUtil.deserializeSimple;
import static com.kszamosy.util.DeserializerUtil.deserializeWithParam;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Slf4j
public class NbaRapidApiService {

    private static final String X_RAPID_API_KEY = "x-rapidapi-key";
    private static final String API_KEY = "2917c604cfmsh0c53f6e67edb214p1f073djsn4cf93873feeb";
    private static final String BASE_URL = "https://free-nba.p.rapidapi.com";

    private final HttpClientService httpClientService;

    public NbaRapidApiService(HttpClientService httpClientService) {
        this.httpClientService = httpClientService;
    }

    public List<NbaMatchResource> getMatchesByDate(String date) {
        log.info("Fetching games for date {}", date);

        var res = collectPageResponses(NbaMatchResource.class, "/games",
                Map.of("dates[]", date));
        log.info("Fetched games for date {}. Found {} games.", date, res.size());

        return res;
    }

    public NbaMatchResource getMatchById(Long gameId) {
        log.info("Fetching game for gameId {}", gameId);

        return httpClientService.get("https://free-nba.p.rapidapi.com/games/" + gameId, X_RAPID_API_KEY, API_KEY)
                .map(body -> deserializeSimple(body, NbaMatchResource.class))
                .orElseThrow(() -> new NotFoundException("Couldn't find nba game for provided gameId: " + gameId));
    }

    public List<NbaPlayerStatResource> getStatsByGameId(Long gameId) {
        log.info("Fetching stats for gameId {}", gameId);

        var res = collectPageResponses(NbaPlayerStatResource.class, "/stats",
                Map.of("game_ids[]", gameId));

        log.info("Fetched stats for gameId {}. Found {} stats.", gameId, res.size());
        return res;
    }

    private <T extends NbaApiResponseBody> List<T> collectPageResponses(Class<T> clazz, String path, Map<String, Object> params) {
        var parameters = new HashMap<>(params);
        parameters.put("per_page", 35);

        var res = executeCall(clazz, path, parameters)
                .orElseThrow(() -> new NotFoundException("Couldn't find results for params: " + params));
        return res.getData();
    }

    private <T extends NbaApiResponseBody> Optional<NbaApiResponseResource<T>> executeCall(Class<T> clazz, String path, Map<String, Object> params) {
        log.info("Calling nba api on {} path with {} params", path, params);
        return httpClientService.get(BASE_URL + path + buildQuery(params), X_RAPID_API_KEY, API_KEY)
                .map(body -> deserializeWithParam(body, clazz));
    }

    private String buildQuery(Map<String, Object> params) {
        return params.entrySet().stream()
                .map(p -> encode(p.getKey(), UTF_8) + "=" + encode(p.getValue().toString(), UTF_8))
                .reduce((p1, p2) -> p1 + "&" + p2)
                .map(q -> "?" + q)
                .orElse("");
    }

}
