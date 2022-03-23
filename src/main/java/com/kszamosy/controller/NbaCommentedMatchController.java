package com.kszamosy.controller;

import com.kszamosy.model.resource.NbaCommentedMatchResource;
import com.kszamosy.service.NbaCommentedMatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
@Tag(name = "NBA Match API with Comments")
public class NbaCommentedMatchController {

    private final NbaCommentedMatchService nbaCommentedMatchService;

    public NbaCommentedMatchController(NbaCommentedMatchService nbaCommentedMatchService) {
        this.nbaCommentedMatchService = nbaCommentedMatchService;
    }

    @Operation(summary = "Get NBA matches by date")
    @ApiResponse(responseCode = "200", description = "Returns all NBA matches by date with comments and player scores")
    @GetMapping
    public ResponseEntity<List<NbaCommentedMatchResource>> getHistoricalFixtures(@RequestParam("date") String date) {
        var data = nbaCommentedMatchService.getMatchesByDate(date);
        return ResponseEntity.ok(data);
    }

    @Operation(summary = "Get NBA match by game Id")
    @ApiResponse(responseCode = "200", description = "Returns an NBA match by game Id with comments and player scores")
    @GetMapping("/{gameId}")
    public ResponseEntity<NbaCommentedMatchResource> getHistoricalFixtureById(@PathVariable("gameId") Long gameId) {
        var data = nbaCommentedMatchService.getMatchById(gameId);
        return ResponseEntity.ok(data);
    }
}
