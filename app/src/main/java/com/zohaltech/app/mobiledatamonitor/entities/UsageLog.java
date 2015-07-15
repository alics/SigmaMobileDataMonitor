package com.zohaltech.app.mobiledatamonitor.entities;

import java.util.Date;

/**
 * Created by Ali on 7/15/2015.
 */
public class UsageLog {
    private Integer id;
    private Long trafficBytes;
    private Date logDateTime;

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
