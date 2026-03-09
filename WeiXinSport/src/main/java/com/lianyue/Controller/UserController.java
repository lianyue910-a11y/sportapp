package com.lianyue.Controller;

import com.lianyue.Service.UserService;
import com.lianyue.Util.JwtUtil;
import com.lianyue.pojo.Result;
import com.lianyue.pojo.User;
import com.lianyue.pojo.UserLoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            // 1. 生成 Token
            String token = JwtUtil.createToken(user.getId(), user.getRole());
            // 2. 封装返回数据
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("userInfo", user); // 用户信息也带上，前端要用
            return Result.success(map);
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
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody Map<String, String> params, HttpServletRequest request) {
        // 1. 从 Token 获取当前用户 ID
        Integer userId = (Integer) request.getAttribute("userId");
        System.out.println(userId);
        // 2. 获取参数
        String oldPassword = params.get("oldPassword");
        String newPassword = params.get("newPassword");
        // 3. 判空
        if (StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
            return Result.error("密码不能为空");
        }
        // 4. 调用业务逻辑
        userService.updatePassword(userId, oldPassword, newPassword);
        return Result.success("修改成功，请重新登录");
    }
}
