package com.example.taskmanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<com.example.taskmanager.exceptions.ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        com.example.taskmanager.exceptions.ErrorResponse error = new com.example.taskmanager.exceptions.ErrorResponse("Not Found", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<com.example.taskmanager.exceptions.ErrorResponse> handleAllExceptions(Exception ex) {
        com.example.taskmanager.exceptions.ErrorResponse error = new ErrorResponse("Internal Server Error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
