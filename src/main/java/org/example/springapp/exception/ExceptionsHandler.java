package org.example.springapp.exception;

import jakarta.validation.ConstraintDeclarationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetails> methodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                new Date(),
                "Error of saving data: " + ex.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintDeclarationException.class)
    public ResponseEntity<ExceptionDetails> constraintDeclarationException(final ConstraintDeclarationException ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                new Date(),
                "Error of saving data: " + ex.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDetails> illegalArgumentException(final IllegalArgumentException ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                new Date(),
                "Enum:" + ex.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
}
