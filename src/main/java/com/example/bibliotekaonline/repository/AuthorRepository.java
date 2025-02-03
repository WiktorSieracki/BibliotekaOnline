package com.example.bibliotekaonline.repository;

import com.example.bibliotekaonline.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findFirstByName(String name);
}
