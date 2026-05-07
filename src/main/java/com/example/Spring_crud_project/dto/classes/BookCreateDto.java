package com.example.Spring_crud_project.dto.classes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BookCreateDto {
    private String title;
    private String isbn;
    private AuthorDto author;
}
