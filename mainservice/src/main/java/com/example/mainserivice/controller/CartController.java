
package com.example.mainserivice.controller;

import com.example.mainserivice.entity.Cart;
import com.example.mainserivice.entity.Order;
import com.example.mainserivice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("/findCart/{userID}")
    public List<Cart> findCartItemsByUser(@PathVariable Integer userID) {
        System.out.println("findCart");
        return cartService.findCartItemsByUser(userID);
    }
    @PostMapping("/deleteCarts")
    public List<Cart>  AddOrders(@RequestBody List<Cart> cartList){
        return cartService.deleteCartItem(cartList);
    }
    @PostMapping("/delete/{userID}/{itemID}")
    public Cart deleteCartItem(@PathVariable Integer userID, @PathVariable Integer itemID) {
        return cartService.deleteCartItem(userID, itemID);
    }

    @PostMapping("/changeAmount/{itemID}/{amount}")
    public Cart changeItemAmount(@PathVariable Integer itemID, @PathVariable Integer amount) {
        return cartService.changeAmount(itemID, amount);
    }

    @PostMapping("/addCartItem/{bookID}/{amount}/{userID}")
    public Cart addCartItem(@PathVariable("bookID") Integer bookID,
                            @PathVariable("amount") Integer amount,
                            @PathVariable("userID") Integer userID) {
        return cartService.addBookToCart(bookID, userID, amount);
    }

    @PostMapping("/addCartToOrder/{userID}")
    public Order addOrder(@PathVariable Integer userID) {
        return cartService.addCartToOrder(userID);
    }
}
