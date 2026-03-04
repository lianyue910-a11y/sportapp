package com.lianyue.Controller;

import com.lianyue.Service.UserService;
import com.lianyue.pojo.Result;
import com.lianyue.pojo.User;
import com.lianyue.pojo.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        User user = userService.login(userLoginDTO);
        if (user == null) {
            return Result.error("用户名或密码错误");
        } else {
            user.setPassword(null);
            return Result.success(user);
        }
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        //判断账号是否已经注册过
        boolean user1 = userService.selectUsername(user.getUsername(user));
        if (user1){
            return Result.error("用户已存在");
        }else {
            boolean register = userService.register(user);
            if (register) {
                user.setPassword(null);
                return Result.success(user);
            }else {
                return Result.error("注册失败,请重新尝试或联系管理员");
            }
        }

    }

}
