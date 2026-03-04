package com.lianyue.Service;

import com.lianyue.pojo.Notice;

import java.util.List;

public interface NoticeService {
    // 查列表 (置顶在前，时间在后)
    public List<Notice> getList();
    //发布公告
    public Integer addNotice(Notice notice);
    //删除公告
    public Integer deleteNotice(Integer id);
}
