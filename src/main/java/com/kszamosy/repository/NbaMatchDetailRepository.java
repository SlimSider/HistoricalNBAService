package com.kszamosy.repository;


import com.kszamosy.model.entity.NbaMatchDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NbaMatchDetailRepository extends JpaRepository<NbaMatchDetail, Long> {

    List<NbaMatchDetail> findByDate(String date);
}
