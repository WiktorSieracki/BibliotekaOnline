package com.example.bibliotekaonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class BibliotekaOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliotekaOnlineApplication.class, args);
    }

}
