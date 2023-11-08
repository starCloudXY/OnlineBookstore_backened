package com.example.mainserivice.dao;


import com.example.mainserivice.entity.Item;
import com.example.mainserivice.entity.Order;
import com.example.mainserivice.entity.Orderitem;

public interface OrderItemDao {
    Orderitem AddOrderItem(Item item, Order order);
}
