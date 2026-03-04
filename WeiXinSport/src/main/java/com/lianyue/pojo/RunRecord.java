package com.lianyue.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class RunRecord {
    //主键
    private Long id;
    /**
     * 学生/用户ID (前端传参: userId)
     */
    private Long userId;

    /**
     * 跑步距离 (单位: 公里) (前端传参: distance)
     * 使用 BigDecimal 避免精度丢失
     */
    private BigDecimal distance;

    /**
     * 跑步时长 (单位: 秒) (前端传参: duration)
     */
    private Integer duration;

    /**
     * 步数 (前端传参: stepCount)
     * 防作弊核心字段：用于计算步幅
     */
    private Integer stepCount;

    /**
     * 跑步轨迹 (前端传参: pathLine)
     * 存 JSON 字符串，例如: [{"latitude":39.9,"longitude":116.4},...]
     * 这是一个大文本字段
     */
    private String pathLine;

    /**
     * 记录学期
     */
    private String semester;
    /**
     * 记录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 状态: 1有效, 0无效/作弊 (后台逻辑判断后写入)
     */
    private Integer status;

    /**
     * 无效原因 (后台逻辑判断后写入)
     * 如: "配速过快"、"步幅异常"
     */
    private String invalidReason;

    /**
     * 申诉状态: 0未申诉, 1申诉中, 2申诉通过, 3驳回 (后续流程用)
     */
    private Integer appealStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getStepCount() {
        return stepCount;
    }

    public void setStepCount(Integer stepCount) {
        this.stepCount = stepCount;
    }

    public String getPathLine() {
        return pathLine;
    }

    public void setPathLine(String pathLine) {
        this.pathLine = pathLine;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public Integer getAppealStatus() {
        return appealStatus;
    }

    public void setAppealStatus(Integer appealStatus) {
        this.appealStatus = appealStatus;
    }

    @Override
    public String toString() {
        return "RunRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", distance=" + distance +
                ", duration=" + duration +
                ", stepCount=" + stepCount +
                ", pathLine='" + pathLine + '\'' +
                ", semester='" + semester + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                ", invalidReason='" + invalidReason + '\'' +
                ", appealStatus=" + appealStatus +
                '}';
    }
}
