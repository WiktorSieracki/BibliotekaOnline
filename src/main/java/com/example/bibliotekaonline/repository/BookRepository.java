package com.example.bibliotekaonline.repository;

import com.example.bibliotekaonline.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}