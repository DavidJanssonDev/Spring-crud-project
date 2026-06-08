package com.example.Spring_crud_project.repository;

import com.example.Spring_crud_project.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
