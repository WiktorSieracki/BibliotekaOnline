package com.example.bibliotekaonline.mapper;

import com.example.bibliotekaonline.dto.request.BookRequestDTO;
import com.example.bibliotekaonline.dto.response.BookResponseDTO;
import com.example.bibliotekaonline.model.Book;
import com.example.bibliotekaonline.model.Comment;

import java.util.stream.Collectors;

public class BookMapper {
    public static BookResponseDTO toResponseDTO(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getCategories(),
                book.getPublishedYear(),
                book.getNumPages(),
                book.getDescription(),
                book.getThumbnail(),
                book.getRatingsCount(),
                book.getAverageRating(),
                book.getAuthors().stream().map(AuthorMapper::toResponseDTO).collect(Collectors.toList()),
                book.getComments().stream().map(CommentMapper::toResponseDTO).collect(Collectors.toList())
        );
    }

    public static Book toEntity(BookRequestDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setCategories(dto.getCategories());
        book.setPublishedYear(dto.getPublishedYear());
        book.setNumPages(dto.getNumPages());
        book.setDescription(dto.getDescription());
        book.setThumbnail(dto.getThumbnail() != null ? dto.getThumbnail() : "https://picsum.photos/200/300");
        book.setRatingsCount(dto.getRatingsCount() != null ? dto.getRatingsCount() : 0);
        book.setAverageRating(dto.getAverageRating() != null ? dto.getAverageRating() : 0.0);
        book.setAuthors(dto.getAuthors().stream().map(AuthorMapper::toEntity).collect(Collectors.toList()));
        return book;
    }
}