package com.example.bibliotekaonline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {

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
}