package com.lianyue.Service.impl;

import com.alibaba.fastjson.JSON;
import com.lianyue.Mapper.Adminmapper;
import com.lianyue.Service.AdminService;
import com.lianyue.Util.Md5;
import com.lianyue.pojo.SemesterConfig;
import com.lianyue.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
        String json = redisTemplate.opsForValue().get(key);
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
        // 加密密码后再存储
        String encryptedPassword = Md5.encrypt(newPassword);
        adminMapper.updatePassword(userId, encryptedPassword);
    }

    @Override
    public void deleteUser(Integer userId) {
        adminMapper.deleteUser(userId);
    }

    @Override
    public void addUser(User user) {
        // 处理密码：如果没填密码，生成随机密码
        String rawPassword = user.getPassword();
        if (rawPassword == null || rawPassword.isEmpty()) {
            // 生成8位随机密码（字母+数字）
            rawPassword = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
            user.setPassword(rawPassword);
        }

        // 加密密码后再存储
        String encryptedPassword = Md5.encrypt(rawPassword);
        user.setPassword(encryptedPassword);

        adminMapper.insertUser(user);

        // 存储原始密码用于返回给管理员（实际项目中应该通过安全渠道发送）
        user.setPassword(rawPassword); // 将原始密码设置回对象，供Controller返回
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
