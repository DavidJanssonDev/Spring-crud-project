package com.example.Spring_crud_project.repository;


import com.example.Spring_crud_project.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitle(String title);

    List<Book> findAllByTitleContainingIgnoreCase(String title);
}
