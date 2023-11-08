package com.example.mainserivice.service;




import com.example.mainserivice.entity.Order;
import com.example.mainserivice.entity.OrderData;
import com.example.mainserivice.entity.Orderitem;

import java.util.List;

public interface OrderService {

    List<Order> findOnesOrder(Integer userID);

    List<Orderitem> findOrderItemsByOrderID(Integer orderID);

    List<Order> findAllOrders();
    Order AddOrders(OrderData data);

    Order deleteOrderById(Integer id);
    List<Object[]>findTopBooksByOrderCount();
    List<Object[]> findTopUsersByOrderCount();
    List<Object[]>findTopBooksByOrderCountFromDateToDate(String start,String end);
    List<Object[]>findTopUsersByOrderCountFromDateToDate(String start,String end);

}
