package com.example.bibliotekaonline.service;

import com.example.bibliotekaonline.dto.request.CommentRequestDTO;
import com.example.bibliotekaonline.dto.response.CommentResponseDTO;
import com.example.bibliotekaonline.mapper.CommentMapper;
import com.example.bibliotekaonline.model.Book;
import com.example.bibliotekaonline.model.Comment;
import com.example.bibliotekaonline.model.User;
import com.example.bibliotekaonline.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private BookService bookService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

   @Transactional
    public Comment saveComment(long bookId,long userId, CommentRequestDTO commentDTO) {
        Book book = bookService.getBookById(bookId);
        User user = customUserDetailsService.getUserById(userId);
        Comment comment = CommentMapper.toEntity(commentDTO);
        comment.setUser(user);
        comment.setBook(book);
        commentRepository.save(comment);
        return comment;
    }

    @Transactional
    public void deleteComment(long commentId) {
       commentRepository.deleteById(commentId);
    }

    public List<Comment> getComments(long bookId) {
        Book book = bookService.getBookById(bookId);
        return book.getComments();
    }

}