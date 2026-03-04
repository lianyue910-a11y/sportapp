package com.lianyue.Mapper;

import com.lianyue.pojo.SemesterConfig;
import com.lianyue.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface Adminmapper {
    // 查所有学期列表
    @Select("SELECT * FROM sys_semester_config ORDER BY semester DESC")
    List<SemesterConfig> selectSemesterList();

    // 新增学期
    @Insert("INSERT INTO sys_semester_config(semester, target_distance, is_current) VALUES(#{semester}, #{targetDistance}, 0)")
    void insertSemester(SemesterConfig config);

    // 更新学期目标
    @Update("UPDATE sys_semester_config SET target_distance = #{targetDistance} WHERE id = #{id}")
    void updateSemesterTarget(Integer id, Integer targetDistance);

    // 重置所有学期为非当前状态
    @Update("UPDATE sys_semester_config SET is_current = 0")
    void resetAllCurrentStatus();

    // 设置指定学期为当前状态
    @Update("UPDATE sys_semester_config SET is_current = 1 WHERE id = #{id}")
    void setSemesterAsCurrent(Integer id);

    // 获取当前正在生效的学期
    @Select("SELECT * FROM sys_semester_config WHERE is_current = 1 LIMIT 1")
    SemesterConfig selectCurrentSemester();

    // --- 2. 教师分配相关 ---
    // 获取全校所有班级名 (从学生表中去重查询)
    @Select("SELECT DISTINCT class_name FROM sys_user WHERE role = 'student' AND class_name IS NOT NULL")
    List<String> selectAllClassNames();

    // 获取所有老师列表
    @Select("SELECT id, username, name, role, class_name as classname FROM sys_user WHERE role = 'teacher'")
    List<User> selectAllTeachers();

    // 更新老师所带的班级 (存为字符串 "班级A,班级B")
    @Update("UPDATE sys_user SET class_name = #{classes} WHERE id = #{teacherId}")
    void updateTeacherClasses(@Param("teacherId") Integer teacherId, @Param("classes") String classes);

    // 1. 查所有用户 (支持按名字搜索)
    @Select("SELECT id, username, name, role, class_name as classname FROM sys_user " +
            "WHERE (name LIKE CONCAT('%',#{keyword},'%') OR username LIKE CONCAT('%',#{keyword},'%')) " +
            "AND role != 'admin' " + // 不显示管理员
            "ORDER BY role, username")
    List<User> selectUserList(String keyword);

    // 2. 重置密码
    @Update("UPDATE sys_user SET password = #{password} WHERE id = #{id}")
    void updatePassword(@Param("id") Integer id, @Param("password") String password);

    // 3. 删除用户
    @Delete("DELETE FROM sys_user WHERE id = #{id}")
    void deleteUser(Integer id);

    // 4. 新增用户
    @Insert("INSERT INTO sys_user(username, password, name, role, class_name) " +
            "VALUES(#{username}, #{password}, #{name}, #{role}, #{classname})")
    void insertUser(User user);

    //跑步总里程
    @Select("SELECT user_id, SUM(distance) as total FROM run_record WHERE semester=#{semester} AND status=1 GROUP BY user_id")                          // 只显示前50名
    List<Map<String, Object>> selectAllTotalDistances(String semester);
}
