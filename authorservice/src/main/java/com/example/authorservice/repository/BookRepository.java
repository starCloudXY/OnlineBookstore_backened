package com.example.authorservice.repository;

import com.example.authorservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Override
    List<Book> findAll();
    @Query(value = "from Book where name like :bookname")
    Book getAuthorByBookName(@Param("bookname") String bookname);
}
