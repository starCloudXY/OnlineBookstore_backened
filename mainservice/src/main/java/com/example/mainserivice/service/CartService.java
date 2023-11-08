package com.example.mainserivice.service;




import com.example.mainserivice.entity.Cart;
import com.example.mainserivice.entity.Order;

import java.util.List;

public interface CartService {
    Cart addBookToCart(Integer bookID, Integer userID, Integer amount);

    List<Cart> findCartItemsByUser(Integer userID);

    Order addCartToOrder(Integer userID);

    Cart deleteCartItem(Integer userID, Integer itemID);

    Cart changeAmount(Integer itemID, Integer amount);
    List<Cart> deleteCartItem(List<Cart> cartList);

    void deleteCartByIds(List<Integer>ids);

}

