package com.example.mainserivice.component;

import com.example.mainserivice.entity.Order;
import com.example.mainserivice.entity.OrderData;
import com.example.mainserivice.service.OrderService;
import com.example.mainserivice.websocket.WebsocketServer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonProperties;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

@Component
public class BookstoreListener {
    @Autowired
    private WebsocketServer websocketServer;
    @Autowired
    private OrderService orderService;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    public String parseUser(String finishMessage) {
        // 首先，将字符串按换行符分割成多行
        String[] lines = finishMessage.split("\\n");

        // 遍历每一行，查找包含"[用户:"的行
        for (String line : lines) {
            if (line.contains("[用户:")) {
                // 找到包含"[用户:"的行后，提取用户名
                int startIndex = line.indexOf("[用户:") + "[用户:".length();
                int endIndex = line.indexOf("]", startIndex);
                if (endIndex > startIndex) {
                    return line.substring(startIndex, endIndex);
                }
            }
        }
        // 如果未找到匹配的行，返回null或者适当的默认值
        return null;
    }
    @KafkaListener(topics = "order-events", groupId = "order-test")
    public void orderListener(ConsumerRecord<String,OrderData>record){
    System.out.println("I am getting message!");
    System.out.println(record.value());
    OrderData orderData=record.value();
    Order order=orderService.AddOrders(orderData);
    System.out.println("I am getting message finishing!");
    String FinishMessage="订单完成:\n"+"[用户:"+orderData.getUserid()+"]"+"[订单号:"+order.getId()+"]\n[时间:"+order.getDate()+"]\n[总价:"+order.getSum()+"]\n";
    kafkaTemplate.send("finished-events", "key", FinishMessage);
    System.out.println("监听到订单消息");
    }
    @KafkaListener(topics = "finished-events",groupId = "finished-test")
    public void orderFinishedListener(ConsumerRecord<String,String>record){
        System.out.println("I am getting message finished!");
        System.out.println(record);
        String userId = parseUser(record.value());
        websocketServer.sendMessageToUser(userId,record.value());
    }
}
