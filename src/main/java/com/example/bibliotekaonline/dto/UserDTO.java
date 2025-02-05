package com.example.bibliotekaonline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class UserDTO {
    private long id;
    private String name;
    private String email;
    private String password;
    private List<String> roles = new ArrayList<>();
}
