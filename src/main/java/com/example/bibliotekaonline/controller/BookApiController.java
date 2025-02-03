package com.example.bibliotekaonline.controller;

import com.example.bibliotekaonline.dto.BookDTO;
import com.example.bibliotekaonline.dto.CommentDTO;
import com.example.bibliotekaonline.mapper.BookMapper;
import com.example.bibliotekaonline.mapper.CommentMapper;
import com.example.bibliotekaonline.model.Book;
import com.example.bibliotekaonline.model.Comment;
import com.example.bibliotekaonline.service.AuthorService;
import com.example.bibliotekaonline.service.BookService;
import com.example.bibliotekaonline.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<Page<BookDTO>> getBooks(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "24") int size) {
        Page<Book> bookPage = bookService.getAllBooks(PageRequest.of(page, size));
        Page<BookDTO> bookDTOPage = bookPage.map(BookMapper::toDTO);
        return new ResponseEntity<>(bookDTOPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        BookDTO bookDTO = BookMapper.toDTO(book.get());
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        Book book = BookMapper.toEntity(bookDTO);
        Book savedBook = bookService.saveBook(book);
        BookDTO savedBookDTO = BookMapper.toDTO(savedBook);
        return new ResponseEntity<>(savedBookDTO, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        Comment newComment = commentService.saveComment(id,commentDTO);
        CommentDTO newCommentDTO = CommentMapper.toDTO(newComment);
        return new ResponseEntity<>(newCommentDTO,HttpStatus.CREATED);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Long id) {
        List<Comment> commentList = commentService.getComments(id);
        List<CommentDTO> commentDTOList = commentList.stream().map(CommentMapper::toDTO).toList();
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }
}