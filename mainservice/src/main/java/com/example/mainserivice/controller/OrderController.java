package com.example.mainserivice.controller;


import com.example.mainserivice.entity.Order;
import com.example.mainserivice.entity.OrderData;
import com.example.mainserivice.entity.Orderitem;
import com.example.mainserivice.service.CartService;
import com.example.mainserivice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    CartService cartService;
    @Autowired
    private KafkaTemplate<String, OrderData> kafkaTemplate;
    @GetMapping("/findOnesOrder/{userID}")
    public List<Order> findOnesOrder(@PathVariable Integer userID) {
        return orderService.findOnesOrder(userID);
    }

    @GetMapping("/findOrderItems/{orderID}")
    public List<Orderitem> findOrderItemsByOrderID(@PathVariable Integer orderID) {
        System.out.println("findOrderItems");
        return orderService.findOrderItemsByOrderID(orderID);
    }

    @GetMapping("/findAllOrders")
    public List<Order> findAllOrders(){
        System.out.println("findAllOrders");
        return orderService.findAllOrders();
    }
    @GetMapping("/findTopBooksByOrderCount")
    public List<Object[]> findTopBooksByOrderCount(){
        System.out.println("findTopBooksByOrderCount");
        return orderService.findTopBooksByOrderCount();
    }

    @GetMapping("/findTopUsersByOrderCount")
    public List<Object[]> findTopUsersByOrderCount(){
        System.out.println("findTopBooksByOrderCount");
        return orderService.findTopUsersByOrderCount();
    }
    @PostMapping("/findTopBooksByOrderCountFromDateToDate")
    public List<Object[]> findTopBooksByOrderCount(@RequestBody Map<String, String> params){
        String start = params.get("startDate");
        String end = params.get("endDate");
        return orderService.findTopBooksByOrderCountFromDateToDate(start,end);
    }
    @PostMapping("/findTopUsersByOrderCountFromDateToDate")
    public List<Object[]> findTopUsersByOrderCount(@RequestBody Map<String, String> params){
        String start = params.get("startDate");
        String end = params.get("endDate");
        return orderService.findTopUsersByOrderCountFromDateToDate(start,end);
    }
    @PostMapping("/AddOrders")
    public Order AddOrders(@RequestBody OrderData orderData){
        System.out.println("I am sending !");
        kafkaTemplate.send("order-events","key",orderData);
        System.out.println(" sending finished!");
        return orderService.AddOrders(orderData);
    }
    @PostMapping("/AddOrders/confirm")
    void Confirm(@RequestBody List<Integer> id){
        cartService.deleteCartByIds(id);
    }
    @GetMapping("/AddOrders/cancel/{orderId}")
    void Cancel(@PathVariable Integer orderId){
        orderService.deleteOrderById(orderId);
    }


}
