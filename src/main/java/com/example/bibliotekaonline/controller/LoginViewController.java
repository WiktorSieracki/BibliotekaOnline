package com.example.bibliotekaonline.controller;

import com.example.bibliotekaonline.model.User;
import com.example.bibliotekaonline.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginViewController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/login")
    public String login() {
       return "loginView";
    }

    @GetMapping("/success")
    public String defaultAfterLogin() {
       return "redirect:/books";
    }

    @GetMapping("/logout")
    public String logoutSuccess() {
       return "logout";
    }

    @GetMapping("/logout-success")
    public String logoutSuccessView() {
       return "redirect:/books";
    }

    @GetMapping("/profile")
    public String getUserProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = customUserDetailsService.getUserByEmail(email);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/users/{userId}/borrow/{bookId}")
    public String getBorrowBook(@PathVariable long userId, @PathVariable long bookId, Model model) {
        customUserDetailsService.borrowBookFromReserved(bookId, userId);
        return "redirect:/profile";
    }

    @GetMapping("users/{userId}/removeReserve/{bookId}")
    public String getRemoveReserveBook(@PathVariable long userId, @PathVariable long bookId, Model model) {
        customUserDetailsService.removeBookFromReserved(bookId, userId);
        return "redirect:/profile";
    }

    @GetMapping("/users/{userId}/reserve/{bookId}")
    public String getReserveBook(@PathVariable long userId, @PathVariable long bookId, Model model) {
        customUserDetailsService.addBookToReserved(bookId, userId);
        return "redirect:/profile";
    }

    @GetMapping("/users/{userId}/return/{bookId}")
    public String getReturnBook(@PathVariable long userId, @PathVariable long bookId, Model model) {
        customUserDetailsService.returnBook(bookId, userId);
        return "redirect:/profile";
    }
}