package com.example.bibliotekaonline.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "\"user\"")
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(cascade = CascadeType.ALL)
    private List<Book> borrowedBooks = new ArrayList<Book>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Book> reservedBooks = new ArrayList<Book>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Book> borrowHistory = new ArrayList<Book>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
}
