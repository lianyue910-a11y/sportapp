package com.lianyue.Service;

import com.lianyue.pojo.Appeal;
import com.lianyue.pojo.Rank;

import java.util.List;

public interface TeacherService {
    // 1. 查待审核列表 (appeal_status = 1 表示申诉中)
    List<Appeal> selectAppealList();
    // 2.更新审核状态
    boolean updateAuditStatus(Integer id, Boolean pass);
    // 3. 发送消息
    public void sendMessage(Integer teacherId, Integer studentId, String type);
    // 4. 获取老师所带学生
    public List<Rank> getMyStudents(Integer teacherId, String semester);
}
