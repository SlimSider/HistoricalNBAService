package com.kszamosy.service;

import com.kszamosy.model.resource.NbaApiResponseMetaResource;
import com.kszamosy.model.resource.NbaApiResponseResource;
import com.kszamosy.model.resource.NbaMatchResource;
import com.kszamosy.model.resource.NbaPlayerStatResource;
import com.kszamosy.util.DeserializerUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NbaRapidApiServiceTest {

    @Mock
    private HttpClientService httpClientService;

    @InjectMocks
    private NbaRapidApiService nbaRapidApiService;

    @Test
    public void getMatchesByDate_successful() {
        var res = new NbaApiResponseResource<>();
        var match = new NbaMatchResource();
        var meta = new NbaApiResponseMetaResource();

        meta.setCurrentPage(1);
        meta.setTotalPages(1);

        match.setId(5L);
        res.setData(List.of(match));
        res.setMeta(meta);

        when(httpClientService.get(any(), any())).thenReturn(Optional.of("RESULT"));
        try (MockedStatic<DeserializerUtil> util = Mockito.mockStatic(DeserializerUtil.class)) {
            util.when(() -> DeserializerUtil.deserializeWithParam(any(), any())).thenReturn(res);

            var matches = nbaRapidApiService.getMatchesByDate("2022-03-23");

            assertNotNull(matches);
            assertFalse(matches.isEmpty());
            assertEquals(List.of(match), matches);
        }
    }

    @Test
    public void getMatchById_successful() {
        var match = new NbaMatchResource();
        match.setId(5L);

        when(httpClientService.get(any(), any())).thenReturn(Optional.of("RESULT"));
        try (MockedStatic<DeserializerUtil> util = Mockito.mockStatic(DeserializerUtil.class)) {
            util.when(() -> DeserializerUtil.deserializeSimple(any(), any())).thenReturn(match);

            var result = nbaRapidApiService.getMatchById(5L);

            assertNotNull(result);
            assertEquals(match, result);
        }
    }

    @Test
    public void getStatsByGameId_successful() {
        var res = new NbaApiResponseResource<>();
        var secondRes = new NbaApiResponseResource<>();
        var stat = new NbaPlayerStatResource();
        var meta = new NbaApiResponseMetaResource();
        var secondMeta = new NbaApiResponseMetaResource();

        stat.setName("Test Player");

        meta.setCurrentPage(1);
        meta.setNextPage(1);
        meta.setTotalPages(2);
        secondMeta.setCurrentPage(2);
        secondMeta.setNextPage(2);
        secondMeta.setTotalPages(2);

        res.setData(List.of(stat));
        res.setMeta(meta);
        secondRes.setData(List.of(stat));
        secondRes.setMeta(secondMeta);

        when(httpClientService.get(any(), any())).thenReturn(Optional.of("RESULT"));
        try (MockedStatic<DeserializerUtil> util = Mockito.mockStatic(DeserializerUtil.class)) {
            util.when(() -> DeserializerUtil.deserializeWithParam(any(), any())).thenReturn(res, secondRes);

            var matches = nbaRapidApiService.getStatsByGameId(5L);

            assertNotNull(matches);
            assertFalse(matches.isEmpty());
            assertEquals(List.of(stat, stat), matches);
        }
    }
}