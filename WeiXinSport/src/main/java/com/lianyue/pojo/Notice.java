package com.lianyue.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Notice {
    private Integer id;
    private String title;
    private String content;
    private String publisher;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;
    private Integer isTop;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", publisher='" + publisher + '\'' +
                ", createTime=" + createTime +
                ", isTop=" + isTop +
                '}';
    }
}