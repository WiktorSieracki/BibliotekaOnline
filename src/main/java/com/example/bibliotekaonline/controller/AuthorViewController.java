package com.example.bibliotekaonline.controller;

import com.example.bibliotekaonline.dto.request.AuthorRequestDTO;
import com.example.bibliotekaonline.mapper.AuthorMapper;
import com.example.bibliotekaonline.model.Author;
import com.example.bibliotekaonline.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorViewController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new AuthorRequestDTO());
        return "author/create";
    }

    @PostMapping("/create")
    public String processCreateForm(@ModelAttribute("author") @Valid Author author, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("author", AuthorMapper.toResponseDTO(author));
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "author/create";
        }
        authorService.saveAuthor(author);
        return "author/create";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", AuthorMapper.toResponseDTO(author));
        return "author/edit";
    }
}