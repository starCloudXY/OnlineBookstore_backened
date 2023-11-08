package com.example.mainserivice.dao;


import com.example.mainserivice.entity.UserAuth;

public interface UserAuthDao {
    UserAuth checkUser(String username, String password);

}
