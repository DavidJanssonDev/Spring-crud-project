package com.example.Spring_crud_project.dto.mapper;

import com.example.Spring_crud_project.dto.classes.AuthorDto;
import com.example.Spring_crud_project.entity.Author;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {

    private final ModelMapper modelMapper;

    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AuthorDto toDto(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    public Author fromDto(AuthorDto dto) {
        return modelMapper.map(dto, Author.class);
    }
}
