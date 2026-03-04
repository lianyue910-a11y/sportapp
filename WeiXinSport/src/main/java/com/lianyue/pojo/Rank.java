package com.lianyue.pojo;

public class Rank {
    private Integer userId;
    private String name;      // 姓名
    private String avatar;    // 头像
    private String className; // 班级
    private Double totalDistance; // 总里程
    private Integer rank;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Rank{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", className='" + className + '\'' +
                ", totalDistance=" + totalDistance +
                ", rank=" + rank +
                '}';
    }
}
