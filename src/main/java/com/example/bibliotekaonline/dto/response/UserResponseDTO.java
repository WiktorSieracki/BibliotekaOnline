package com.example.bibliotekaonline.dto.response;

import com.example.bibliotekaonline.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UserResponseDTO {
    private long id;
    private String name;
    private String email;
    private String password;
    private List<BookResponseDTO> reservedBooks;
    private List<BookResponseDTO> borrowedBooks;
    private List<BookResponseDTO> borrowHistory;
    private List<String> roles;
}
