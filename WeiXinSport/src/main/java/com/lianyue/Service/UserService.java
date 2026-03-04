package com.lianyue.Service;

import com.lianyue.pojo.RunRecord;
import com.lianyue.pojo.User;
import com.lianyue.pojo.UserLoginDTO;

import java.util.List;

public interface UserService {
    //登录功能
    User login(UserLoginDTO userLoginDTO);
    //注册功能
    boolean register(User user);
    //查询用户名是否被注册过
    boolean selectUsername(String  username);
    //按照id查询用户信息
    User selectUserById(Integer id);

}
