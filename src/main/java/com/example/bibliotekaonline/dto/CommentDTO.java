package com.example.bibliotekaonline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDTO {
    private long id;
//    private UserDTO user;
//    private BookDTO book;
    private String text;

}
