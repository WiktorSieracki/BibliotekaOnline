package com.example.bibliotekaonline.repository;

import com.example.bibliotekaonline.model.Author;
import com.example.bibliotekaonline.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAll(Pageable pageable);

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Book> findByAuthors_NameContainingIgnoreCase(String authorName, Pageable pageable);

    Page<Book> findByCategoriesContainingIgnoreCase(String category, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.ratingsCount>0 ORDER BY b.averageRating DESC")
    List<Book> findTop3ByOrderByAverageRatingDesc();
}