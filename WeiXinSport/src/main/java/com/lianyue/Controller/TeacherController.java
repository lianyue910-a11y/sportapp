package com.lianyue.Controller;

import com.lianyue.Service.TeacherService;
import com.lianyue.pojo.Appeal;
import com.lianyue.pojo.Rank;
import com.lianyue.pojo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @PostMapping("/selectList")
    public Result selectList() {
        List<Appeal> list = teacherService.selectAppealList();
        return Result.success(list);
    }
    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestBody Map<String, Object> map) {
        Object idObj = map.get("id");
        Integer id = Integer.parseInt(idObj.toString());
        Boolean pass = (Boolean) map.get("pass");
        if (teacherService.updateAuditStatus(id, pass)){
            return Result.success(pass ? "已通过" : "已驳回");
        }else {
            return Result.error("操作失败");
        }
    }
    @PostMapping("/interact")
    public Result interact(@RequestBody Map<String, Object> map) {
        Integer teacherId = (Integer) map.get("teacherId");
        Integer studentId = (Integer) map.get("studentId");
        String type = (String) map.get("type");
        try {
            teacherService.sendMessage(teacherId, studentId, type);
            return Result.success("发送成功");
        } catch (RuntimeException e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/student/list")
    public Result getStudentList(@RequestParam Integer teacherId, @RequestParam String semester) {
        List<Rank> list = teacherService.getMyStudents(teacherId, semester);
        return Result.success(list);
    }
}
