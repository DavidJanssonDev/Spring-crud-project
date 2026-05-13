package com.example.Spring_crud_project.dto.mapper;


import com.example.Spring_crud_project.dto.classes.BookDto;
import com.example.Spring_crud_project.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", uses = AuthorMapper.class, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface BookMapper {

    BookDto toDto(Book book);

    Book fromDto(BookDto bookDto);
}
