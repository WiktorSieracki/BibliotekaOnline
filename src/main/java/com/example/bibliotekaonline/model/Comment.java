package com.example.bibliotekaonline.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

     @ManyToOne
     @JoinColumn(name = "user_id")
     private User user;

     @ManyToOne
     @JoinColumn(name = "book_id")
     private Book book;

     @CreationTimestamp
     private LocalDateTime createdAt;

    @Column(columnDefinition="TEXT")
    private String text;
}
