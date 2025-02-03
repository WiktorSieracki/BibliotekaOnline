package com.example.bibliotekaonline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserDTO {
    private long id;
    private String name;
    private String email;
    private String password;
}
