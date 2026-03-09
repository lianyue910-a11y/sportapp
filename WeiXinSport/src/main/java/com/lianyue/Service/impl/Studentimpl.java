package com.lianyue.Service.impl;

import com.lianyue.Mapper.Studentmapper;
import com.lianyue.Mapper.Usermapper;
import com.lianyue.Service.StudentService;
import com.lianyue.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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
    public RunRecord  insertRunRecord(RunRecord runRecord) {
        double distMeters = runRecord.getDistance().doubleValue() * 1000; // 公里转米
        int stepCount = runRecord.getStepCount();
        int duration = runRecord.getDuration();
        if (duration <= 0) {
            return "时间异常";
        }
        double speed = distMeters / duration; // 米每秒
        // 步幅校验
        double stride = distMeters / stepCount; // 平均步幅
        if (stride > 2.0) {
            // 步幅超过2米？正常人跑步步幅0.7-1.2米
            // 判定为：骑车 (因为有距离但没步数)
            runRecord.setStatus(0); // 标记为无效
            runRecord.setAppealStatus(0);// 标记为申诉中
            runRecord.setInvalidReason("步幅异常，疑似骑行");
        }
        // B. 规则判定
        if (distMeters < 5) {
            // 规则1：距离太短 (小于500米)
            runRecord.setStatus(0); // 标记为无效
            runRecord.setAppealStatus(0);// 标记为申诉中
            runRecord.setInvalidReason("距离不足500米");
        } else if (speed > 10) {
            // 规则2：速度太快 (超过10m/s，可能是骑车)
            runRecord.setStatus(0);
            runRecord.setAppealStatus(0);// 标记为申诉中
            runRecord.setInvalidReason("配速异常，疑似骑行");
        } else if (speed < 1) {
            // 规则3：速度太慢 (单纯走路或没动)
            runRecord.setStatus(0);
            runRecord.setAppealStatus(0);// 标记为申诉中
            runRecord.setInvalidReason("配速过慢，未达到跑步标准");
        } else {
            // C. 通过检查
            runRecord.setStatus(1); // 有效
            runRecord.setInvalidReason(null);
        }
        if (Studentmapper.insertRunRecord(runRecord) > 0) {
            String key = "rank:" + runRecord.getSemester();
            redisTemplate.opsForZSet().incrementScore(key, runRecord.getUserId().toString(), runRecord.getDistance().doubleValue());
            return runRecord;
        } else {
            throw new RuntimeException("系统出现问题，请联系管理员");
        }
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
        return  Studentmapper.selectDetail(id);
    }

    @Override
    public List<Rank> selectRank(String semester) {
        String key = "rank:" + semester;
        System.out.println("key"+ key);
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
