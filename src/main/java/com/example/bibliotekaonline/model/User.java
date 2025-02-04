package com.example.bibliotekaonline.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Book> borrowedBooks = new ArrayList<Book>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Book> reservedBooks = new ArrayList<Book>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Book> borrowHistory = new ArrayList<Book>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> comments;
}
