package com.kszamosy.service;

import com.kszamosy.model.entity.NbaMatchDetail;
import com.kszamosy.repository.NbaMatchDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NbaMatchDetailService {

    private final NbaMatchDetailRepository nbaMatchDetailRepository;

    public NbaMatchDetailService(NbaMatchDetailRepository nbaMatchDetailRepository) {
        this.nbaMatchDetailRepository = nbaMatchDetailRepository;
    }

    public List<NbaMatchDetail> getMatchesByDate(String date) {
        return nbaMatchDetailRepository.findByDate(date);
    }

    public Optional<NbaMatchDetail> getMatchById(Long id) {
        return nbaMatchDetailRepository.findById(id);
    }

    public NbaMatchDetail save(NbaMatchDetail nbaMatchDetail) {
        return nbaMatchDetailRepository.save(nbaMatchDetail);
    }

}
