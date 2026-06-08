package com.example.Spring_crud_project.service.fileUpload;

import com.example.Spring_crud_project.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    List<FileEntity> uploadFiles(MultipartFile[] files);
}