package com.lianyue.Mapper;

import com.lianyue.pojo.Message;
import com.lianyue.pojo.Rank;
import com.lianyue.pojo.RunRecord;
import org.apache.ibatis.annotations.*;


import java.util.List;
import java.util.Map;

@Mapper
public interface Studentmapper {
    //记录跑步数据到数据库
    @Insert("INSERT INTO run_record " +
            "(user_id, distance, duration, path_line, semester, step_count, status, invalid_reason, appeal_status) " +
            "VALUES " +
            "(#{userId}, #{distance}, #{duration}, #{pathLine}, #{semester}, #{stepCount}, #{status}, #{invalidReason}, #{appealStatus})")
    Integer insertRunRecord(RunRecord runRecord);
    //查询用户某次跑步数据
    @Select("SELECT * FROM run_record WHERE id = #{id}")
    RunRecord selectDetail(Integer id);
    //查询用户某学期的跑步总数据
    @Select("SELECT " +
            "IFNULL(SUM(distance), 0) as totalDistance, " + // 总公里数
            "IFNULL(SUM(duration), 0) as totalDuration, " + // 总时长(秒)
            "COUNT(*) as runCount " +                       // 有效次数
            "FROM run_record " +
            "WHERE user_id = #{id} AND semester = #{semester} AND status = 1")
    Map<String, Object> selectRunSummary(@Param("id") Integer id, @Param("semester") String semester);
    //查询用户某学期的跑步记录
    @Select("SELECT " +
            "id, user_id, distance, duration, step_count, " + // 基础数据
            "create_time, status, invalid_reason, appeal_status " + // 状态数据
            // 没有查 path_line ,可以在用户点击进详情页的时候再查
            "FROM run_record " +
            "WHERE user_id = #{id} " +
            "AND semester = #{semester} " +
            "ORDER BY create_time DESC") // 按时间倒序，最近的在上面
    List<RunRecord> selectRunList(@Param("id") Integer id, @Param("semester") String semester);
    //学生对跑步数据进行申诉
    @Update("UPDATE run_record SET appeal_status = #{status} WHERE id = #{id}")
    boolean updateAppealStatus(@Param("id") Integer id, @Param("status") Integer status);
    //查询未读消息
    @Select("SELECT * FROM sys_message WHERE receiver_id = #{userId} AND is_read = 0 ORDER BY create_time DESC")
    List<Message> selectUnreadMessages(Integer userId);
    //标记消息已读
    @Update("UPDATE sys_message SET is_read = 1 WHERE id = #{id}")
    void updateMessageReadStatus(Integer id);
}
