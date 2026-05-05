package com.example.Spring_crud_project.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String firstName; // = first_name
    private String lastName;  // = last_name

    @OneToMany(mappedBy = "author")
    private List<Book> books;
}
