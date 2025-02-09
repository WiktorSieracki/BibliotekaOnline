package com.example.bibliotekaonline.dto;

import com.example.bibliotekaonline.model.Book;
import com.example.bibliotekaonline.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDTO {
    private long id;
    private User user;
    private Book book;
    private String text;

}
