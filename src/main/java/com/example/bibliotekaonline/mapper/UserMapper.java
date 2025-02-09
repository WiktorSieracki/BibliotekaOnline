package com.example.bibliotekaonline.mapper;

import com.example.bibliotekaonline.dto.request.UserRequestDTO;
import com.example.bibliotekaonline.dto.response.UserResponseDTO;
import com.example.bibliotekaonline.model.User;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getReservedBooks().stream().map(BookMapper::toResponseDTO).collect(Collectors.toList()),
                user.getBorrowedBooks().stream().map(BookMapper::toResponseDTO).collect(Collectors.toList()),
                user.getBorrowHistory().stream().map(BookMapper::toResponseDTO).collect(Collectors.toList()),
                user.getRoles()
        );
    }

    public static User toEntity(UserRequestDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }
}