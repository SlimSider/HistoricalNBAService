package com.kszamosy.exception;

import com.kszamosy.model.resource.ErrorResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorResource> entityNotFoundHandler(NotFoundException ex) {
        return ResponseEntity.status(404).body(new ErrorResource(ex.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResource> badRequestHandler(BadRequestException ex) {
        return ResponseEntity.status(400).body(new ErrorResource(ex.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(InternalErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorResource> badRequestHandler(InternalErrorException ex) {
        return ResponseEntity.status(500).body(new ErrorResource(ex.getMessage()));
    }

}
