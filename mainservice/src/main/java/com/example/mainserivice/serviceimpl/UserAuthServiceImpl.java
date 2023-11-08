package com.example.mainserivice.serviceimpl;

import com.example.mainserivice.dao.UserAuthDao;
import com.example.mainserivice.entity.UserAuth;
import com.example.mainserivice.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Autowired
    private UserAuthDao userAuthDao;

    @Override
    public UserAuth checkUser(String username, String password){
        return userAuthDao.checkUser(username,password);
    }
}
