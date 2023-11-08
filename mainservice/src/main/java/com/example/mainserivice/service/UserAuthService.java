package com.example.mainserivice.service;


import com.example.mainserivice.entity.UserAuth;

public interface UserAuthService {
    UserAuth checkUser(String username, String password);
}

