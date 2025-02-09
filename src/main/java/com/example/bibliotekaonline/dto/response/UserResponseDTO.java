package com.example.bibliotekaonline.dto;

import com.example.bibliotekaonline.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UserDTO {
    private long id;
    private String name;
    private String email;
    private String password;
    private List<Book> reservedBooks;
    private List<Book> borrowedBooks;
    private List<Book> borrowHistory;
    private List<String> roles;
}
