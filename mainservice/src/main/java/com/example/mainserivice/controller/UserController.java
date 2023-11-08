    package com.example.mainserivice.controller;



        import com.example.mainserivice.entity.User;
        import com.example.mainserivice.service.TimeService;
        import com.example.mainserivice.service.UserAuthService;
        import com.example.mainserivice.service.UserService;
        import jakarta.servlet.http.HttpServletRequest;
        import jakarta.servlet.http.HttpSession;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.context.annotation.Scope;
        import org.springframework.web.bind.annotation.*;
        import org.springframework.web.context.WebApplicationContext;

        import java.util.List;
        import java.util.Map;


@RestController
@Scope("session")
public class UserController {

    @Autowired
    WebApplicationContext applicationContext;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserService userService;
    @Autowired
    private TimeService timeService;
    @RequestMapping("/login")
    public User login(@RequestBody Map<String, String> params, HttpServletRequest request) {
//        userService = applicationContext.getBean(UserService.class);
        String username = params.get("username");
        String password = params.get("password");
        User user = userService.findUserByPasswd(username, password);
//        timeService =  applicationContext.getBean(TimeService.class);
        timeService.startSession();
        System.out.println(this);
        System.out.println(timeService);
        HttpSession session = request.getSession(); // 获取当前请求的会话对象

        return user;
    }
    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @PostMapping("/changeBanState/{userID}")
    public User banUser(@PathVariable Integer userID){
        return userService.changeOnesBanState(userID);
    }
    @PostMapping("/removeUser/{userID}")
    public User deleteUser(@PathVariable Integer userID){return userService.deleteUser(userID);}
    @PostMapping("/signUp")
    public User signUp(@RequestBody Map<String, String> params) {
        System.out.println("I'm controller");
        String username = params.get("username");
        String password = params.get("password");
        String nickname = params.get("nickname");
        String name = params.get("name");
        String tel = params.get("tel");
        String email = params.get("email");
        String address = params.get("address");
        return userService.addUser(username, password, name,nickname, tel, email, address);
    }
    @PostMapping("/logout")
    @ResponseBody
    public Long logout() {
        long sessionDuration = timeService.durationTime();
        System.out.println(this);
        System.out.println(timeService);
        return sessionDuration;
    }
}
