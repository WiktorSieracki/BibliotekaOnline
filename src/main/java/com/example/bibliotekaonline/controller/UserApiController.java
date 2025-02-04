package com.example.bibliotekaonline.controller;

import com.example.bibliotekaonline.dto.UserDTO;
import com.example.bibliotekaonline.mapper.UserMapper;
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
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = customUserDetailsService.getAllUsers();
        List<UserDTO> userDTOs = users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        User savedUser = customUserDetailsService.saveUser(user);
        return new ResponseEntity<>(UserMapper.toDTO(savedUser), HttpStatus.CREATED);
    }

}