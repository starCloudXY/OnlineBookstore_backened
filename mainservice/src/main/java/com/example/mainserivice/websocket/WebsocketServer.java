package com.example.mainserivice.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{userid}")
@Component
public class WebsocketServer {
    public static final ConcurrentHashMap<String, Session>SESSIONS = new ConcurrentHashMap<>();
    public void sendMessage(Session toSession,String message){
        if(toSession!=null){
            try {
                toSession.getBasicRemote().sendText(message);
            }
            catch (IOException e){
                e.printStackTrace();;
            }
        }
        else {
            System.out.println("不在线");
        }

    }
    public void sendMessageToUser(String user,String message){
        Session toSession = SESSIONS.get(user);
        if(toSession!=null){
            sendMessage(toSession,message);
            System.out.println("消息成功发送给"+user);
            System.out.println(message);
        }
        else {
            System.out.println("无法连接到用户"+user);
            System.out.println(message);
        }

    }
    @OnMessage
    public void onMessage(String message){
        System.out.println("接收到消息"+message);
    }
    @OnOpen
    public void onOpen(Session session, @PathParam("userid") String userid){
        if(SESSIONS.get(userid)!=null)
            return;
        System.out.println("连接到session"+session+"来自于"+userid);
        SESSIONS.put(userid,session);
    }
    @OnClose
    public void onClose(@PathParam("userid") String userid){
        SESSIONS.remove(userid);
    }
    @OnError
    public void onError(Session session,Throwable throwable){
        System.out.println("发生错误");
        throwable.printStackTrace();
    }
}
