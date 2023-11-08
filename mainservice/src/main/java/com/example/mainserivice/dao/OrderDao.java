package com.example.mainserivice.dao;

import com.example.mainserivice.entity.*;

import java.util.List;

public interface OrderDao {

    List<Order> findByUserID(User user);

    List<Orderitem> findOrderItemsByOrder(Order order);
    Order AddOrder(Integer userID,Double sum);
    Order findOrderByOrderID(Integer orderID);
    public List<Object[]> findTopUsersByOrderCount();
    Order addCartToOrder(User user, List<Cart> Carts);
    Order deleteOrderById(Integer itemID);
    List<Orderitem> deleteOrderItemsByBook(Book book);
//    void DeleteOrder(Integer id);
    List<Order> findAllOrders();

    List<Object[]> findTopBooksByOrderCount();
    List<Object[]>findTopBooksByOrderCountFromDateToDate(String start,String end);
    List<Object[]>findTopUsersByOrderCountFromDateToDate(String start,String end);
}
