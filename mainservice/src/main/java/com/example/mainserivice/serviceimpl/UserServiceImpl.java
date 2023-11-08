
package com.example.mainserivice.serviceimpl;


import com.example.mainserivice.dao.UserAuthDao;
import com.example.mainserivice.dao.UserDao;
import com.example.mainserivice.entity.User;
import com.example.mainserivice.entity.UserAuth;
import com.example.mainserivice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope("prototype")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserAuthDao userAuthDao;
    @Override
    public User findUserById(Integer id) {
        return userDao.findbyID(id);
    }
    @Override
    public User findUserByPasswd(String username, String password){
        UserAuth userAuth=userAuthDao.checkUser(username,password);

        if(userAuth==null)return null;

        else return userAuth.getUser();
    }
    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
    @Override
    public User changeOnesBanState(Integer userID){
        return userDao.changeOnesBanState(userID);
    }
    @Override
    public User deleteUser(Integer ID){
        return  userDao.deleteUser(ID);
    };
    @Override
    public  User addUser(String username, String password, String name, String nickname,String tel, String email, String address) {
        String image = "https://i01piccdn.sogoucdn.com/1537b3add45b3814";
        return userDao.addUser(username, password, name,nickname, tel, email, address, image);
    }

    ;
}
