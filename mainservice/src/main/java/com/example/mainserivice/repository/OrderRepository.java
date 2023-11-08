package com.example.mainserivice.repository;


import com.example.mainserivice.entity.Order;
import com.example.mainserivice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findOrdersByUser(User user);
    List<Order> findOrdersByDateBetween(Date date1, Date date2);

    List<Order> findOrdersByDateBetweenAndUser(Date date1, Date date2, User user);
    @Query("SELECT oi.user.user,oi.user.name, SUM(oi.sum) AS totalAmount " +
            "FROM Order oi " +
            "GROUP BY oi.user.user " +
            "ORDER BY totalAmount DESC")
    List<Object[]> findTopBooksByOrderCount();
    @Query("SELECT o.user.user,o.user.name, SUM(o.sum)  AS totalAmount " +
            "FROM Order o " +
            "WHERE o.date >= :startDate AND o.date <= :endDate " +
            "GROUP BY o.user.user " +
            "ORDER BY totalAmount DESC")
    List<Object[]> findTopUsersByOrderCountwithDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}

