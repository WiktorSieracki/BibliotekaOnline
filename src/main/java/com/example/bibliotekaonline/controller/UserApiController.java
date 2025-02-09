package com.example.bibliotekaonline.controller;

import com.example.bibliotekaonline.dto.request.UserRequestDTO;
import com.example.bibliotekaonline.dto.response.BookResponseDTO;
import com.example.bibliotekaonline.dto.response.UserResponseDTO;
import com.example.bibliotekaonline.mapper.BookMapper;
import com.example.bibliotekaonline.mapper.UserMapper;
import com.example.bibliotekaonline.model.Book;
import com.example.bibliotekaonline.model.User;
import com.example.bibliotekaonline.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = customUserDetailsService.getAllUsers();
        List<UserResponseDTO> userDTOs = users.stream().map(UserMapper::toResponseDTO).collect(Collectors.toList());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        User savedUser = customUserDetailsService.saveUser(user);
        return new ResponseEntity<>(UserMapper.toResponseDTO(savedUser), HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/reserve/{bookId}")
    public ResponseEntity<BookResponseDTO> reserveBook(@PathVariable long userId, @PathVariable long bookId) {
        Book reservedBook = customUserDetailsService.addBookToReserved(bookId, userId);
        BookResponseDTO reservedBookDTO = BookMapper.toResponseDTO(reservedBook);
        return new ResponseEntity<>(reservedBookDTO, HttpStatus.OK);
    }

    @PostMapping("/{userId}/borrow/{bookId}")
    public ResponseEntity<Void> borrowBook(@PathVariable long userId, @PathVariable long bookId) {
        customUserDetailsService.borrowBookFromReserved(bookId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{userId}/return/{bookId}")
    public ResponseEntity<Void> returnBook(@PathVariable long userId, @PathVariable long bookId) {
        customUserDetailsService.returnBook(bookId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}