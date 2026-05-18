package com.example.Spring_crud_project.service.impl;

import com.example.Spring_crud_project.exception.customExceptions.IsbnBookAlreadyExistException;
import com.example.Spring_crud_project.exception.customExceptions.NoBookWithIDFoundException;

import com.example.Spring_crud_project.entity.Author;
import com.example.Spring_crud_project.entity.Book;
import com.example.Spring_crud_project.repository.AuthorRepository;
import com.example.Spring_crud_project.repository.BookRepository;
import com.example.Spring_crud_project.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book createBook(Book book) {
        Author incomingAuthor = book.getAuthor();
        String isbn = book.getIsbn();

        boolean bookIsbnExist = bookRepository.findByisbn(isbn) != null;

        if (bookIsbnExist)
            throw new IsbnBookAlreadyExistException("Book with the " + isbn + " already exists", isbn);

        Author authorToUse = authorRepository
                .findByFirstNameAndLastName(
                        incomingAuthor.getFirstName(),
                        incomingAuthor.getLastName()
                )
                .orElseGet(() -> authorRepository.save(incomingAuthor));

        book.setAuthor(authorToUse);
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NoBookWithIDFoundException("Book not found with the id of " + id));
    }

    @Override
    public Book getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public Book updateBookById(Long id, Book book) {
        Book existing = getBookById(id);
        existing.setTitle(book.getTitle());
        existing.setIsbn(book.getIsbn());
        existing.setAuthor(book.getAuthor());
        return bookRepository.save(existing);
    }

    @Override
    public void deleteBook(Long id) {
        Book existing = getBookById(id);
        bookRepository.delete(existing);
    }

    @Override
    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findAllByTitleContainingIgnoreCase(title);
    }
}
