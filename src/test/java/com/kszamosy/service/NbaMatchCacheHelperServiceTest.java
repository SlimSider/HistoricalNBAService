package com.kszamosy.service;

import com.kszamosy.model.entity.NbaMatchCacheHelper;
import com.kszamosy.model.entity.NbaMatchComment;
import com.kszamosy.repository.NbaMatchCacheHelperRepository;
import com.kszamosy.repository.NbaMatchCommentRepository;
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
class NbaMatchCacheHelperServiceTest {

    @Mock
    private NbaMatchCacheHelperRepository nbaMatchCacheHelperRepository;

    @InjectMocks
    private NbaMatchCacheHelperService nbaMatchCacheHelperService;

    @Test
    void getByDate_successful() {
        var cacheHelper = new NbaMatchCacheHelper();
        cacheHelper.setDate("2022-03-23");
        when(nbaMatchCacheHelperRepository.findById(any())).thenReturn(Optional.of(cacheHelper));

        nbaMatchCacheHelperService.getByDate("2022-03-23").ifPresent(result -> assertEquals(cacheHelper, result));
    }

    @Test
    void save_successful() {
        var cacheHelper = new NbaMatchCacheHelper();
        cacheHelper.setDate("2022-03-23");
        when(nbaMatchCacheHelperRepository.save(any())).thenReturn(cacheHelper);

        var result = nbaMatchCacheHelperService.save(cacheHelper);

        assertNotNull(result);
        assertEquals(cacheHelper, result);
    }
}