package com.example.Spring_crud_project.repository;

import com.example.Spring_crud_project.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
