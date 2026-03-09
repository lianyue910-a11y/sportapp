package com.lianyue.Service.impl;

import com.lianyue.Mapper.Studentmapper;
import com.lianyue.Mapper.Usermapper;
import com.lianyue.Service.UserService;
import com.lianyue.Util.Md5;
import com.lianyue.pojo.RunRecord;
import com.lianyue.pojo.User;
import com.lianyue.pojo.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lianyue.Util.Md5;
@Service
public class Userimpl implements UserService {
    @Autowired
    private Usermapper usermapper;
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String password = userLoginDTO.getPassword();
        if (password != null && !password.trim().isEmpty()) {
            userLoginDTO.setPassword(Md5.encrypt(password));
        }
        return usermapper.selectUser(userLoginDTO);
    }

    @Override
    public boolean register(User user) {
        String raw = user.getPassword();
        // 加密成密文
        user.setPassword(Md5.encrypt(raw));
            if (usermapper.insertUser(user) > 0) {
                return true;
            }else {
                throw new RuntimeException("系统出现问题，请联系管理员");
            }
    }

    @Override
    public void updatePassword(Integer userId, String oldPassword, String newPassword) {
        // 1. 查出当前用户
        User user = usermapper.selectUserById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        // 2. 校验旧密码
        String oldMd5 = Md5.encrypt(oldPassword);
        if (!user.getPassword().equals(oldMd5)) {
            throw new RuntimeException("旧密码错误");
        }
        // 3. 加密新密码并更新
        String newMd5 = Md5.encrypt(newPassword);
        user.setPassword(newMd5);
        // 4. 存库
        usermapper.updatePassword(user);
    }

    @Override
    public boolean selectUsername(String username) {
            if (usermapper.selectUsername(username) > 0) {
                return true;
            }else {
                throw new RuntimeException("系统出现问题，请联系管理员");
            }
    }

    @Override
    public User selectUserById(Integer id) {
        return usermapper.selectUserById(id);
    }
}
