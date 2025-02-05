package com.example.bibliotekaonline.mapper;


import com.example.bibliotekaonline.dto.UserDTO;
import com.example.bibliotekaonline.model.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
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
