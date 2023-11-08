package com.example.mainserivice.repository;

import com.example.mainserivice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> { }



