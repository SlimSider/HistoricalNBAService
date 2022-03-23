package com.kszamosy.service;

import com.kszamosy.model.entity.NbaMatchDetail;
import com.kszamosy.repository.NbaMatchDetailRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NbaMatchDetailServiceTest {

    @Mock
    private NbaMatchDetailRepository nbaMatchDetailRepository;

    @InjectMocks
    private NbaMatchDetailService nbaMatchDetailService;

    @Test
    void getMatchesByDate_successful() {
        var detail = new NbaMatchDetail();
        detail.setId(1L);
        when(nbaMatchDetailRepository.findByDate(any())).thenReturn(List.of(detail));

        var result = nbaMatchDetailService.getMatchesByDate("2022-03-23");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(List.of(detail), result);
    }

    @Test
    void getMatchById_successful() {
        var detail = new NbaMatchDetail();
        detail.setId(1L);
        when(nbaMatchDetailRepository.findById(any())).thenReturn(Optional.of(detail));
        nbaMatchDetailService.getMatchById(1L).ifPresent(
                result -> assertEquals(detail, result));
    }

    @Test
    void save_successful() {
        var detail = new NbaMatchDetail();
        detail.setId(1L);
        when(nbaMatchDetailRepository.save(any())).thenReturn(detail);

        var result = nbaMatchDetailService.save(any());

        assertNotNull(result);
        assertEquals(detail, result);
    }
}