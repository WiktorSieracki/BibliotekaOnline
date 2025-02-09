package com.example.bibliotekaonline.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;

}
