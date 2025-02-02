package com.example.bibliotekaonline.dto;

import lombok.Data;

import java.time.Year;
import java.util.List;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private List<String> categories;
    private Year publishedYear;
    private Integer numPages;
    private String description;
    private String thumbnail;
    private Integer ratingsCount;
    private Double averageRating;
    private List<AuthorDTO> authors;

}