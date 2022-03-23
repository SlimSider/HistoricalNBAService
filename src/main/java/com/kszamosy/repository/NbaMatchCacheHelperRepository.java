package com.kszamosy.repository;

import com.kszamosy.model.entity.NbaMatchCacheHelper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NbaMatchCacheHelperRepository extends JpaRepository<NbaMatchCacheHelper, String> {
}
