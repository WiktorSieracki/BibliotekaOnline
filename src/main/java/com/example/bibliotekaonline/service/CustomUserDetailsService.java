package com.example.bibliotekaonline.service;

import com.example.bibliotekaonline.dto.request.UserRequestDTO;
import com.example.bibliotekaonline.model.Book;
import com.example.bibliotekaonline.model.User;
import com.example.bibliotekaonline.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BookService bookService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.getRoles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public Book addBookToReserved(long bookId, long userId) {
        Book book = bookService.getBookById(bookId);
        User user = getUserById(userId);
        
        user.getReservedBooks().add(book);
        userRepository.save(user);
        
        return book;
    }

    public void removeBookFromReserved(long bookId, long userId) {
        Book book = bookService.getBookById(bookId);
        User user = getUserById(userId);

        user.getReservedBooks().remove(book);
        userRepository.save(user);
    }
    @Transactional
    public void borrowBookFromReserved(long bookId, long userId) {
        User user = getUserById(userId);
        Book book = bookService.getBookById(bookId);

        if (user.getReservedBooks().contains(book)) {
            user.getReservedBooks().remove(book);
            user.getBorrowedBooks().add(book);
            if (!user.getBorrowHistory().contains(book)) {
                user.getBorrowHistory().add(book);
            }
            userRepository.save(user);
        } else {
            throw new IllegalStateException("Book is not in the reserved list");
        }
    }

    @Transactional
    public void returnBook(long bookId, long userId) {
        User user = getUserById(userId);
        Book book = bookService.getBookById(bookId);

        if (user.getBorrowedBooks().contains(book)) {
            user.getBorrowedBooks().remove(book);
            userRepository.save(user);
        } else {
            throw new IllegalStateException("Book is not in the borrowed list");
        }
    }

     @PostConstruct
     public void createAdminUser() {
         if (!userRepository.existsByEmail("admin@example.com")) {
             User admin = new User();
             admin.setEmail("admin@example.com");
             admin.setName("admin");
             admin.setPassword("password");
             admin.setRoles(List.of("ROLE_ADMIN"));
             saveUser(admin);
         }
     }

    @Transactional
    public User saveUser(User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }else{
            throw new EntityExistsException("User already exists with email: " + user.getEmail());
        }
    }

    @Transactional
    public void deleteUser(long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
    }

    @Transactional
    public User updateUser(long userId, UserRequestDTO userDTO) {
        User existingUser = getUserById(userId);
        existingUser.setName(userDTO.getName());

        if (!Objects.equals(existingUser.getEmail(), userDTO.getEmail())) {
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw new EntityExistsException("User already exists with email: " + userDTO.getEmail());
            }
            existingUser.setEmail(userDTO.getEmail());
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        return userRepository.save(existingUser);
}
}