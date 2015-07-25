package com.zohaltech.app.mobiledatamonitor.entities;

import java.util.Date;


public class UsageLog {
    private Integer id;
    private Long trafficBytes;
    private Date logDateTime;

    public UsageLog(Long trafficBytes) {
        setTrafficBytes(trafficBytes);
    }

    public UsageLog(Integer id, Long trafficBytes, Date logDateTime) {
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

    public Date getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(Date logDateTime) {
        this.logDateTime = logDateTime;
    }
}
