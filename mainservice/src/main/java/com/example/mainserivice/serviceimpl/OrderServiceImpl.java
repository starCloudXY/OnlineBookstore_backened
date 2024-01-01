package com.example.mainserivice.serviceimpl;

import com.example.mainserivice.dao.BookDao;
import com.example.mainserivice.dao.OrderDao;
import com.example.mainserivice.dao.OrderItemDao;
import com.example.mainserivice.dao.UserDao;
import com.example.mainserivice.entity.*;
import com.example.mainserivice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private BookDao bookDao;
    @Autowired
    private OrderItemDao orderItemDao;

    @Override
    public List<Order> findOnesOrder(Integer userID) {
        User user = userDao.findbyID(userID);
        return orderDao.findByUserID(user);
    }
    @Override
    public Order findOrderbyId(Integer orderID){
        return orderDao.findOrderByOrderID(orderID);
    }
    @Override
    public List<Orderitem> findOrderItemsByOrderID(Integer orderID) {
        List<Orderitem> orderItems = orderDao.findOrderItemsByOrder(orderDao.findOrderByOrderID(orderID));
        for(Orderitem o : orderItems){
            o.setBook(bookDao.findbyID(o.getBook().getId()));
        }
        return orderItems;
    }

    @Override
    public List<Order> findAllOrders() {
        return orderDao.findAllOrders();
    }
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Order AddOrders(OrderData data){
        System.out.println("Begin Adding Order by "+this.orderDao);
        Order order = orderDao.AddOrder(data.getUserid(),data.sum);
        List<Item> items = data.getItems();
        for(Item o : items){
            orderItemDao.AddOrderItem(o,order);
        }
        System.out.println("Finish Adding Order by "+this.orderDao);
        return order;
    }
    @Override
    public Order deleteOrderById(Integer id){
        return orderDao.deleteOrderById(id);
    };
    @Override
    public List<Object[]> findTopBooksByOrderCount() {
        return orderDao.findTopBooksByOrderCount();
    }
    @Override
    public List<Object[]> findTopUsersByOrderCount() {
        return orderDao.findTopUsersByOrderCount();
    }

    @Override
    public List<Object[]> findTopBooksByOrderCountFromDateToDate(String start, String end) {
        return orderDao.findTopBooksByOrderCountFromDateToDate(start,end);
    }
    @Override
    public List<Object[]> findTopUsersByOrderCountFromDateToDate(String start, String end) {
        return orderDao.findTopUsersByOrderCountFromDateToDate(start,end);
    }

}
