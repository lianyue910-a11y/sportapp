package com.lianyue.Service;

import com.lianyue.pojo.SemesterConfig;
import com.lianyue.pojo.User;

import java.util.List;
import java.util.Map;

public interface AdminService {
    // 学期管理
    List<SemesterConfig> getSemesterList();
    void saveSemester(SemesterConfig config); // 新增或更新
    void switchCurrentSemester(Integer id);
    SemesterConfig getCurrentSemester();

    // 教师分配
    Map<String, Object> getBindInitData(); // 获取分配页面所需的数据
    void bindClassesToTeacher(Integer teacherId, List<String> classList);

    List<User> getUserList(String keyword);
    void resetUserPassword(Integer userId, String newPassword);
    void deleteUser(Integer userId);
    void addUser(User user);
    void syncDataToRedis(String semester);
}
