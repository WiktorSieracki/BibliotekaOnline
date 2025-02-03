package com.example.bibliotekaonline.repository;

import com.example.bibliotekaonline.model.Author;
import com.example.bibliotekaonline.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAll(Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findByAuthors_NameContainingIgnoreCase(String authorName, Pageable pageable);

    Page<Book> findByCategoriesContainingIgnoreCase(String category, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.ratingsCount>10 ORDER BY b.averageRating DESC")
    List<Book> findTop3ByOrderByAverageRatingDesc();
}