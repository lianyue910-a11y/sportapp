package com.lianyue.Mapper;

import com.lianyue.pojo.Appeal;
import com.lianyue.pojo.Rank;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface Teachermapper {
    // 1. 查待审核列表 (appeal_status = 1 表示申诉中)
    @Select("SELECT r.id, u.name as studentName, u.class_name as className, " +
            "r.distance, r.invalid_reason as invalidReason, r.create_time as createTime " +
            "FROM run_record r " +
            "LEFT JOIN sys_user u ON r.user_id = u.id " +
            "WHERE r.appeal_status = 1 " +
            "ORDER BY r.create_time DESC")
    List<Appeal> selectAppealList();
    // 2. 更新审核状态，如果通过：status=1(有效), appeal_status=2(通过)
    @Update("UPDATE run_record SET status = #{status}, appeal_status = #{appealStatus} WHERE id = #{id}")
    Integer updateAuditStatus(@Param("id") Integer id, @Param("status") Integer status, @Param("appealStatus") Integer appealStatus);
    // 插入消息
    @Insert("INSERT INTO sys_message(sender_id, receiver_id, type, content) " +
            "VALUES(#{senderId}, #{receiverId}, #{type}, #{content})")
     void insertMessage(@Param("senderId") Integer senderId, @Param("receiverId") Integer receiverId, @Param("type") String type, @Param("content") String content);
    // 检查今天是否已经督促过 (防止老师重复点击)
    @Select("SELECT count(*) FROM sys_message WHERE sender_id = #{tid} AND receiver_id = #{sid} " +
            "AND type = #{type} AND DATE(create_time) = CURDATE()")
     int checkTodayMessageCount(@Param("tid") Integer tid, @Param("sid") Integer sid, @Param("type") String type);

    @Select({
            "<script>",
            "SELECT u.id as userId, u.name, u.class_name as className ,IFNULL(SUM(r.distance), 0) as totalDistance",
            "FROM sys_user u ",
            "LEFT JOIN run_record r ON u.id = r.user_id AND r.semester = #{semester} AND r.status = 1 ",
            "WHERE u.role = 'student' ",
            "AND u.class_name IN ",
            "<foreach item='cls' collection='classList' open='(' separator=',' close=')'>",
            "#{cls}",
            "</foreach>",
            "GROUP BY u.id",
            "ORDER BY totalDistance ASC",
            "</script>"
    })
    List<Rank> selectMyStudents(@Param("classList") List<String> classList, @Param("semester") String semester);
}
