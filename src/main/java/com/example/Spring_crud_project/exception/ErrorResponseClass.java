package com.example.Spring_crud_project.exception;


import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponseClass(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> validationErrors
) {

}
