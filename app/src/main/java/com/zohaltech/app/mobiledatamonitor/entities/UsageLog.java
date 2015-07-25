package com.zohaltech.app.mobiledatamonitor.entities;

public class UsageLog {

    private Integer id;
    private Long trafficBytes;
    private String logDateTime;

    public UsageLog(Long trafficBytes) {
        setTrafficBytes(trafficBytes);
    }

    public UsageLog(Integer id, Long trafficBytes, String logDateTime) {
        this(trafficBytes);
        setId(id);
        setLogDateTime(logDateTime);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTrafficBytes() {
        return trafficBytes;
    }

    public void setTrafficBytes(Long trafficBytes) {
        this.trafficBytes = trafficBytes;
    }

    public String getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(String logDateTime) {
        this.logDateTime = logDateTime;
    }
}
