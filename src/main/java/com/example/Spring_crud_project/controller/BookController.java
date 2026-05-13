package com.example.Spring_crud_project.controller;

import jakarta.validation.Valid;
import com.example.Spring_crud_project.dto.classes.BookDto;
import com.example.Spring_crud_project.dto.mapper.BookMapper;
import com.example.Spring_crud_project.entity.Book;
import com.example.Spring_crud_project.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    // =========================
    // PUBLIC READ ENDPOINTS
    // =========================

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(
                bookService.getAllBooks()
                        .stream()
                        .map(bookMapper::toDto)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(
                bookMapper.toDto(bookService.getBookById(id))
        );
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookDto>> getBooksByTitle(@PathVariable String title) {
        return ResponseEntity.ok(
                bookService.getBooksByTitle(title)
                        .stream()
                        .map(bookMapper::toDto)
                        .toList()
        );
    }

    // =========================
    // WRITE OPERATIONS (SECURED)
    // =========================

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto dto) {

        Book saved = bookService.createBook(
                bookMapper.fromDto(dto)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookDto> updateBook(
            @PathVariable Long id,
            @RequestBody BookDto dto
    ) {
        return ResponseEntity.ok(
                bookMapper.toDto(
                        bookService.updateBookById(id, bookMapper.fromDto(dto))
                )
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {

        bookService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }
}