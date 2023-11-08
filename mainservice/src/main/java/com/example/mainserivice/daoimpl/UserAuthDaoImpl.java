package com.example.mainserivice.daoimpl;


import com.example.mainserivice.dao.UserAuthDao;
import com.example.mainserivice.entity.UserAuth;
import com.example.mainserivice.repository.UserAuthRepository;
import com.example.mainserivice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserAuthDaoImpl implements UserAuthDao {
    @Autowired
    UserAuthRepository userAuthRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserAuth checkUser(String username, String password){
        return userAuthRepository.checkUser(username, password);
        }


}
