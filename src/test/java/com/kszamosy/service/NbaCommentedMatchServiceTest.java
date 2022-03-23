package com.kszamosy.service;

import com.kszamosy.exception.BadRequestException;
import com.kszamosy.model.entity.NbaMatchComment;
import com.kszamosy.model.resource.NbaMatchResource;
import com.kszamosy.model.resource.NbaPlayerStatResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static com.kszamosy.factory.NbaCommentedMatchResourceFactory.createCommentedMatch;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NbaCommentedMatchServiceTest {

    @Mock
    private NbaMatchCommentService nbaMatchCommentService;

    @Mock
    private NbaRapidApiService nbaRapidApiService;

    @Mock
    private NbaMatchDetailService nbaMatchDetailService;

    @Mock
    private NbaMatchCacheHelperService nbaMatchCacheHelperService;

    @InjectMocks
    private NbaCommentedMatchService nbaCommentedMatchService;

    @Test
    public void getMatchesByDate_invalidDateFormat() {
        assertThrows(BadRequestException.class, () -> nbaCommentedMatchService.getMatchesByDate("3020-03-23"));
    }

    @Test
    public void getMatchesByDate_successful() {
        var match = new NbaMatchResource();
        match.setId(1L);
        var comment = new NbaMatchComment();
        comment.setId(2L);
        comment.setComment("Test");
        var stat = new NbaPlayerStatResource();
        stat.setName("Test Player");
        stat.setPoints(10);

        var commentedMatch = createCommentedMatch(match, List.of("Test"), Map.of(stat.getName(), stat.getPoints()));

        when(nbaMatchCommentService.getCommentsByGame(any())).thenReturn(List.of(comment));
        when(nbaRapidApiService.getStatsByGameId(any())).thenReturn(List.of(stat));
        when(nbaRapidApiService.getMatchesByDate(any())).thenReturn(List.of(match));

        var result = nbaCommentedMatchService.getMatchesByDate("2020-03-23");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(List.of(commentedMatch), result);
    }

    @Test
    public void getMatchById_successful() {
        var match = new NbaMatchResource();
        match.setId(1L);
        var comment = new NbaMatchComment();
        comment.setId(2L);
        comment.setComment("Test");
        var stat = new NbaPlayerStatResource();
        stat.setName("Test Player");
        stat.setPoints(10);

        var commentedMatch = createCommentedMatch(match, List.of("Test"), Map.of(stat.getName(), stat.getPoints()));

        when(nbaMatchCommentService.getCommentsByGame(any())).thenReturn(List.of(comment));
        when(nbaRapidApiService.getStatsByGameId(any())).thenReturn(List.of(stat));
        when(nbaRapidApiService.getMatchById(any())).thenReturn(match);

        var result = nbaCommentedMatchService.getMatchById(1L);

        assertNotNull(result);
        assertEquals(commentedMatch, result);
    }
}