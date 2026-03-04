package com.lianyue.pojo;

import java.util.Date;

public class Message {
    private Integer id;
    // 发送者ID (老师ID)
    private Integer senderId;
    // 接收者ID (学生ID)
    private Integer receiverId;
    // 类型
    private String type;
    // 内容
    private String content;
    // 是否已读: 0未读, 1已读
    private Integer isRead;
    // 创建时间
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", isRead=" + isRead +
                ", createTime=" + createTime +
                '}';
    }
}
