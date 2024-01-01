package com.example.mainserivice.serviceimpl;


import com.example.mainserivice.dao.BookDao;
import com.example.mainserivice.dao.CartDao;
import com.example.mainserivice.dao.OrderDao;
import com.example.mainserivice.dao.UserDao;
import com.example.mainserivice.entity.Book;
import com.example.mainserivice.entity.Cart;
import com.example.mainserivice.entity.Order;
import com.example.mainserivice.entity.User;
import com.example.mainserivice.repository.BookRepository;
import com.example.mainserivice.repository.UserRepository;
import com.example.mainserivice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

//    @Autowired
//    OrderDao orderDao;
    @Autowired
CartDao cartDao;
    @Autowired
    UserDao userDao;
    @Autowired
    BookDao bookDao;

    @Autowired
    OrderDao orderDao;

    @Override
    public Cart addBookToCart(Integer bookID, Integer userID, Integer amount) {
        Book book = bookDao.findInRep(bookID);
        User user = userDao.findbyID(userID);
        return cartDao.addCartItem(book, user, amount);
    }
    @Override
    public List<Cart> deleteCartItem(List<Cart> cartList){
        return cartDao.deleteCartItem(cartList);
    }
    @Override
    public List<Cart> findCartItemsByUser(Integer userID) {
        User user = userDao.findbyID(userID);
        List<Cart> carts = cartDao.findCartItemsByUser(user);

        for(Cart cart : carts){
            cart.setBook(bookDao.findbyID(cart.getBook().getId()));
        }
        return carts;
    }

    @Override
    public Order addCartToOrder(Integer userID) {
        User user = userDao.findbyID(userID);
        List<Cart> Carts = cartDao.findCartItemsByUser(user);
        for(Cart cart : Carts){
            Book book = cart.getBook();
            bookDao.changeInventory(book.getId(), cart.getAmount());
            deleteCartItem(userID, cart.getId());
        }
        return orderDao.addCartToOrder(user, Carts);
    }

    @Override
    public Cart deleteCartItem(Integer userID, Integer itemID) {
        return cartDao.deleteCartItem(itemID);
    }

    @Override
    public Cart changeAmount(Integer itemID, Integer amount) {
        return cartDao.changeItemAmount(itemID, amount);
    }
    @Override
    public void deleteCartByIds(List<Integer>ids){
        for(Integer id:ids){
            cartDao.deleteCartItem(id);
        }
    }
}
