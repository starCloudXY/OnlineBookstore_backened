package com.example.mainserivice.daoimpl;


import com.example.mainserivice.dao.OrderItemDao;
import com.example.mainserivice.entity.Item;
import com.example.mainserivice.entity.Order;
import com.example.mainserivice.entity.Orderitem;
import com.example.mainserivice.repository.BookRepository;
import com.example.mainserivice.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemDaoImpl  implements OrderItemDao {
    @Autowired
    OrderItemRepository orderItemRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Orderitem AddOrderItem(Item item, Order order){
        Orderitem orderitem = new Orderitem();
        System.out.println("=> Start to create order item.O.o");
        orderitem.setBook(bookRepository.getReferenceById(item.getId()));
        orderitem.setAmount(item.getAmount());
        orderitem.setOrder(order);
        orderItemRepository.saveAndFlush(orderitem);
        System.out.println("=> Finish to create order item."+orderitem);
        return orderitem;
    };
}
