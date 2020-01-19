package com.meitu.ablum.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Missingdata {
    private int id;
    private  String username;
    private String version;
    private Date startTime;
    private Date endTime;

    public Missingdata(int id, String username, String version, Date startTime, Date endTime) {
        this.id = id;
        this.username = username;
        this.version = version;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Missingdata{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", version='" + version + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
