package com.lianyue.pojo;

public class SemesterConfig {
    private Integer id;
    /**
     * 学期名称
     * 例如: "2025-2026-2"
     */
    private String semester;
    /**
     * 目标里程 (公里)
     * 例如: 100
     */
    private Integer targetDistance;
    /**
     * 是否为当前学期
     * 1: 是, 0: 否
     */
    private Integer isCurrent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getTargetDistance() {
        return targetDistance;
    }

    public void setTargetDistance(Integer targetDistance) {
        this.targetDistance = targetDistance;
    }

    public Integer getIsCurrent() {
        return isCurrent;
    }

    public void setIsCurrent(Integer isCurrent) {
        this.isCurrent = isCurrent;
    }

    @Override
    public String toString() {
        return "SemesterConfig{" +
                "id=" + id +
                ", semester='" + semester + '\'' +
                ", targetDistance=" + targetDistance +
                ", isCurrent=" + isCurrent +
                '}';
    }
}
