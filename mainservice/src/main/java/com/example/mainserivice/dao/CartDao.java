package com.example.mainserivice.dao;


import com.example.mainserivice.entity.Book;
import com.example.mainserivice.entity.Cart;
import com.example.mainserivice.entity.User;

import java.util.List;

public interface CartDao {
    Cart addCartItem(Book book, User user, Integer amount);

    Cart deleteCartItem(Integer itemID);
    List<Cart> deleteCartItem(List<Cart> cartList);
    Cart changeItemAmount(Integer itemID, Integer amount);

    List<Cart> findCartItemsByUser(User user);
    void deleteCartItemsByBook(Book book);
}
