package com.lianyue.Mapper;

import com.lianyue.pojo.Notice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface Noticemapper {
    // 查列表 (置顶在前，时间在后)
    @Select("SELECT * FROM sys_notice ORDER BY is_top DESC, create_time DESC")
    List<Notice> selectList();
    // 发布
    @Insert("INSERT INTO sys_notice(title, content, publisher, is_top) VALUES(#{title}, #{content}, #{publisher}, #{isTop})")
    Integer insert(Notice notice);
    // 删除
    @Delete("DELETE FROM sys_notice WHERE id = #{id}")
    Integer delete(Integer id);
}
