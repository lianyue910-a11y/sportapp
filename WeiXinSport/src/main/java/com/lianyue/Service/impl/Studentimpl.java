package com.lianyue.Service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lianyue.Mapper.Studentmapper;
import com.lianyue.Mapper.Usermapper;
import com.lianyue.Service.StudentService;
import com.lianyue.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class Studentimpl implements StudentService {
    @Autowired
    private Studentmapper Studentmapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private Usermapper usermapper;

    @Override
    @Transactional
    public String insertRunRecord(RunRecord runRecord) {
        // 1. 基础校验 (报错直接抛出，由全局异常拦截器变成 Result.error 的 msg返回)
        int duration = runRecord.getDuration();
        if (duration <= 0) {
            throw new RuntimeException("运动时间异常，数据无效");
        }
        double distMeters = runRecord.getDistance().doubleValue() * 1000;
        int stepCount = runRecord.getStepCount() == null ? 0 : runRecord.getStepCount();
        double speed = distMeters / duration;
        // 默认先假设这条记录是正常的
        runRecord.setStatus(1);
        runRecord.setInvalidReason(null);
        runRecord.setAppealStatus(null);
        // 2. 核心规则校验
        if (distMeters < 500) {
            markAsInvalid(runRecord, "距离不足500米");
        } else if (speed > 10) {
            markAsInvalid(runRecord, "配速异常，疑似骑行");
        } else if (speed < 1) {
            markAsInvalid(runRecord, "配速过慢，未达到跑步标准");
        } else if (stepCount == 0 || (distMeters / stepCount) > 2.0) {
            markAsInvalid(runRecord, "步幅异常，疑似代跑或骑行");
        }
        // 3. 插入数据库
        if (Studentmapper.insertRunRecord(runRecord) > 0) {
            // 4. 判断存入的是有效记录还是无效记录
            if (runRecord.getStatus() == 1) {
                // 有效：加进 Redis 排行榜，返回成功提示
                String key = "rank:" + runRecord.getSemester();
                redisTemplate.opsForZSet().incrementScore(
                        key,
                        runRecord.getUserId().toString(),
                        runRecord.getDistance().doubleValue()
                );
                return "提交成功，跑步记录已生效！";
            } else {
                // 无效：不加分，但告诉用户原因
                return "记录已保存，但判定无效：" + runRecord.getInvalidReason(); //返回提示语
            }
        } else {
            throw new RuntimeException("系统存储失败，请联系管理员");
        }
    }

    // 辅助方法
    private void markAsInvalid(RunRecord record, String reason) {
        record.setStatus(0);
        record.setAppealStatus(0);
        record.setInvalidReason(reason);
    }

    @Override
    public Map<String, Object> getHistoryData(Integer userId, String semester) {
        // 1. 查统计概览 (总公里数等)
        Map<String, Object> summary = Studentmapper.selectRunSummary(userId, semester);
        // 2. 查记录列表
        List<RunRecord> list = Studentmapper.selectRunList(userId, semester);
        // 3. 组装结果
        Map<String, Object> result = new HashMap<>();
        result.put("summary", summary); // 概览数据
        result.put("list", list);       // 列表数据
        return result;
    }

    @Override
    public RunRecord selectDetail(Integer id) {
        return Studentmapper.selectDetail(id);
    }

    @Override
    public List<Rank> selectRank(String semester) {
        String key = "rank:" + semester;
        System.out.println("key" + key);
        // 1. 从 Redis 取前 50 名 (按分数从高到低)
        // Tuple 包含: value(学生ID) 和 score(总里程)
        Set<ZSetOperations.TypedTuple<String>> topStudents =
                redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, 49);
        if (topStudents == null || topStudents.isEmpty()) {
            System.out.println("redis没有数据");
            return new ArrayList<>(); // 没人跑
        }
        // 2. 提取出所有学生 ID
        List<Integer> userIds = new ArrayList<>();
        // 用一个 Map 暂存 ID -> 里程 的对应关系，方便后面组装
        Map<Integer, Double> distanceMap = new HashMap<>();
        for (ZSetOperations.TypedTuple<String> tuple : topStudents) {
            Integer uid = Integer.parseInt(tuple.getValue());
            userIds.add(uid);
            distanceMap.put(uid, tuple.getScore());
        }
        // 3. 去 MySQL 查这些人的名字
        List<User> userList = usermapper.selectListByIds(userIds);
        // 4. 组装最终结果 (Rank)
        List<Rank> result = new ArrayList<>();
        int rankNum = 1;
        // 这里要按 Redis 返回的顺序来组装，因为 userList 可能是乱序的
        for (Integer uid : userIds) {
            // 在 userList 里找到这个人
            User u = userList.stream().filter(user -> user.getId().equals(uid)).findFirst().orElse(null);
            if (u != null) {
                Rank vo = new Rank();
                vo.setUserId(u.getId());
                vo.setName(u.getName());
                vo.setClassName(u.getClassname());
                vo.setTotalDistance(distanceMap.get(uid)); // 从 Redis 拿出来的里程
                vo.setRank(rankNum++);
                result.add(vo);
            }
        }
        return result;
    }
    @Override
    public boolean updateAppealStatus(Integer id, Integer status) {
        return Studentmapper.updateAppealStatus(id, status);
    }


    @Override
    public List<Message> getUnreadMessages(Integer userId) {
        return Studentmapper.selectUnreadMessages(userId);
    }

    @Override
    public void markMessageRead(Integer messageId) {
        Studentmapper.updateMessageReadStatus(messageId);
    }

}
