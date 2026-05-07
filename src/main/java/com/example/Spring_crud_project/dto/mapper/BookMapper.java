package com.example.Spring_crud_project.dto.mapper;

import com.example.Spring_crud_project.dto.classes.BookDto;
import com.example.Spring_crud_project.entity.Book;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    private final ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BookDto toDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    public Book fromDto(BookDto dto) {
        return modelMapper.map(dto, Book.class);
    }

}
