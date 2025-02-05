package com.example.bibliotekaonline.mapper;


import com.example.bibliotekaonline.dto.UserDTO;
import com.example.bibliotekaonline.model.User;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getReservedBooks().stream().map(BookMapper::toDTO).collect(Collectors.toList()),
                user.getBorrowedBooks().stream().map(BookMapper::toDTO).collect(Collectors.toList()),
                user.getBorrowHistory().stream().map(BookMapper::toDTO).collect(Collectors.toList()),
                user.getRoles()
        );
    }

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRoles(userDTO.getRoles());
        return user;
    }


}
