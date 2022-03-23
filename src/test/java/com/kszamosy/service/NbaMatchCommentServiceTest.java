package com.kszamosy.service;

import com.kszamosy.exception.BadRequestException;
import com.kszamosy.exception.NotFoundException;
import com.kszamosy.model.entity.NbaMatchComment;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NbaMatchCommentServiceTest {

    @Mock
    private NbaMatchCommentRepository nbaCommentRepository;

    @Mock
    private NbaRapidApiService nbaRapidApiService;

    @InjectMocks
    private NbaMatchCommentService nbaMatchCommentService;

    @Test
    public void getComments_successful() {
        var comment = new NbaMatchComment();
        comment.setId(1L);
        when(nbaCommentRepository.findAll()).thenReturn(List.of(comment));

        var comments = nbaMatchCommentService.getComments();

        assertNotNull(comments);
        assertFalse(comments.isEmpty());
        assertEquals(List.of(comment), comments);
    }

    @Test
    public void getCommentsById_successful() {
        var comment = new NbaMatchComment();
        comment.setId(1L);
        when(nbaCommentRepository.findById(any())).thenReturn(Optional.of(comment));

        var result = nbaMatchCommentService.getCommentById(1L);

        assertNotNull(result);
        assertEquals(comment, result);
    }

    @Test
    public void getCommentsById_notFound() {
        when(nbaCommentRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> nbaMatchCommentService.getCommentById(1L));
    }

    @Test
    public void getCommentsByGameId_successful() {
        var comment = new NbaMatchComment();
        comment.setId(1L);
        when(nbaCommentRepository.findByGameId(any())).thenReturn(List.of(comment));

        var comments = nbaMatchCommentService.getCommentsByGame(1L);

        assertNotNull(comments);
        assertFalse(comments.isEmpty());
        assertEquals(List.of(comment), comments);
    }

    @Test
    public void saveComment_successful() {
        var comment = new NbaMatchComment();
        comment.setComment("Test");
        when(nbaCommentRepository.save(comment)).thenReturn(comment);

        var result = nbaMatchCommentService.saveComment(comment);

        assertNotNull(result);
        assertEquals(comment.getComment(), result.getComment());
    }

    @Test
    public void updateComment_idMismatch() {
        var comment = new NbaMatchComment();
        comment.setComment("newTest");
        comment.setId(1L);

        assertThrows(BadRequestException.class, () -> nbaMatchCommentService.updateComment(2L, comment));
    }

    @Test
    public void updateComment_successful() {
        var oldComment = new NbaMatchComment();
        oldComment.setGameId(1L);
        var comment = new NbaMatchComment();
        comment.setGameId(1L);
        comment.setComment("newTest");
        when(nbaCommentRepository.findById(any())).thenReturn(Optional.of(oldComment));
        when(nbaCommentRepository.save(comment)).thenReturn(comment);

        var result = nbaMatchCommentService.updateComment(1L, comment);

        assertNotNull(result);
        assertEquals(comment.getComment(), result.getComment());
    }

    @Test
    public void deleteComment_successful() {
        var comment = new NbaMatchComment();
        comment.setId(1L);
        when(nbaCommentRepository.findById(any())).thenReturn(Optional.of(comment));

        var result = nbaMatchCommentService.deleteComment(1L);

        verify(nbaCommentRepository, times(1)).delete(comment);

        assertNotNull(result);
        assertEquals(comment, result);
    }
}