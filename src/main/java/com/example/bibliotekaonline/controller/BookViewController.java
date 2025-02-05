package com.example.bibliotekaonline.controller;

import com.example.bibliotekaonline.dto.CommentDTO;
import com.example.bibliotekaonline.model.Book;
import com.example.bibliotekaonline.model.User;
import com.example.bibliotekaonline.service.BookService;
import com.example.bibliotekaonline.service.CommentService;
import com.example.bibliotekaonline.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookViewController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping
    public String getBooks(Model model,
                           @RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "24") int size,
                           @RequestParam(defaultValue = "title") String sortBy,
                           @RequestParam(defaultValue = "ASC") String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Book> bookPage = bookService.getAllBooks(pageable);
        List<Book> mostPopularBooks = bookService.getMostPopularBooks();
        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("mostPopularBooks", mostPopularBooks);
        return "books/list";
    }

    @GetMapping("/{id}")
    public String getBookDetails(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = customUserDetailsService.getUserByEmail(email);
        model.addAttribute("user", user);
        model.addAttribute("book", book);
        model.addAttribute("newComment", new CommentDTO());
        return "books/details";
    }

    @PostMapping("/{id}/comments")
    public String addComment(@PathVariable Long id, @ModelAttribute("newComment") CommentDTO commentDTO) {
        commentService.saveComment(id, commentDTO);
        return "redirect:/books/" + id;
    }

    @PostMapping("/{id}/rating")
    public String addRating(@PathVariable Long id, @RequestParam int rating) {
        Book book = bookService.getBookById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        bookService.postBookReview(book, rating);
        return "redirect:/books/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "books/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/books/" + id;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/create";
    }

    @PostMapping("/create")
    public String createBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam String searchBy,
                              @RequestParam String query,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "24") int size,
                              @RequestParam(defaultValue = "title") String sortBy,
                              @RequestParam(defaultValue = "ASC") String sortDirection,
                              Model model) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Book> bookPage = bookService.getAllBooks(pageable);
        List<Book> mostPopularBooks = bookService.getMostPopularBooks();
        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("searchBy", searchBy);
        model.addAttribute("query", query);
        model.addAttribute("mostPopularBooks", mostPopularBooks);
        return "books/list";
    }
}
