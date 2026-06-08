package com.example.Spring_crud_project.entity;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

@Entity
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String path;
    private String contentType;
    private Long size;

    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public FileEntity() {}

    public void setFilename(String fileName) {
        this.filename = fileName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setContentType(@Nullable String contentType) {
        this.contentType = contentType;
    }

    public void setSize(long size) {
        this.size = size;   
    }

    public void setUploadedAt(LocalDateTime now) {
        this.uploadedAt = now;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
