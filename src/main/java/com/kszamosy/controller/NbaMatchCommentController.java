package com.kszamosy.controller;

import com.kszamosy.model.entity.NbaMatchComment;
import com.kszamosy.service.NbaMatchCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/comments")
@Tag(name = "NBA Comment API")
public class NbaMatchCommentController {

    private final NbaMatchCommentService nbaMatchCommentService;

    public NbaMatchCommentController(NbaMatchCommentService nbaMatchCommentService) {
        this.nbaMatchCommentService = nbaMatchCommentService;
    }


    @Operation(summary = "Returns all comments")
    @ApiResponse(responseCode = "200", description = "Returns all comments")
    @GetMapping
    public ResponseEntity<List<NbaMatchComment>> getComments() {
        var resources = nbaMatchCommentService.getComments();
        return ResponseEntity.ok(resources);
    }

    @Operation(summary = "Returns a comment by id")
    @ApiResponse(responseCode = "200", description = "Returns all comments")
    @GetMapping("/{id}")
    public ResponseEntity<NbaMatchComment> getCommentById(@PathVariable("id") Long id) {
        var resource = nbaMatchCommentService.getCommentById(id);
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Saves a comment")
    @ApiResponse(responseCode = "201", description = "Saves resource and returns location in the headers")
    @PostMapping
    public ResponseEntity<NbaMatchComment> saveComments(@RequestBody NbaMatchComment nbaMatchComment) {
        var saved = nbaMatchCommentService.saveComment(nbaMatchComment);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(saved.getId()).toUri()).build();
    }

    @Operation(summary = "Updates a comment")
    @ApiResponse(responseCode = "200", description = "Updates a comment a returns it")
    @PutMapping("/{id}")
    public ResponseEntity<NbaMatchComment> updateComment(@PathVariable("id") Long id,
                                                         @RequestBody NbaMatchComment nbaMatchComment) {
        var updated = nbaMatchCommentService.updateComment(id, nbaMatchComment);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Removes a comment")
    @ApiResponse(responseCode = "200", description = "Removes a comment and returns it")
    @DeleteMapping("/{id}")
    public ResponseEntity<NbaMatchComment> deleteComment(@PathVariable("id") Long id) {
        var deleted = nbaMatchCommentService.deleteComment(id);
        return ResponseEntity.ok(deleted);
    }
}
