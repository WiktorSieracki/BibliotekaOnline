package com.example.bibliotekaonline.mapper;

import com.example.bibliotekaonline.dto.BookDTO;
import com.example.bibliotekaonline.model.Book;
import com.example.bibliotekaonline.model.Comment;

import java.time.Year;
import java.util.Collections;
import java.util.stream.Collectors;

public class BookMapper {
    public static BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getCategories(),
                book.getPublishedYear(),
                book.getNumPages(),
                book.getDescription(),
                book.getThumbnail(),
                book.getRatingsCount(),
                book.getAverageRating(),
                book.getAuthors().stream().map(AuthorMapper::toDTO).collect(Collectors.toList()),
                book.getComments().stream().map(CommentMapper::toDTO).collect(Collectors.toList())
        );
    }

    public static Book toEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setCategories(dto.getCategories());
        book.setPublishedYear(dto.getPublishedYear() != null ? dto.getPublishedYear() : Year.now());
        book.setNumPages(dto.getNumPages());
        book.setDescription(dto.getDescription());
        book.setThumbnail(dto.getThumbnail());
        book.setRatingsCount(dto.getRatingsCount() != null ? dto.getRatingsCount() : 0);
        book.setAverageRating(dto.getAverageRating() != null ? dto.getAverageRating() : 0.0);
        book.setAuthors(dto.getAuthors().stream().map(AuthorMapper::toEntity).collect(Collectors.toList()));
        book.setComments(dto.getComments() != null ? dto.getComments().stream().map(commentDTO -> {
            Comment comment = CommentMapper.toEntity(commentDTO);
            comment.setBook(book);
            return comment;
        }).collect(Collectors.toList()) : Collections.emptyList());
        return book;
    }
}