package com.example.Spring_crud_project.dto.classes;

import java.util.List;

public record FileUploadRequest(
        String fileName,
        List<String> imageBase64
) {
}
