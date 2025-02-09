package com.example.bibliotekaonline.mapper;

import com.example.bibliotekaonline.dto.request.CommentRequestDTO;
import com.example.bibliotekaonline.dto.response.CommentResponseDTO;
import com.example.bibliotekaonline.model.Comment;

public class CommentMapper {
    public static CommentResponseDTO toResponseDTO(Comment comment) {
        return new CommentResponseDTO(
                comment.getId(),
                comment.getUser().getId(),
                comment.getBook().getId(),
                comment.getCreatedAt(),
                comment.getText()
        );
    }

    public static Comment toEntity(CommentRequestDTO dto) {
        Comment comment = new Comment();
        comment.setText(dto.getText());
        return comment;
    }
}