package com.example.mainserivice.service;


import com.example.mainserivice.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {

    User findUserById(Integer id);
    User findUserByPasswd(String username, String password);
    List<User> getAllUsers();
    User changeOnesBanState(Integer userID);
    User deleteUser(Integer ID);

    User addUser(String username, String password, String name, String nickname,String tel, String email, String address) ;

}



