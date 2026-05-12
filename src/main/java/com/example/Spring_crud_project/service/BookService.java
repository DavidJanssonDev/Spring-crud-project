package com.example.Spring_crud_project.service;

import com.example.Spring_crud_project.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookByTitle(String title);
    Book getBookById(Long Id);

    Book createBook(Book book);
    Book updateBookById(Long id, Book book);

    void deleteBook(Long id);

    List<Book> getBooksByTitle(String title);
}
