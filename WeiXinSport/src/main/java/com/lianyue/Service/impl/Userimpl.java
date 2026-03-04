package com.lianyue.Service.impl;

import com.lianyue.Mapper.Studentmapper;
import com.lianyue.Mapper.Usermapper;
import com.lianyue.Service.UserService;
import com.lianyue.pojo.RunRecord;
import com.lianyue.pojo.User;
import com.lianyue.pojo.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class Userimpl implements UserService {
    @Autowired
    private Usermapper usermapper;
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        try {
            if (usermapper != null) {
                return usermapper.selectUser(userLoginDTO);
            }else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean register(User user) {
        try {
            if (usermapper.insertUser(user) > 0) {
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean selectUsername(String username) {
        try {
            if (usermapper.selectUsername(username) > 0) {
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public User selectUserById(Integer id) {
        return usermapper.selectUserById(id);
    }
}
