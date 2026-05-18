package com.example.Spring_crud_project.exception.customExceptions;

public class IsbnBookAlreadyExistException extends RuntimeException {
    public String Isbn;
    public IsbnBookAlreadyExistException(String message, String isbn) {
        super(message);
        Isbn = isbn;
    }
}
