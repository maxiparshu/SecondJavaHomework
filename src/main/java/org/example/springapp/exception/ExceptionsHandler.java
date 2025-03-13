package org.example.springapp.exception;

import jakarta.validation.ConstraintDeclarationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * Глобальный обработчик исключений для обработки различных типов ошибок в Spring Boot приложении.
 * Возвращает кастомные ответы с деталями ошибки для определённых исключений.
 */
@RestControllerAdvice
public class ExceptionsHandler {

    /**
     * Обрабатывает ResourceNotFoundException и возвращает статус 404 NOT_FOUND с деталями ошибки.
     *
     * @param exception пойманное исключение ResourceNotFoundException
     * @return ResponseEntity с объектом ExceptionDetails и статусом NOT_FOUND
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDetails> resourceNotFoundException(
            final ResourceNotFoundException exception) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                new Date(),
                exception.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Обрабатывает BadRequestException и возвращает статус 400 BAD_REQUEST с деталями ошибки.
     *
     * @param exception пойманное исключение BadRequestException
     * @return ResponseEntity с объектом ExceptionDetails и статусом BAD_REQUEST
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDetails> badRequestException(
            final BadRequestException exception) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                new Date(),
                exception.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает MethodArgumentNotValidException (ошибки валидации аргументов) и возвращает статус 400 BAD_REQUEST.
     *
     * @param ex пойманное исключение MethodArgumentNotValidException
     * @return ResponseEntity с объектом ExceptionDetails и статусом BAD_REQUEST
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetails> methodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                new Date(),
                "Ошибка сохранения данных: " + ex.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает ConstraintDeclarationException (ошибки валидации ограничений) и возвращает статус 400 BAD_REQUEST.
     *
     * @param ex пойманное исключение ConstraintDeclarationException
     * @return ResponseEntity с объектом ExceptionDetails и статусом BAD_REQUEST
     */
    @ExceptionHandler(ConstraintDeclarationException.class)
    public ResponseEntity<ExceptionDetails> constraintDeclarationException(final ConstraintDeclarationException ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                new Date(),
                "Ошибка сохранения данных: " + ex.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает IllegalArgumentException (например, некорректные значения enum) и возвращает статус 400 BAD_REQUEST.
     *
     * @param ex пойманное исключение IllegalArgumentException
     * @return ResponseEntity с объектом ExceptionDetails и статусом BAD_REQUEST
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDetails> illegalArgumentException(final IllegalArgumentException ex) {
        ExceptionDetails exceptionDetails = new ExceptionDetails(
                new Date(),
                "Enum: " + ex.getMessage()
        );
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }
}
