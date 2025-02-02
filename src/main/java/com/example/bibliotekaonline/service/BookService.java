package com.example.bibliotekaonline.service;

import com.example.bibliotekaonline.dto.BookDTO;
import com.example.bibliotekaonline.model.Author;
import com.example.bibliotekaonline.model.Book;
import com.example.bibliotekaonline.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookRepository bookRepository;


    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        book.setAuthors(book.getAuthors().stream().map(author -> {
            Optional<Author> existingAuthor = authorService.findByName(author.getName());
            return existingAuthor.orElse(author);
        }).collect(Collectors.toList()));
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public BookDTO convertToDTO(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setCategories(book.getCategories());
        bookDTO.setNumPages(book.getNumPages());
        bookDTO.setPublishedYear(book.getPublishedYear());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setThumbnail(book.getThumbnail());
        bookDTO.setRatingsCount(book.getRatingsCount());
        bookDTO.setAverageRating(book.getAverageRating());
        bookDTO.setAuthors(book.getAuthors().stream().map(authorService::convertToDTO).collect(Collectors.toList()));
        return bookDTO;
    }

    public Book convertToEntity(BookDTO bookDTO) {
        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setTitle(bookDTO.getTitle());
        book.setCategories(bookDTO.getCategories());
        book.setNumPages(bookDTO.getNumPages());
        book.setPublishedYear(bookDTO.getPublishedYear());
        book.setDescription(bookDTO.getDescription());
        book.setThumbnail(bookDTO.getThumbnail());
        book.setRatingsCount(bookDTO.getRatingsCount());
        book.setAverageRating(bookDTO.getAverageRating());
        book.setAuthors(bookDTO.getAuthors().stream().map(authorService::convertToEntity).collect(Collectors.toList()));
        return book;
    }

    public Page<Book> searchBooks(String title, String author, String category, Pageable pageable) {
        if (title != null && !title.isEmpty()) {
            return searchBooksByTitle(title, pageable);
        } else if (author != null && !author.isEmpty()) {
            return searchBooksByAuthor(author, pageable);
        } else if (category != null && !category.isEmpty()) {
            return searchBooksByCategory(category, pageable);
        } else {
            return getAllBooks(pageable);
        }
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

}
