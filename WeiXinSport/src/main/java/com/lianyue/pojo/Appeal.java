package com.lianyue.pojo;

public class Appeal {
    private Integer id;         // 跑步记录ID
    private String studentName; // 学生姓名
    private String className;   // 班级
    private Double distance;    // 里程
    private String invalidReason; // 机器判定的作弊原因
    private String createTime;  // 跑步时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Appeal{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                ", className='" + className + '\'' +
                ", distance=" + distance +
                ", invalidReason='" + invalidReason + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
