package com.example.bibliotekaonline.repository;

import com.example.bibliotekaonline.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findFirstByName(String name);

    Optional<Author> findAuthorById(Long id);
}
