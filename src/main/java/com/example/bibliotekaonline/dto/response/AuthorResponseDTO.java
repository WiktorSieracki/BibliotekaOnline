package com.example.bibliotekaonline.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class AuthorResponseDTO {
    private Long id;
    private String name;

}