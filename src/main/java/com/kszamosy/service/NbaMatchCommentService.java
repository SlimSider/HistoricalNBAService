package com.kszamosy.service;

import com.kszamosy.exception.BadRequestException;
import com.kszamosy.exception.NotFoundException;
import com.kszamosy.model.entity.NbaMatchComment;
import com.kszamosy.repository.NbaCommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class NbaMatchCommentService {

    private final NbaCommentRepository nbaCommentRepository;
    private final NbaRapidApiService nbaRapidApiService;

    public NbaMatchCommentService(NbaCommentRepository nbaCommentRepository, NbaRapidApiService nbaRapidApiService) {
        this.nbaCommentRepository = nbaCommentRepository;
        this.nbaRapidApiService = nbaRapidApiService;
    }

    public List<NbaMatchComment> getComments() {
        log.info("Fetching comments");
        return nbaCommentRepository.findAll();
    }

    public List<NbaMatchComment> getCommentsByGame(Long gameId) {
        log.info("Fetching comments for gameId {}", gameId);
        return nbaCommentRepository.findByGameId(gameId);
    }

    public NbaMatchComment getCommentById(Long id) {
        log.info("Fetching comment for id {}", id);
        return nbaCommentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Couldn't find entity with id: " + id));
    }

    public NbaMatchComment saveComment(NbaMatchComment nbaMatchComment) {
        log.info("Saving comment {}", nbaMatchComment);

        nbaRapidApiService.getMatchById(nbaMatchComment.getGameId());

        Optional.ofNullable(nbaMatchComment.getId())
                .map(nbaCommentRepository::findById)
                .ifPresent(m -> {
                    throw new BadRequestException("Entity already exists with id: " + nbaMatchComment.getId());
                });

        return nbaCommentRepository.save(nbaMatchComment);
    }

    public NbaMatchComment updateComment(Long id, NbaMatchComment nbaMatchComment) {
        log.info("Updating comment {}", nbaMatchComment);
        if (isNull(nbaMatchComment.getId())) {
            nbaMatchComment.setId(id);
        } else if (!nbaMatchComment.getId().equals(id)) {
            throw new BadRequestException("Mismatch between provided id and resource");
        }

        var oldComment = getCommentById(id);
        if (!oldComment.getGameId().equals(nbaMatchComment.getGameId())) {
            nbaRapidApiService.getMatchById(nbaMatchComment.getGameId());
        }

        return nbaCommentRepository.save(nbaMatchComment);
    }

    public NbaMatchComment deleteComment(Long id) {
        log.info("Deleting comment for id {}", id);
        var entity = getCommentById(id);
        nbaCommentRepository.delete(entity);
        return entity;
    }

}
