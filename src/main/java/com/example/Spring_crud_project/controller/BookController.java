package com.example.Spring_crud_project.controller;

import jakarta.validation.Valid;
import com.example.Spring_crud_project.dto.classes.BookDto;
import com.example.Spring_crud_project.dto.mapper.BookMapper;
import com.example.Spring_crud_project.entity.Book;
import com.example.Spring_crud_project.service.BookService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookDto dto) {
        Book book = bookMapper.fromDto(dto);
        Book saved = bookService.createBook(book);
        BookDto bookDto = bookMapper.toDto(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(bookDto);
    }



    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> bookDtoList = bookService.getAllBooks()
                .stream()
                .map(bookMapper::toDto)
                .toList();

        return ResponseEntity.ok(bookDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        Book bookFromId = bookService.getBookById(id);
        BookDto bookDto = bookMapper.toDto(bookFromId);

        return ResponseEntity.ok(bookDto);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<BookDto>> getBooksByTitle(@PathVariable String title) {
        List<Book> booksFromTitle = bookService.getBooksByTitle(title);

        List<BookDto> booksTitleListConverted = booksFromTitle
                .stream()
                .map(bookMapper::toDto)
                .toList();

        return ResponseEntity.ok(booksTitleListConverted);
    }

}
