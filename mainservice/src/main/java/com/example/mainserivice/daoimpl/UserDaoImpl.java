package com.example.mainserivice.daoimpl;

import com.example.mainserivice.dao.UserDao;
import com.example.mainserivice.entity.User;
import com.example.mainserivice.entity.UserAuth;
import com.example.mainserivice.repository.UserAuthRepository;
import com.example.mainserivice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAuthRepository userAuthRepository;

    @Override
    public User findbyID(int id){
        return userRepository.getReferenceById(id);
    }
    @Override
    public User changeOnesBanState(Integer UserID){
        User user = userRepository.getReferenceById(UserID);
        if(user.isBan()) user.CancelBan();
        else user.SetBan();
        userRepository.saveAndFlush(user);
        return user;
    }
    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();

        return users;
    }
    @Override
    public User deleteUser(Integer ID){
        User user = userRepository.getReferenceById(ID);
        userRepository.delete(user);
        return user;
    }


    @Override
    public User addUser(String username, String password, String name, String nickname,String tel, String email, String address, String image) {
        System.out.println("I am adding user");
        List<UserAuth> userAuthList=userAuthRepository.findAll();
        for(UserAuth u : userAuthList){
            if(u.getUsername().equals(username)){
                System.out.println(u);
                User noUser = new User();
                noUser.setUserType(-2);
                System.out.println("There is user");
                return noUser;
            }
        }
        User user=new User();
        UserAuth userAuth=new UserAuth();
        userAuth.setUsername(username);
        userAuth.setPassword(password);
        System.out.println("I am adding userauth");
        user.setUserType(0);
        user.setName(name);
        user.setNickname(nickname);
        user.setTel(tel);
        user.setEmail(email);
        user.setImage(image);
        user.setNickname(nickname);
        System.out.println("I am saving user");
        userRepository.saveAndFlush(user);
        System.out.println(" saving user finished");
//        userAuth.setUser(user);
        userAuth.setId(user.getUser());
        userAuthRepository.saveAndFlush(userAuth);
        System.out.println("I am saving user");
        return user;
    }
}
