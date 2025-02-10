package com.example.bibliotekaonline.controller;

import com.example.bibliotekaonline.dto.request.AuthorRequestDTO;
import com.example.bibliotekaonline.dto.response.AuthorResponseDTO;
import com.example.bibliotekaonline.mapper.AuthorMapper;
import com.example.bibliotekaonline.model.Author;
import com.example.bibliotekaonline.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<AuthorResponseDTO> createAuthor(@RequestBody AuthorRequestDTO authorDTO) {
        Author author = AuthorMapper.toEntity(authorDTO);
        Author savedAuthor = authorService.saveAuthor(author);
        AuthorResponseDTO createdAuthorDTO = AuthorMapper.toResponseDTO(savedAuthor);
        return new ResponseEntity<>(createdAuthorDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{authorId}")
    public ResponseEntity<AuthorResponseDTO> updateAuthor(@PathVariable long authorId, @RequestBody AuthorRequestDTO authorDTO) {
        Author author = AuthorMapper.toEntity(authorDTO);
        author.setId(authorId);
        Author updatedAuthor = authorService.saveAuthor(author);
        AuthorResponseDTO updatedAuthorDTO = AuthorMapper.toResponseDTO(updatedAuthor);
        return new ResponseEntity<>(updatedAuthorDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable long authorId) {
        authorService.deleteAuthor(authorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
}