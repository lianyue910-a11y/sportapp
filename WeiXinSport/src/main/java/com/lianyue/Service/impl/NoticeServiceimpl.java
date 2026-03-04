package com.lianyue.Service.impl;

import com.lianyue.Mapper.Noticemapper;
import com.lianyue.Service.NoticeService;
import com.lianyue.pojo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceimpl implements NoticeService {
    @Autowired
    private Noticemapper noticeMapper;

    @Override
    public List<Notice> getList() {
        return noticeMapper.selectList();
    }

    @Override
    public Integer addNotice(Notice notice) {
        if(notice.getPublisher() == null) notice.setPublisher("管理员");
        return noticeMapper.insert(notice);
    }

    @Override
    public Integer deleteNotice(Integer id) {
        return noticeMapper.delete(id);
    }
}
