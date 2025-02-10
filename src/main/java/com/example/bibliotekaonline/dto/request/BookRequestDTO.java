package com.example.bibliotekaonline.dto.request;

import com.example.bibliotekaonline.dto.response.AuthorResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookRequestDTO {
    private String title;
    private List<String> categories;
    private Year publishedYear;
    private Integer numPages;
    private String description;
    private String thumbnail;
    private Integer ratingsCount;
    private Double averageRating;
    private List<AuthorRequestDTO> authors;

}