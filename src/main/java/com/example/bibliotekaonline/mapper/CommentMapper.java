package com.example.bibliotekaonline.mapper;

import com.example.bibliotekaonline.dto.CommentDTO;
import com.example.bibliotekaonline.model.Comment;

public class CommentMapper {
    public static CommentDTO toDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
//                UserMapper.toDTO(comment.getUser()),
                BookMapper.toDTO(comment.getBook()),
                comment.getText()
        );
    }

    public static Comment toEntity(CommentDTO dto) {
        Comment comment = new Comment();
        comment.setId(dto.getId());
        comment.setText(dto.getText());
        return comment;
    }
}