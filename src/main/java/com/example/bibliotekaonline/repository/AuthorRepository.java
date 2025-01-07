package com.example.bibliotekaonline.repository;

import com.example.bibliotekaonline.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
