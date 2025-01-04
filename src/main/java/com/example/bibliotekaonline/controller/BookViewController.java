package com.example.bibliotekaonline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookViewController {

    @GetMapping
    public String getBooks(){
        return "books/list";
    }
}
