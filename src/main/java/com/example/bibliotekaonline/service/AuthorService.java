package com.example.bibliotekaonline.service;

import com.example.bibliotekaonline.dto.AuthorDTO;
import com.example.bibliotekaonline.model.Author;
import com.example.bibliotekaonline.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;


    public Author saveAuthor(Author author) {
        Optional<Author> existingAuthor = authorRepository.findFirstByName(author.getName());
        return existingAuthor.orElseGet(() -> authorRepository.save(author));
    }

    public Optional<Author> findByName(String name) {
        return authorRepository.findFirstByName(name);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

}