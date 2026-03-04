package com.lianyue.Controller;

import com.lianyue.Service.StudentService;
import com.lianyue.Service.UserService;
import com.lianyue.pojo.*;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserService userService;

    @PostMapping("/run")
    public Result run(@RequestBody RunRecord runRecord) {
        String msg = studentService.insertRunRecord(runRecord);
        return Result.success(msg);

    }

    @PostMapping("/history")
    public Result getHistory(@RequestBody Map<String, Object> params) {
        // 调用 Service 获取组装好的数据
        Integer userId = (Integer) params.get("userId");
        String semester = (String) params.get("semester");
        Map<String, Object> data = studentService.getHistoryData(userId, semester);
        if (data != null){
            return Result.success(data);
        }else {
            return Result.error("没有数据");
        }
    }

    @PostMapping("/detail")
    public Result getDetail(@RequestBody Map<String, Integer> map) {
        Integer id = map.get("id");
        RunRecord runRecord = studentService.selectDetail(id);
        if (runRecord != null) {
            return Result.success(runRecord);
        } else {
            return Result.error("没有数据");
        }
    }

    @PostMapping("/selectUser")
    public Result selectUser(@RequestBody User user ) {
        Integer id = user.getId();
        User user1 = userService.selectUserById(id);
        if (user1 != null) {
            user1.setPassword(null);
            return Result.success(user1);
        } else {
            //抛出异常，只要登陆了就一定存在
            return Result.error("用户不存在");
        }
    }

    @PostMapping("/rank")
    public Result getRank(@RequestBody Map<String, String> paramMap) {
        String semester = paramMap.get("semester");
        List<Rank> list = studentService.selectRank(semester);
        return Result.success(list);
    }

    @PostMapping("/appeal")
    public Result appeal(@RequestBody Map<String, Integer> map) {
        Integer recordId = map.get("id");
        boolean success = studentService.updateAppealStatus(recordId, 1);
        if (success) {
            return Result.success("申诉提交成功");
        } else {
            return Result.error("提交失败");
        }
    }

    // 1. 获取未读消息
    @GetMapping("/message/unread")
    public Result getUnreadMsg(@RequestParam Integer userId) {
        return Result.success(studentService.getUnreadMessages(userId));
    }

    // 2. 标记已读 (点击弹窗确认后调用)
    @PostMapping("/message/read")
    public Result markRead(@RequestBody Map<String, Integer> map) {
        Integer id = map.get("id");
        studentService.markMessageRead(id);
        return Result.success("已读");
    }
}
