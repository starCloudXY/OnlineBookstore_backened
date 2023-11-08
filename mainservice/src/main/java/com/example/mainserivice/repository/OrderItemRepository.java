package com.example.mainserivice.repository;


import com.example.mainserivice.entity.Book;
import com.example.mainserivice.entity.Order;
import com.example.mainserivice.entity.Orderitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<Orderitem, Integer> {
    List<Orderitem> findOrderItemsByOrder(Order order);
    void deleteByOrder(Order order);
    List<Orderitem> findOrderItemsByBook(Book book);
    @Query("SELECT oi.book.id,oi.book.name, SUM(oi.amount) AS totalAmount " +
            "FROM Orderitem oi " +
            "GROUP BY oi.book.id " +
            "ORDER BY totalAmount DESC")
    List<Object[]> findTopBooksByOrderCount();
    @Query("SELECT oi.book.id,oi.book.name, SUM(oi.amount) AS totalAmount " +
            "FROM Orderitem oi " +
            "INNER JOIN oi.order o " +
            "WHERE o.date >= :startDate AND o.date <= :endDate " +
            "GROUP BY oi.book.id " +
            "ORDER BY totalAmount DESC")
    List<Object[]> findTopBooksByOrderCountwithDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
//    @Query("SELECT oi.book, SUM(oi.amount) AS totalAmount, COUNT(oi) AS totalBooks, SUM(oi.amount * oi.book.price) AS totalPrice" +
//            "FROM Orderitem oi " +
//            "WHERE oi.date >= :startDate AND o.date <= :endDate " +
//            "AND oi.order.user = :user "+
//            "GROUP BY o.user.user " +
//            "GROUP BY oi.book"+
//            "ORDER BY totalAmount DESC")
//    List<Object[]> findBooksBuyingWithDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate, User user);

}



