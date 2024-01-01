package com.example.mainserivice.daoimpl;

import com.example.mainserivice.dao.BookDao;
import com.example.mainserivice.dao.OrderDao;
import com.example.mainserivice.entity.*;
import com.example.mainserivice.repository.BookRepository;
import com.example.mainserivice.repository.OrderItemRepository;
import com.example.mainserivice.repository.OrderRepository;
import com.example.mainserivice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookDao bookDao;

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<Orderitem> findOrderItemsByOrder(Order order) {
        return orderItemRepository.findOrderItemsByOrder(order);
    }

    @Override
    public Order findOrderByOrderID(Integer orderID) {
        return orderRepository.getById(orderID);
    }

    @Override
    public List<Object[]> findTopUsersByOrderCount() {
        List<Object[]> allRank = orderRepository.findTopBooksByOrderCount();
        int numberOfTopBooks = 5;
        List<Object[]> topUserSubset = allRank.subList(0, Math.min(numberOfTopBooks, allRank.size()));
        return topUserSubset;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class, isolation = Isolation.READ_COMMITTED)
    public Order addCartToOrder(User user, List<Cart> Carts) {
        System.out.println("I'm Dao");
        Order order = new Order();
        order.setUser(user);
        order.setDate(new Timestamp(new Date().getTime()));
        order.setState("paid");
        orderRepository.saveAndFlush(order);
        for(Cart cart : Carts){
            Orderitem item = new Orderitem();
            item.setAmount(cart.getAmount());
            item.setBook(cart.getBook());
            item.setOrder(order);
            orderItemRepository.saveAndFlush(item);
        }

        return order;
    }
    @Override
    public Order deleteOrderById(Integer itemID) {
        Order order = orderRepository.getReferenceById(itemID);
        List<Orderitem> orderitems=orderItemRepository.findOrderItemsByOrder(order);
        for (Orderitem o:orderitems){
            orderItemRepository.delete(o);
        }
        orderRepository.delete(order);
        return order;
    }
    @Override
    public List<Orderitem> deleteOrderItemsByBook(Book book) {
        List<Orderitem> orderItems = orderItemRepository.findOrderItemsByBook(book);
        for(Orderitem orderItem : orderItems){
            orderItemRepository.delete(orderItem);
        }
        return orderItems;
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Object[]> findTopBooksByOrderCount() {
        List<Object[]> topBooks = orderItemRepository.findTopBooksByOrderCount();
        int numberOfTopBooks = 5; // 设置要获取的前几个书籍数量

        List<Object[]> topBooksSubset = topBooks.subList(0, Math.min(numberOfTopBooks, topBooks.size()));
        return topBooksSubset;
    }

    @Override
    public List<Object[]> findTopBooksByOrderCountFromDateToDate(String start, String end) {
        // 指定日期字符串的格式，与前端传递的格式相匹配
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将日期字符串转换为LocalDate对象
        LocalDate date_s = LocalDate.parse(start, formatter);
        // 将LocalDate对象转换为LocalDateTime对象，并添加时间部分
        LocalDateTime dateTime_s = date_s.atStartOfDay();

        // 将LocalDateTime对象转换为Timestamp对象
        Timestamp startDate = Timestamp.valueOf(dateTime_s);

        // 将日期字符串转换为LocalDate对象
        LocalDate date_e = LocalDate.parse(end, formatter);
        // 将LocalDate对象转换为LocalDateTime对象，并添加时间部分
        LocalDateTime dateTime_e= date_e.atTime(23,59,59);

        // 将LocalDateTime对象转换为Timestamp对象
        Timestamp endDate = Timestamp.valueOf(dateTime_e);
        return orderItemRepository.findTopBooksByOrderCountwithDate(startDate,endDate);
    }

    @Override
    public List<Object[]> findTopUsersByOrderCountFromDateToDate(String start, String end) {
        // 指定日期字符串的格式，与前端传递的格式相匹配
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 将日期字符串转换为LocalDate对象
        LocalDate date_s = LocalDate.parse(start, formatter);
        // 将LocalDate对象转换为LocalDateTime对象，并添加时间部分
        LocalDateTime dateTime_s = date_s.atStartOfDay();

        // 将LocalDateTime对象转换为Timestamp对象
        Timestamp startDate = Timestamp.valueOf(dateTime_s);

        // 将日期字符串转换为LocalDate对象
        LocalDate date_e = LocalDate.parse(end, formatter);
        // 将LocalDate对象转换为LocalDateTime对象，并添加时间部分
        LocalDateTime dateTime_e= date_e.atStartOfDay();

        // 将LocalDateTime对象转换为Timestamp对象
        Timestamp endDate = Timestamp.valueOf(dateTime_e);
        return orderRepository.findTopUsersByOrderCountwithDate(startDate,endDate);
    }


    @Override
    public  Order AddOrder(Integer userID,Double sum){
        Order order=new Order();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~Start to create order baby.O.o~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        order.setUser(userRepository.getReferenceById(userID));
        Date utildate=new Date();
        order.setDate(new java.sql.Timestamp(utildate.getTime()));
        order.setSum(sum);
        order.setState("unpaid");
        orderRepository.saveAndFlush(order);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~Finish to save mature order.O.o~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        return order;
    };

    @Override
    public List<Order> findByUserID(User user) {
        return orderRepository.findOrdersByUser(user);
    }

}
