package com.example.mainserivice.dao;



import com.example.mainserivice.entity.User;

import java.util.List;

public interface UserDao {
    User findbyID(int id);
    List<User> findAll();
    User changeOnesBanState(Integer UserID);
    User deleteUser(Integer ID);

    User addUser(String username, String password, String name, String nickname,String tel, String email, String address, String image);
}
