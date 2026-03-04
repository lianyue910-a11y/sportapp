package com.lianyue.Service;

import com.lianyue.pojo.Message;
import com.lianyue.pojo.Rank;
import com.lianyue.pojo.RunRecord;


import java.util.List;
import java.util.Map;

public interface StudentService {
    //记录跑步数据
    String insertRunRecord(RunRecord runRecord);

    // 获取历史页面所有数据 (统计 + 列表)
    Map<String, Object> getHistoryData(Integer userId, String semester);
    //查询单次跑步详情
    RunRecord selectDetail(Integer id);
    //列出跑步里程前五十名
    List<Rank> selectRank(String semester);
    //学生对跑步数据申诉
    boolean updateAppealStatus(Integer id, Integer status);
    //获取未读消息
    List<Message> getUnreadMessages(Integer userId);
    //标记已读
    void markMessageRead(Integer messageId);
    //redis和MySQL之间数据更新

}
