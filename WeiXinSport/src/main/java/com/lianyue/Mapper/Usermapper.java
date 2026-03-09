package com.lianyue.Mapper;


import com.lianyue.pojo.RunRecord;
import com.lianyue.pojo.User;
import com.lianyue.pojo.UserLoginDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface Usermapper {
    //在数据库查找username和 password匹配的数据，如果找到则成功登录
    @Select("select * from sys_user where username=#{username} and password=#{password} and role=#{role}")
    User selectUser(UserLoginDTO userLoginDTO);
    //在数据库查找username是否被注册过
    @Select("select count(*) from sys_user where username=#{username}")
    Integer selectUsername(String username);
    //注册账号
    @Insert("INSERT INTO sys_user(username, password, name, role, college, class_name) VALUES(#{username}, #{password}, #{name}, #{role}, #{college}, #{classname})")
    Integer insertUser(User user);
    //修改密码
    @Insert("update sys_user set password=#{password} where id=#{id}")
    Integer updatePassword(User user);
    //查询用户信息
    @Select("select * from sys_user where id=#{id}")
    User selectUserById(Integer id);
    //查询用户列表
    @Select("<script>" +
            "select * from sys_user where id in " +
            "<foreach item='id' collection='list' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    List<User> selectListByIds(List<Integer> ids);

}
