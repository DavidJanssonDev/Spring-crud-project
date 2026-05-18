package com.example.Spring_crud_project.dto.classes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookRequest(

        @NotBlank(message = "Title is required")
        @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
        String title,

        @NotBlank(message = "ISBN is required")
        @Size(min = 10, max = 20, message = "ISBN must be between 10 and 20 characters")
        String isbn,

        @Valid
        @NotNull(message = "Author is required")
        AuthorRequest author
) {}