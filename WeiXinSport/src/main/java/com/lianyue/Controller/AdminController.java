package com.lianyue.Controller;

import com.lianyue.Service.AdminService;
import com.lianyue.pojo.Result;
import com.lianyue.pojo.SemesterConfig;
import com.lianyue.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    // --- 1. 学期管理 ---
    @GetMapping("/semester/list")
    public Result list() {
        return Result.success(adminService.getSemesterList());
    }

    @PostMapping("/semester/save")
    public Result save(@RequestBody SemesterConfig config) {
        adminService.saveSemester(config);
        return Result.success("保存成功");
    }

    @PostMapping("/semester/setCurrent")
    public Result setCurrent(@RequestBody Map<String, Integer> map) {
        Integer id = map.get("id");
        adminService.switchCurrentSemester(id);
        return Result.success("切换成功");
    }

    // 公共接口：供学生端/老师端获取当前学期配置
    @GetMapping("/semester/current")
    public Result getCurrent() {
        return Result.success(adminService.getCurrentSemester());
    }

    // 2. 教师分配
    @GetMapping("/bind/init")
    public Result getInitData() {
        return Result.success(adminService.getBindInitData());
    }

    @PostMapping("/bind/save")
    public Result bindClasses(@RequestBody Map<String, Object> map) {
        Integer teacherId = (Integer) map.get("teacherId");
        List<String> classList = (List<String>) map.get("classes");

        adminService.bindClassesToTeacher(teacherId, classList);
        return Result.success("分配成功");
    }

    //获取用户列表
    @PostMapping("/list")
    public Result list(@RequestBody Map<String, String> requestBody) {
        String keyword = requestBody.getOrDefault("keyword", "");
        return Result.success(adminService.getUserList(keyword));
    }


    // 重置密码
    @PostMapping("/resetPwd")
    public Result resetPwd(@RequestBody Map<String, Integer> map) {
        Integer id = map.get("id");

        // 生成8位随机密码（字母+数字）
        String randomPassword = java.util.UUID.randomUUID().toString()
                .replaceAll("-", "").substring(0, 8);

        adminService.resetUserPassword(id, randomPassword);

        // 返回生成的随机密码（实际项目中应该通过安全渠道发送给用户）
        Map<String, String> result = new HashMap<>();
        result.put("message", "密码已重置");
        result.put("newPassword", randomPassword);
        result.put("note", "请告知用户及时修改密码，此密码仅显示一次");

        return Result.success(result);
    }

    // 删除用户
    @PostMapping("/delete")
    public Result delete(@RequestBody Map<String, Integer> map) {
        Integer id = map.get("id");
        adminService.deleteUser(id);
        return Result.success("删除成功");
    }

    // 新增用户
    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        // 保存用户，addUser方法会处理密码加密
        adminService.addUser(user);

        // 获取生成的随机密码（如果用户未提供密码）
        // addUser方法会将原始密码设置回user对象
        String generatedPassword = user.getPassword();

        // 清除密码字段，不返回给前端
        user.setPassword(null);

        Map<String, Object> result = new HashMap<>();
        result.put("message", "用户新增成功");
        result.put("user", user);

        // 如果生成了随机密码，返回给管理员（实际应通过安全渠道发送）
        if (generatedPassword != null && !generatedPassword.isEmpty()) {
            result.put("generatedPassword", generatedPassword);
            result.put("note", "请告知用户及时修改密码，此密码仅显示一次");
        }

        return Result.success(result);
    }

    // 同步缓存和数据库里面的数据，防止排行榜数据异常
    @GetMapping("/syncRedis")
    public Result syncRedis(@RequestParam String semester) {
        adminService.syncDataToRedis(semester);
        return Result.success("同步完成");
    }
}
