package com.example.bibliotekaonline.mapper;

import com.example.bibliotekaonline.dto.request.AuthorRequestDTO;
import com.example.bibliotekaonline.dto.response.AuthorResponseDTO;
import com.example.bibliotekaonline.model.Author;

import java.util.stream.Collectors;

public class AuthorMapper {
    public static AuthorResponseDTO toResponseDTO(Author author) {
        return new AuthorResponseDTO(
                author.getId(),
                author.getName()
        );
    }

    public static Author toEntity(AuthorRequestDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        return author;
    }
}