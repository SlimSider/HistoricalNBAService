package com.kszamosy.controller;

import com.kszamosy.model.resource.NbaCommentedMatchResource;
import com.kszamosy.model.resource.NbaMatchResource;
import com.kszamosy.service.NbaCommentedMatchService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NbaCommentedMatchController.class)
class NbaCommentedMatchControllerTest {

    @MockBean
    NbaCommentedMatchService nbaCommentedMatchService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getMatchesByDate_successful() throws Exception {
        var match = new NbaCommentedMatchResource();
        match.setId(4L);

        when(nbaCommentedMatchService.getMatchesByDate("2022-03-20")).thenReturn(List.of(match));

        mockMvc.perform(get("/matches?date=2022-03-20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(4)));
    }

    @Test
    void getMatchById_successful() throws Exception {
        var match = new NbaCommentedMatchResource();
        match.setId(1L);

        when(nbaCommentedMatchService.getMatchById(1L)).thenReturn(match);

        mockMvc.perform(get("/matches/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.id", is(1)));
    }
}