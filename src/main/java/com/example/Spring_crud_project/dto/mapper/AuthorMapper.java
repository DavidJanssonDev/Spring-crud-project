package com.example.Spring_crud_project.dto.mapper;


import com.example.Spring_crud_project.dto.classes.AuthorDto;
import com.example.Spring_crud_project.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AuthorMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    AuthorDto toDto(Author author);

    Author fromDto(AuthorDto authorDto);
}
