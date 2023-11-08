package com.example.mainserivice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user", schema = "bookstore", catalog = "")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user")
    private int user;


    @Basic
    @Column(name = "ban")
    private boolean ban;
    @Basic
    @Column(name = "image")
    private String image;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "nickname")
    private String nickname;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "tel")
    private String tel;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "user_type")
    private int userType;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public int getUser() {
        return user;
    }
    public void CancelBan(){
        ban = false;
    }
    public void SetBan() { ban = true; }
    public void setUser(int user) {
        this.user = user;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isBan() {
        return ban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user1 = (User) o;
        return user == user1.user && Objects.equals(nickname, user1.nickname) && Objects.equals(name, user1.name) && Objects.equals(tel, user1.tel) && Objects.equals(address, user1.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, nickname, name, tel, address);
    }
}
