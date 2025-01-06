package com.example.bibliotekaonline.repository;

import com.example.bibliotekaonline.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}