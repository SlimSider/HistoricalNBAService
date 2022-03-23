package com.kszamosy.repository;

import com.kszamosy.model.entity.NbaMatchComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NbaMatchCommentRepository extends JpaRepository<NbaMatchComment, Long> {

    List<NbaMatchComment> findByGameId(Long gameId);
}
