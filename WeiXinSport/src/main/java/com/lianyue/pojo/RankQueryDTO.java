package com.lianyue.pojo;

public class RankQueryDTO {

    private String semester;
    // 是否强制从 MySQL 同步到 Redis
    private Boolean forceSync;

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Boolean getForceSync() {
        return forceSync;
    }

    public void setForceSync(Boolean forceSync) {
        this.forceSync = forceSync;
    }
    @Override
    public String toString() {
        return "RankQueryDTO{" +
                "semester='" + semester + '\'' +
                ", forceSync=" + forceSync +
                '}';
    }
}
