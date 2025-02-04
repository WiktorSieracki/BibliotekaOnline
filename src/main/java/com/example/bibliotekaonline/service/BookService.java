package com.example.bibliotekaonline.service;

import com.example.bibliotekaonline.dto.BookDTO;
import com.example.bibliotekaonline.mapper.BookMapper;
import com.example.bibliotekaonline.model.Author;
import com.example.bibliotekaonline.model.Book;
import com.example.bibliotekaonline.model.Comment;
import com.example.bibliotekaonline.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;


    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

   @Transactional
    public Book saveBook(Book book) {
        List<Author> savedAuthors = book.getAuthors().stream()
            .map(author -> authorService.saveAuthor(author))
            .collect(Collectors.toList());
        book.setAuthors(savedAuthors);

        return bookRepository.save(book);
    }

    public Page<Book> searchBooks(String searchBy, String query, Pageable pageable) {
        return switch (searchBy) {
            case "title" -> searchBooksByTitle(query, pageable);
            case "author" -> searchBooksByAuthor(query, pageable);
            case "category" -> searchBooksByCategory(query, pageable);
            case null, default -> getAllBooks(pageable);
        };
    }

    public Page<Book> searchBooksByTitle(String title, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

    public Page<Book> searchBooksByAuthor(String authorName, Pageable pageable) {
        return bookRepository.findByAuthors_NameContainingIgnoreCase(authorName, pageable);
    }

    public Page<Book> searchBooksByCategory(String category, Pageable pageable) {
        return bookRepository.findByCategoriesContainingIgnoreCase(category, pageable);
    }

    public List<Book> getMostPopularBooks() {
        return bookRepository.findTop3ByOrderByAverageRatingDesc().stream().limit(4).toList();
    }

    public Book postBookReview(Book book, Integer rating) {
        if (rating>0 && rating<6) {
        Integer currentRatingCount = book.getRatingsCount();
        Integer newRatingCount = currentRatingCount + rating;
        Double currentAverageRating = book.getAverageRating();
        Double newAverageRating = ((currentAverageRating * currentRatingCount) + rating)/newRatingCount;
        book.setAverageRating(newAverageRating);
        book.setRatingsCount(currentRatingCount);
        }
        return bookRepository.save(book);
    }

}
