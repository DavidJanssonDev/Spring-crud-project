package com.example.Spring_crud_project.dto.classes;

import java.util.List;

public record FileUploadResponse(
        String message,
        List<String> files
) {}