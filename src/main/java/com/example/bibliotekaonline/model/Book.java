package com.example.bibliotekaonline.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;


@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String title;

    String authors;

    float averageRating;

    String languageCode;

    int numPages;

    int ratingsCount;

    int textReviewsCount;

    Date publicationDate;

    String publisher;
}
