package com.example.Spring_crud_project.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.Spring_crud_project.dto.classes.BookDto;
import com.example.Spring_crud_project.dto.mapper.BookMapper;
import com.example.Spring_crud_project.entity.Book;
import com.example.Spring_crud_project.service.BookService;
import com.example.Spring_crud_project.service.impl.BookServiceImpl;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookServiceImpl bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping
    public BookDto createBook(@RequestBody BookDto dto) {

        log.info("📥 Incoming POST /books request: {}", dto);

        Book book = bookMapper.fromDto(dto);

        log.info("🔄 Mapped DTO → Entity: {}", book);

        Book saved = bookService.createBook(book);

        log.info("💾 Saved Book in DB: {}", saved);

        return bookMapper.toDto(saved);
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks()
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookMapper.toDto(bookService.getBookById(id));
    }

    @GetMapping("/title/{title}")
    public List<BookDto> getBooksByTitle(@PathVariable String title) {
        return bookService.getBooksByTitle(title)
                .stream()
                .map(bookMapper::toDto)
                .toList();

    }

}
