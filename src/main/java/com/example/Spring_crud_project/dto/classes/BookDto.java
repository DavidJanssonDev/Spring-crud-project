package com.example.Spring_crud_project.dto.classes;

import lombok.Data;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String isbn;
    private AuthorDto author;
}
