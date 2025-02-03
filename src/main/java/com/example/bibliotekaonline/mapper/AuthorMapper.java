package com.example.bibliotekaonline.mapper;

import com.example.bibliotekaonline.dto.AuthorDTO;
import com.example.bibliotekaonline.model.Author;

public class AuthorMapper {
    public static AuthorDTO toDTO(Author author) {
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setId(author.getId());
        authorDTO.setName(author.getName());
        return authorDTO;
    }

    public static Author toEntity(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setId(authorDTO.getId());
        author.setName(authorDTO.getName());
        return author;
    }
}