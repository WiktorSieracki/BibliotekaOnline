package com.example.bibliotekaonline.controller;

import com.example.bibliotekaonline.dto.request.BookRequestDTO;
import com.example.bibliotekaonline.dto.request.CommentRequestDTO;
import com.example.bibliotekaonline.dto.response.BookResponseDTO;
import com.example.bibliotekaonline.dto.response.CommentResponseDTO;
import com.example.bibliotekaonline.mapper.BookMapper;
import com.example.bibliotekaonline.mapper.CommentMapper;
import com.example.bibliotekaonline.model.Book;
import com.example.bibliotekaonline.model.Comment;
import com.example.bibliotekaonline.service.BookService;
import com.example.bibliotekaonline.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/books")
public class BookApiController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<Page<BookResponseDTO>> getBooks(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "24") int size,
                                                          @RequestParam(defaultValue = "title") String sortBy,
                                                          @RequestParam(defaultValue = "ASC") String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Book> bookPage = bookService.getAllBooks(pageable);
        Page<BookResponseDTO> bookDTOPage = bookPage.map(BookMapper::toResponseDTO);
        return new ResponseEntity<>(bookDTOPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        BookResponseDTO bookDTO = BookMapper.toResponseDTO(book);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookRequestDTO bookRequestDTO) {
        Book book = BookMapper.toEntity(bookRequestDTO);
        Book savedBook = bookService.saveBook(book);
        BookResponseDTO savedBookDTO = BookMapper.toResponseDTO(savedBook);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id, @RequestBody BookRequestDTO updatedBookRequestDTO) {
        Book existingBook = bookService.getBookById(id);
        Book book = BookMapper.toEntity(updatedBookRequestDTO);
        book.setId(id);
        Book updatedBook = bookService.saveBook(book);
        BookResponseDTO updatedBookDTO = BookMapper.toResponseDTO(updatedBook);
        return new ResponseEntity<>(updatedBookDTO, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Book existingBook = bookService.getBookById(id);
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{bookId}/comments/{userId}")
    public ResponseEntity<CommentResponseDTO> addComment(@PathVariable Long bookId, @PathVariable Long userId, @RequestBody CommentRequestDTO commentDTO) {
        Comment newComment = commentService.saveComment(bookId,userId,commentDTO);
        CommentResponseDTO newCommentDTO = CommentMapper.toResponseDTO(newComment);
        return new ResponseEntity<>(newCommentDTO,HttpStatus.CREATED);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentResponseDTO>> getComments(@PathVariable Long id) {
        List<Comment> commentList = commentService.getComments(id);
        List<CommentResponseDTO> commentDTOList = commentList.stream().map(CommentMapper::toResponseDTO).toList();
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/comments/{commentId}/delete")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @PathVariable Long id) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/rating/{rating}")
    public ResponseEntity<Void> addReview(@PathVariable Long id,@PathVariable Integer rating) {
        Book book = bookService.getBookById(id);
        bookService.postBookReview(book,rating);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}