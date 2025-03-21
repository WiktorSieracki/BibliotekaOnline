package com.example.bibliotekaonline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Year;
import java.util.List;

@AllArgsConstructor
@Data
public class BookResponseDTO {
    private Long id;
    private String title;
    private List<String> categories;
    private Year publishedYear;
    private Integer numPages;
    private String description;
    private String thumbnail;
    private Integer ratingsCount;
    private Double averageRating;
    private List<AuthorResponseDTO> authors;
    private List<CommentResponseDTO> comments;

}