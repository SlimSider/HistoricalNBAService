package com.kszamosy.service;

import com.kszamosy.model.entity.NbaMatchCacheHelper;
import com.kszamosy.repository.NbaMatchCacheHelperRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NbaMatchCacheHelperService {

    private final NbaMatchCacheHelperRepository detailHelperRepository;

    public NbaMatchCacheHelperService(NbaMatchCacheHelperRepository detailHelperRepository) {
        this.detailHelperRepository = detailHelperRepository;
    }

    public Optional<NbaMatchCacheHelper> getByDate(String date) {
        return detailHelperRepository.findById(date);
    }

    public NbaMatchCacheHelper save(NbaMatchCacheHelper detailHelper) {
        return detailHelperRepository.save(detailHelper);
    }
}
