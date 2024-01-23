package com.cms.app.ExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling {

    // Exception handler for ResourceNotFoundException
    @ExceptionHandler(CustomException.ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(CustomException.ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // Exception handler for CustomValidationException
    @ExceptionHandler(CustomException.CustomValidationException.class)
    public ResponseEntity<String> handleCustomValidationException(CustomException.CustomValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Default exception handler for handling other unhandled exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
}
