package com.example.Spring_crud_project.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName; // = first_name
    private String lastName;  // = last_name

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    public Author() {}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id;}

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }
}
