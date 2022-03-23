package com.kszamosy.controller;

import com.kszamosy.JsonUtil;
import com.kszamosy.model.entity.NbaMatchComment;
import com.kszamosy.service.NbaMatchCommentService;
import com.kszamosy.service.NbaRapidApiService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NbaMatchCommentController.class)
class NbaMatchCommentControllerTest {

    @MockBean
    NbaMatchCommentService nbaMatchCommentService;
    @MockBean
    NbaRapidApiService nbaRapidApiService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void saveCommentApiTest_successful() throws Exception {
        var comment = getBody(1L, "TEST");
        when(nbaMatchCommentService.saveComment(any())).thenReturn(comment);

        var body = JsonUtil.serialize(comment);
        mockMvc.perform(post("/comments").contentType(APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/comments/1"));
    }

    @Test
    void getAllCommentApiTest_successful() throws Exception {
        var comment = getBody(1L, "TEST");
        when(nbaMatchCommentService.getComments()).thenReturn(List.of(comment));

        mockMvc.perform(get("/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].comment", is("TEST")));
    }

    @Test
    void getByIdCommentApiTest_successful() throws Exception {
        var comment = getBody(1L, "TEST");
        when(nbaMatchCommentService.getCommentById(any())).thenReturn(comment);

        mockMvc.perform(get("/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.comment", is("TEST")));
    }

    @Test
    void updateCommentApiTest_successful() throws Exception {
        var comment = getBody(1L, "newTest");
        when(nbaMatchCommentService.updateComment(any(), any())).thenReturn(comment);

        var body = JsonUtil.serialize(comment);
        mockMvc.perform(put("/comments/1").contentType(APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.comment", is("newTest")));
    }

    @Test
    void deleteCommentApiTest_successful() throws Exception {
        var comment = getBody(1L, "ToBeDeleted");
        when(nbaMatchCommentService.deleteComment(any())).thenReturn(comment);

        mockMvc.perform(delete("/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.comment", is("ToBeDeleted")));
    }

    private NbaMatchComment getBody(Long gameId, String comment) {
        var body = new NbaMatchComment();
        body.setId(1L);
        body.setComment(comment);
        body.setGameId(gameId);
        return body;
    }
}