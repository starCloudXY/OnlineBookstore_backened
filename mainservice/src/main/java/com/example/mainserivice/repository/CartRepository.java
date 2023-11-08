package com.example.mainserivice.repository;

import com.example.mainserivice.entity.Book;
import com.example.mainserivice.entity.Cart;
import com.example.mainserivice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findCartsByUser(User user);

    List<Cart> findCartsByBookAndUser(Book book, User user);

    List<Cart> findCartsByBook(Book book);
}
