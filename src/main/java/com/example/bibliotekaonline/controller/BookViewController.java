package com.example.bibliotekaonline.controller;

import com.example.bibliotekaonline.dto.request.BookRequestDTO;
import com.example.bibliotekaonline.dto.request.CommentRequestDTO;
import com.example.bibliotekaonline.dto.response.CommentResponseDTO;
import com.example.bibliotekaonline.mapper.AuthorMapper;
import com.example.bibliotekaonline.mapper.BookMapper;
import com.example.bibliotekaonline.model.Author;
import com.example.bibliotekaonline.model.Book;
import com.example.bibliotekaonline.model.User;
import com.example.bibliotekaonline.service.AuthorService;
import com.example.bibliotekaonline.service.BookService;
import com.example.bibliotekaonline.service.CommentService;
import com.example.bibliotekaonline.service.CustomUserDetailsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BookViewController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AuthorService authorService;

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
        Book book = bookService.getBookById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = customUserDetailsService.getUserByEmail(email);
        CommentRequestDTO newComment = new CommentRequestDTO();
        model.addAttribute("user", user);
        model.addAttribute("book", book);
        model.addAttribute("newComment", newComment);
        return "books/details";
    }

    @PostMapping("/{bookId}/comments")
    public String addComment(@PathVariable Long bookId, @Valid @ModelAttribute("newComment") CommentRequestDTO commentDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Book book = bookService.getBookById(bookId);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = customUserDetailsService.getUserByEmail(email);
            model.addAttribute("user", user);
            model.addAttribute("book", book);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "books/details";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = customUserDetailsService.getUserByEmail(email);
        commentService.saveComment(bookId, user.getId(), commentDTO);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/{bookId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable Long bookId, @PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return "redirect:/books/" + bookId;
    }

    @PostMapping("/{id}/rating")
    public String addRating(@PathVariable Long id, @RequestParam int rating, Model model) {
        if (rating < 1 || rating > 5) {
            model.addAttribute("error", "Rating must be between 1 and 5");
            return "redirect:/books/" + id;
        }
        Book book = bookService.getBookById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = customUserDetailsService.getUserByEmail(email);
        bookService.postBookReview(book, rating);
        model.addAttribute("user", user);
        model.addAttribute("book", book);
        return "redirect:/books/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        List<Author> allAuthors = authorService.getAllAuthors();
        List<String> allCategories = Arrays.asList("Fiction", "Non-Fiction", "Science", "History", "Biography");
        Book book = bookService.getBookById(id);
        model.addAttribute("book", BookMapper.toResponseDTO(book));
        model.addAttribute("allAuthors", allAuthors.stream().map(AuthorMapper::toResponseDTO).collect(Collectors.toList()));
        model.addAttribute("allCategories", allCategories);
        return "books/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, @Valid @ModelAttribute Book book, BindingResult bindingResult, Model model) {
        List<Author> allAuthors = authorService.getAllAuthors();
        List<String> allCategories = Arrays.asList("Fiction", "Non-Fiction", "Science", "History", "Biography");
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", BookMapper.toResponseDTO(book));
            model.addAttribute("allAuthors", allAuthors.stream().map(AuthorMapper::toResponseDTO).collect(Collectors.toList()));
            model.addAttribute("allCategories", allCategories);
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "books/edit";
        }
        bookService.saveBook(book);
        return "redirect:/books/" + id;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        List<Author> allAuthors = authorService.getAllAuthors();
        model.addAttribute("allAuthors", allAuthors.stream().map(AuthorMapper::toResponseDTO).collect(Collectors.toList()));
        List<String> allCategories = Arrays.asList("Fiction", "Non-Fiction", "Science", "History", "Biography");
        model.addAttribute("allCategories", allCategories);
        return "books/create";
    }

    @PostMapping("/create")
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult result, Model model) {
        List<Author> allAuthors = authorService.getAllAuthors();
        List<String> allCategories = Arrays.asList("Fiction", "Non-Fiction", "Science", "History", "Biography");
        if (result.hasErrors()) {
            model.addAttribute("book", BookMapper.toResponseDTO(book));
            model.addAttribute("allAuthors", allAuthors.stream().map(AuthorMapper::toResponseDTO).collect(Collectors.toList()));
            model.addAttribute("allCategories", allCategories);
            model.addAttribute("errors", result.getAllErrors());
            return "books/create";
        }
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
        Page<Book> bookPage = bookService.searchBooks(searchBy, query, pageable);
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