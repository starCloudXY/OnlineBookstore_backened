package com.example.mainserivice.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "userIcon")
public class UserImage {
    @Id
    private String id;
    private String username;
    private String iconBase64;
    public UserImage(String username,String iconBase64){
        this.username = username;
        this.iconBase64 = iconBase64;
    }
    public String getIconBase64(){
        return iconBase64;
    }
    public String getUsername(){
        return username;
    }
    public void setUsername(){
        this.username = username;
    }
    public void setIconBase64(){
        this.iconBase64 = iconBase64;
    }

}
