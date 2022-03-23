package com.kszamosy.service;

import com.kszamosy.exception.BadRequestException;
import com.kszamosy.model.entity.NbaMatchCacheHelper;
import com.kszamosy.model.entity.NbaMatchComment;
import com.kszamosy.model.entity.NbaMatchDetail;
import com.kszamosy.model.resource.NbaCommentedMatchResource;
import com.kszamosy.model.resource.NbaMatchResource;
import com.kszamosy.model.resource.NbaPlayerStatResource;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.kszamosy.factory.NbaCommentedMatchResourceFactory.createCommentedMatch;
import static com.kszamosy.factory.NbaMatchDetailFactory.createDetail;
import static java.util.stream.Collectors.toMap;

@Service
public class NbaCommentedMatchService {

    private static final String DATE_FORMAT = "^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";

    private final NbaRapidApiService nbaRapidApiService;
    private final NbaMatchCommentService nbaMatchCommentService;
    private final NbaMatchDetailService nbaMatchDetailService;
    private final NbaMatchCacheHelperService nbaMatchCacheHelperService;

    public NbaCommentedMatchService(NbaRapidApiService nbaRapidApiService, NbaMatchCommentService nbaMatchCommentService, NbaMatchDetailService nbaMatchDetailService, NbaMatchCacheHelperService nbaMatchCacheHelperService) {
        this.nbaRapidApiService = nbaRapidApiService;
        this.nbaMatchCommentService = nbaMatchCommentService;
        this.nbaMatchDetailService = nbaMatchDetailService;
        this.nbaMatchCacheHelperService = nbaMatchCacheHelperService;
    }

    public List<NbaCommentedMatchResource> getMatchesByDate(String date) {
        if (!date.matches(DATE_FORMAT)) {
            throw new BadRequestException("Wrong param provided. Please provide a YYYY-MM-DD formatted date!");
        }

        if (nbaMatchCacheHelperService.getByDate(date).isPresent()) {
            return nbaMatchDetailService.getMatchesByDate(date)
                    .stream()
                    .map(this::enrichMatchResource)
                    .toList();
        }

        nbaMatchCacheHelperService.save(new NbaMatchCacheHelper(date));
        return nbaRapidApiService.getMatchesByDate(date).stream()
                .map(this::enrichMatchResource)
                .toList();
    }

    public NbaCommentedMatchResource getMatchById(Long id) {
        return nbaMatchDetailService.getMatchById(id)
                .map(this::enrichMatchResource)
                .orElseGet(() -> {
                    var nbaMatch = nbaRapidApiService.getMatchById(id);
                    return enrichMatchResource(nbaMatch);
                });
    }

    private NbaCommentedMatchResource enrichMatchResource(NbaMatchResource match) {
        var comments = nbaMatchCommentService.getCommentsByGame(match.getId());
        var stats = nbaRapidApiService.getStatsByGameId(match.getId());

        return enrichMatchResource(match, comments, stats);
    }

    private NbaCommentedMatchResource enrichMatchResource(NbaMatchDetail match) {
        var comments = nbaMatchCommentService.getCommentsByGame(match.getId());

        return enrichMatchResource(match, comments);
    }

    private NbaCommentedMatchResource enrichMatchResource(NbaMatchResource match, List<NbaMatchComment> matchComments,
                                                          List<NbaPlayerStatResource> playerStats) {
        var comments = matchComments.stream()
                .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
                .map(NbaMatchComment::getComment)
                .toList();

        var playerScores = playerStats.stream()
                .filter(stat -> stat.getPoints() > 0)
                .collect(toMap(NbaPlayerStatResource::getName, NbaPlayerStatResource::getPoints));

        nbaMatchDetailService.save(createDetail(match, playerScores));
        return createCommentedMatch(match, comments, playerScores);
    }

    private NbaCommentedMatchResource enrichMatchResource(NbaMatchDetail match, List<NbaMatchComment> matchComments) {
        var comments = matchComments.stream()
                .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
                .map(NbaMatchComment::getComment)
                .toList();

        return createCommentedMatch(match, comments);
    }
}
