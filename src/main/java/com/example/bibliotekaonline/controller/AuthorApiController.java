package com.example.bibliotekaonline.controller;

import com.example.bibliotekaonline.dto.response.AuthorResponseDTO;
import com.example.bibliotekaonline.mapper.AuthorMapper;
import com.example.bibliotekaonline.model.Author;
import com.example.bibliotekaonline.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
public class AuthorApiController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        List<AuthorResponseDTO> authorDTOs = authors.stream().map(AuthorMapper::toResponseDTO).collect(Collectors.toList());
        return new ResponseEntity<>(authorDTOs, HttpStatus.OK);
    }
}