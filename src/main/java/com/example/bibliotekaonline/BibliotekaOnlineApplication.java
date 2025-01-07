package com.example.bibliotekaonline;

import com.example.bibliotekaonline.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class BibliotekaOnlineApplication implements CommandLineRunner {

    @Autowired
    private CSVService csvService;

    public static void main(String[] args) {
        SpringApplication.run(BibliotekaOnlineApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        csvService.loadCSVData();
    }
}