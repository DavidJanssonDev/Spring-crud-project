package com.example.Spring_crud_project.exception.customExceptions;

public class NoBookWithIDFoundException extends RuntimeException {
    public NoBookWithIDFoundException(String message) {
        super(message);
    }
}
