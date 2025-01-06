package com.example.bibliotekaonline.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Year;
import java.util.List;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Size(max = 255)
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Author> authors;

    @Size(max = 500)
    private String categories;

    @Size(max = 500)
    private String thumbnail;

    @Lob
    private String description;

    private Year publishedYear;

    @Min(0)
    private Integer ratingsCount;

    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double averageRating;

    @Min(1)
    private Integer numPages;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments;
}
