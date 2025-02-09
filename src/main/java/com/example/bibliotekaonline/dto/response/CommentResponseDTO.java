package com.example.bibliotekaonline.dto.response;

import com.example.bibliotekaonline.model.Book;
import com.example.bibliotekaonline.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class CommentResponseDTO {
    private long id;
    private long userId;
    private long bookId;
    private LocalDateTime createdAt;
    private String text;

}
