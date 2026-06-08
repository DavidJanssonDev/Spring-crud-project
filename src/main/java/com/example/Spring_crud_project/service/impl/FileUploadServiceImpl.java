package com.example.Spring_crud_project.service.impl;

import com.example.Spring_crud_project.configs.UploadProperties;
import com.example.Spring_crud_project.entity.FileEntity;
import com.example.Spring_crud_project.entity.User;
import com.example.Spring_crud_project.exception.customExceptions.FileLimitExceededException;
import com.example.Spring_crud_project.exception.customExceptions.FileStorageException;
import com.example.Spring_crud_project.exception.customExceptions.InvalidFileException;
import com.example.Spring_crud_project.repository.FileRepository;
import com.example.Spring_crud_project.repository.UserRepository;
import com.example.Spring_crud_project.service.fileUpload.FileUploadService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final UploadProperties props;

    public FileUploadServiceImpl(UserRepository userRepository, FileRepository fileRepository, UploadProperties props) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
        this.props = props;
    }

    @Override
    public List<FileEntity> uploadFiles(MultipartFile[] files) {

        User user = getCurrentUser();

        validateFileCount(files);

        File dir = new File(System.getProperty("user.dir"), props.dir());

        System.out.println("UPLOAD PATH = " + dir.getAbsolutePath());
        if (!dir.exists() && !dir.mkdirs())
            throw new FileStorageException("Could not create upload directory", null);

        List<FileEntity> savedFiles = new ArrayList<>();

        for (MultipartFile file : files) {

            validateFile(file);

            try {
                String fileName = UUID.randomUUID() + "_" +file.getOriginalFilename();

                File destination = new File(dir, fileName);
                file.transferTo(destination);

                FileEntity entity = new FileEntity();
                entity.setFilename(fileName);
                entity.setPath(destination.getPath());
                entity.setContentType(file.getContentType());
                entity.setSize(file.getSize());
                entity.setUploadedAt(LocalDateTime.now());
                entity.setUser(user);

                savedFiles.add(fileRepository.save(entity));
            } catch (Exception e) {
                throw new FileStorageException("Upload failed", e);
            }

        }

        return savedFiles;
    }


    private User getCurrentUser() {
        String username = Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private void validateFileCount(MultipartFile[] files) {
        if (files.length > props.maxFiles()) {
            throw new FileLimitExceededException("Max allowed files: " + props.maxFiles());
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidFileException("Empty file");
        }

        if (file.getContentType() == null ||
                !file.getContentType().startsWith("image/")) {
            throw new InvalidFileException("Only images allowed");
        }
    }
}
