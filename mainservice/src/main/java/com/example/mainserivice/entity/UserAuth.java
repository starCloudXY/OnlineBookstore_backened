package com.example.mainserivice.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_auth", schema = "bookstore", catalog = "")
public class UserAuth {


    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "password")
    private String password;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "id",referencedColumnName = "user")
    private User user;
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAuth userAuth = (UserAuth) o;
        return id == userAuth.id && Objects.equals(id, userAuth.id) && Objects.equals(username, userAuth.username) && Objects.equals(password, userAuth.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
