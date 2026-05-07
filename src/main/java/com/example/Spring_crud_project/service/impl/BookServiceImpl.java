package com.example.Spring_crud_project.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.Spring_crud_project.entity.Author;
import com.example.Spring_crud_project.entity.Book;
import com.example.Spring_crud_project.repository.AuthorRepository;
import com.example.Spring_crud_project.repository.BookRepository;
import com.example.Spring_crud_project.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book createBook(Book book) {

        log.info("🔍 Checking author: {} {}",
                book.getAuthor().getFirstName(),
                book.getAuthor().getLastName()
        );

        Author incomingAuthor = book.getAuthor();

        Author authorToUse = authorRepository
                .findByFirstNameAndLastName(
                        incomingAuthor.getFirstName(),
                        incomingAuthor.getLastName()
                )
                .orElseGet(() -> {
                    log.info("🆕 Author not found → creating new one");
                    return authorRepository.save(incomingAuthor);
                });

        log.info("👤 Using author ID: {}", authorToUse.getId());

        book.setAuthor(authorToUse);

        Book saved = bookRepository.save(book);

        log.info("📚 Book saved with ID: {}", saved.getId());

        return saved;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
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
}
