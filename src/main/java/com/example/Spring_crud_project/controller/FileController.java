package com.example.Spring_crud_project.controller;

import com.example.Spring_crud_project.dto.classes.FileUploadResponse;
import com.example.Spring_crud_project.entity.FileEntity;
import com.example.Spring_crud_project.service.fileUpload.FileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileUploadService fileUploadService;

    public FileController(FileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<List<FileEntity>> upload(@RequestParam("files") MultipartFile[] files) {

        return ResponseEntity.ok(fileUploadService.uploadFiles(files));
    }
}
