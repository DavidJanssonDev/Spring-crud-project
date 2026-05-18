package com.example.Spring_crud_project.dto.classes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorRequest(

        Long id,
        @NotBlank(message = "Author first name is required")
        @Size(min = 2, max = 50)
        String firstName,

        @NotBlank(message = "Author last name is required")
        @Size(min = 2, max = 50)
        String lastName

) {}
