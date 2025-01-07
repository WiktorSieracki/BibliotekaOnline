package com.example.bibliotekaonline.repository;

import com.example.bibliotekaonline.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}