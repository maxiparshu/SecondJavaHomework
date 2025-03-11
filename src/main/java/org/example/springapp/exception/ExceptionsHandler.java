package org.example.springapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDetails> resourceNotFoundException(
            final ResourceNotFoundException exception) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                new Date(),
                exception.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails,
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDetails> badRequestException(
            final BadRequestException exception) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                new Date(),
                exception.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails,
                HttpStatus.BAD_REQUEST);
    }
}
