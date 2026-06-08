package com.example.Spring_crud_project.exception.customExceptions;

public class FileLimitExceededException extends RuntimeException {
    public FileLimitExceededException(String message) {
        super(message);
    }
}