package com.example.bibliotekaonline;

import com.example.bibliotekaonline.model.User;
import com.example.bibliotekaonline.repository.UserRepository;
import com.example.bibliotekaonline.service.CSVService;
import com.example.bibliotekaonline.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class BibliotekaOnlineApplication implements CommandLineRunner {

    @Autowired
    private CSVService csvService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserRepository userRepository;


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BibliotekaOnlineApplication.class, args);
        User user = context.getBean("user", User.class);
        CustomUserDetailsService customUserDetailsService = context.getBean(CustomUserDetailsService.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
        if (!userRepository.existsByEmail(user.getEmail())){
            customUserDetailsService.saveUser(user);
        }

    }

    @Override
    public void run(String... args) throws Exception {
//        csvService.loadCSVData();
    }
}