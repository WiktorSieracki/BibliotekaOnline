package com.example.bibliotekaonline.service;

import com.example.bibliotekaonline.dto.CommentDTO;
import com.example.bibliotekaonline.mapper.CommentMapper;
import com.example.bibliotekaonline.model.Book;
import com.example.bibliotekaonline.model.Comment;
import com.example.bibliotekaonline.model.User;
import com.example.bibliotekaonline.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private BookService bookService;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public Comment saveComment(long bookId, CommentDTO commentDTO) {
        Book book = bookService.getBookById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Comment comment = CommentMapper.toEntity(commentDTO);
        comment.setBook(book);
        book.getComments().add(comment);
        commentRepository.save(comment);
        bookService.saveBook(book);
        return comment;
    }

    public List<Comment> getComments(long bookId) {
        Book book = bookService.getBookById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        return book.getComments();
    }

}