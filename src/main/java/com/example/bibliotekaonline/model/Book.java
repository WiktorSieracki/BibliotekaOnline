package com.example.bibliotekaonline.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 255)
    private String title;

    @NotNull
    @NotEmpty
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    @NotNull
    @NotEmpty
    @ElementCollection
    @CollectionTable(name = "book_categories", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "categories")
    private List<String> categories;

    private String thumbnail;

    @Column(columnDefinition="TEXT")
    private String description;

    @NotNull
    private Year publishedYear;

    @Min(0)
    private Integer ratingsCount = 0;

    @Min(0)
    private Double averageRating = 0.0;

    @NotNull
    @Min(1)
    private Integer numPages;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
