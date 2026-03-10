package com.lianyue.Service.impl;

import com.alibaba.fastjson.JSON;
import com.lianyue.Mapper.Adminmapper;
import com.lianyue.Service.AdminService;
import com.lianyue.pojo.SemesterConfig;
import com.lianyue.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class Adminimpl implements AdminService {
    @Autowired
    private Adminmapper adminMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public List<SemesterConfig> getSemesterList() {
        return adminMapper.selectSemesterList();
    }

    @Override
    public void saveSemester(SemesterConfig config) {
        if (config.getId() == null) {
            // 新增
            adminMapper.insertSemester(config);
        } else {
            // 修改目标
            adminMapper.updateSemesterTarget(config.getId(), config.getTargetDistance());
        }
    }

    @Transactional // 事务：保证两步操作原子性
    @Override
    public void switchCurrentSemester(Integer id) {
        // 1. 先把所有学期置为 0
        adminMapper.resetAllCurrentStatus();
        // 2. 把指定学期置为 1
        adminMapper.setSemesterAsCurrent(id);
        // 3. 删除 Redis 缓存
        redisTemplate.delete("sys:semester");
    }

    @Override
    public SemesterConfig getCurrentSemester() {
        String key = "sys:semester";
        // 1. 先查 Redis
        String json = (String) redisTemplate.opsForValue().get(key);
        if (json != null) {
            // 缓存命中：直接转成对象返回
            return JSON.parseObject(json, SemesterConfig.class);
        }
        // 2. 缓存未命中：查数据库
        SemesterConfig config = adminMapper.selectCurrentSemester();
        // 3. 回填 Redis (设置 1 小时过期，防止死数据)
        if (config != null) {
            String newJson = JSON.toJSONString(config);
            redisTemplate.opsForValue().set(key, newJson, 1, TimeUnit.HOURS);
        }

        return config;
    }

    @Override
    public Map<String, Object> getBindInitData() {
        Map<String, Object> data = new HashMap<>();
        data.put("classes", adminMapper.selectAllClassNames());
        data.put("teachers", adminMapper.selectAllTeachers());
        return data;
    }

    @Override
    public void bindClassesToTeacher(Integer teacherId, List<String> classList) {
        // 把 List ["班级A", "班级B"] 转成 String "班级A,班级B"
        String classStr = String.join(",", classList);
        adminMapper.updateTeacherClasses(teacherId, classStr);
    }

    @Override
    public List<User> getUserList(String keyword) {
        // 如果 keyword 为 null，转为空字符串防止 SQL 报错
        return adminMapper.selectUserList(keyword == null ? "" : keyword);
    }

    @Override
    public void resetUserPassword(Integer userId, String newPassword) {
        adminMapper.updatePassword(userId, newPassword);
    }

    @Override
    public void deleteUser(Integer userId) {
        adminMapper.deleteUser(userId);
    }

    @Override
    public void addUser(User user) {
        // 默认逻辑处理：如果没填密码，给默认值
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            user.setPassword("123456");
        }
        adminMapper.insertUser(user);
    }

    @Override
    public void syncDataToRedis(String semester) {
        String key = "rank:" + semester;
        // 1. 先清空旧的 (防止重复累加)
        redisTemplate.delete(key);
        // 2. 从 MySQL 查出这个学期所有人的总里程
        List<Map<String, Object>> list = adminMapper.selectAllTotalDistances(semester);
        // 3. 塞入 Redis
        for (Map<String, Object> map : list) {
            Long userId = (Long) map.get("user_id");
            Object totalObj = map.get("total");
            Double total = (totalObj instanceof BigDecimal) ? ((BigDecimal) totalObj).doubleValue() : (Double) totalObj;
            redisTemplate.opsForZSet().add(key, userId.toString(), total);
        }
        System.out.println("数据预热完成！同步了 " + list.size() + " 人");
    }
}
