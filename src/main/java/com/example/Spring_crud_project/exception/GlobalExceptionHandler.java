package com.example.Spring_crud_project.exception;

import com.example.Spring_crud_project.exception.customExceptions.IsbnBookAlreadyExistException;
import com.example.Spring_crud_project.exception.customExceptions.NoBookWithIDFoundException;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoBookWithIDFoundException.class)
    public ResponseEntity<ErrorResponseClass> noBookWithIDFoundException(@NonNull NoBookWithIDFoundException error) {
        ErrorResponseClass response = new ErrorResponseClass(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                error.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseClass> methodArgumentNotValidException(@NonNull MethodArgumentNotValidException error) {
        Map<String, String> validationErrors = new HashMap<>();

        error.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> {
                    validationErrors.put(
                            fieldError.getField(),
                            fieldError.getDefaultMessage()
                    );
                });

        ErrorResponseClass response = new ErrorResponseClass(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_GATEWAY.getReasonPhrase(),
                "Validation failed",
                validationErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IsbnBookAlreadyExistException.class)
    public ResponseEntity<ErrorResponseClass> isbnBookAlreadyExistException(@NonNull IsbnBookAlreadyExistException error) {
        ErrorResponseClass response = new ErrorResponseClass(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                error.getMessage(),
                null
        );
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
}
