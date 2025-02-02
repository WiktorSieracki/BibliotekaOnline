package com.example.bibliotekaonline.service;

import com.example.bibliotekaonline.model.Author;
import com.example.bibliotekaonline.model.Book;
import com.example.bibliotekaonline.repository.AuthorRepository;
import com.example.bibliotekaonline.repository.BookRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Year;
import java.util.*;

@Service
public class CSVService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public void loadCSVData() {
        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource("data.csv").getInputStream()))) {
            List<String[]> rows = reader.readAll();
            for (String[] row : rows.subList(1, rows.size())) { // Skip header row
                boolean hasNullValue = false;
                for (String value : row) {
                    if (value == null || value.isEmpty()) {
                        hasNullValue = true;
                        break;
                    }
                }
                if (hasNullValue) {
                    continue;
                }

                String title = row[0];
                String authorsStr = row[1];
                String categories = row[2];
                String thumbnail = row[3];
                String description = row[4];
                Year publishedYear = Year.parse(row[5]);
                int ratingsCount = Integer.parseInt(row[6]);
                double averageRating = Double.parseDouble(row[7]);
                int numPages = Integer.parseInt(row[8]);
//                System.out.println(title);

                List<Author> authors = new ArrayList<>();
                for (String authorName : authorsStr.split(";")) {
                    Optional<Author> authorOptional = authorRepository.findByName(authorName.trim());
                    Author author;
                    if (authorOptional.isEmpty()) {
                        author = new Author();
                        author.setName(authorName.trim());
                        authorRepository.save(author);
                    }else {
                        author = authorOptional.get();
                    }
                    authors.add(author);
                }

                Book book = new Book();
                book.setTitle(title);
                book.setAuthors(authors);
                book.setCategories(Collections.singletonList(categories));
                book.setThumbnail(thumbnail);
                book.setDescription(description);
                book.setPublishedYear(publishedYear);
                book.setRatingsCount(ratingsCount);
                book.setAverageRating(averageRating);
                book.setNumPages(numPages);

                bookRepository.save(book);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}