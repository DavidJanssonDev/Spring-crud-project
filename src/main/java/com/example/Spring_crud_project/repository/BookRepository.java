package com.example.Spring_crud_project.repository;


import com.example.Spring_crud_project.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
