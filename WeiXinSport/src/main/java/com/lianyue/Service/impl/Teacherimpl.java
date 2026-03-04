package com.lianyue.Service.impl;

import com.lianyue.Mapper.Teachermapper;
import com.lianyue.Mapper.Usermapper;
import com.lianyue.Service.TeacherService;
import com.lianyue.pojo.Appeal;
import com.lianyue.pojo.Rank;
import com.lianyue.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class Teacherimpl implements TeacherService {
    @Autowired
    private Teachermapper teachermapper;
    @Autowired
    private Usermapper usermapper;
    public List<Appeal> selectAppealList() {
        return teachermapper.selectAppealList();
    }

    @Override
    public boolean updateAuditStatus(Integer id, Boolean pass) {
        if (pass) {
            if (teachermapper.updateAuditStatus(id, 1, 2) > 0){
                // 通过：恢复成绩 (status=1)，标记申诉通过 (appeal=2)
                return true;
            }else return false;
        } else {
            if (teachermapper.updateAuditStatus(id, 0, 3) > 0){
                // 驳回：维持无效 (status=0)，标记申诉驳回 (appeal=3)
                return true;
            }else return false;
        }
    }

    @Override
    public void sendMessage(Integer teacherId, Integer studentId, String type) {
        // 1. 防止重复：如果今天已经发过了，就不发了
        int count = teachermapper.checkTodayMessageCount(teacherId, studentId, type);
        if (count > 0) {
            throw new RuntimeException("今天已经提醒过他啦，明天再来吧！");
        }
        // 2. 构造内容
        String content = "";
        if ("remind".equals(type)) {
            content = "你的跑步里程尚未达标，老师喊你起来跑步啦！🏃‍♂️";
        } else if ("like".equals(type)) {
            content = "真棒！你的跑步成绩已达标，老师为你点赞！👍";
        }

        // 3. 入库
        teachermapper.insertMessage(teacherId, studentId, type, content);
    }

    @Override
    public List<Rank> getMyStudents(Integer teacherId, String semester) {
        // 1. 先查老师自己，看他管哪些班
        User teacher = usermapper.selectUserById(teacherId);

        if (teacher == null || teacher.getClassname() == null || teacher.getClassname().isEmpty()) {
            return new ArrayList<>(); // 没分配班级，返回空列表
        }

        // 2. 把 "计科1班,计科2班" 转成 ["计科1班", "计科2班"]
        String[] classArray = teacher.getClassname().split(",");
        // 去除空格
        List<String> classList = Arrays.stream(classArray)
                .map(String::trim)
                .collect(Collectors.toList());

        // 3. 调用 Mapper 查学生
        return teachermapper.selectMyStudents(classList, semester);
    }
}
