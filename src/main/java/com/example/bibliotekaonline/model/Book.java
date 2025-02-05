package com.example.bibliotekaonline.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Year;
import java.util.List;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // @Column(unique = true)
    private String title;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @ElementCollection
    @CollectionTable(name = "book_categories", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "categories")
    private List<String> categories;

    private String thumbnail;

    @Column(columnDefinition="TEXT")
    private String description;

    private Year publishedYear;

    private Integer ratingsCount = 0;

    private Double averageRating = 0.0;

    private Integer numPages;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments;
}
