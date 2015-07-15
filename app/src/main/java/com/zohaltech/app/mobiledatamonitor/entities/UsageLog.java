package com.zohaltech.app.mobiledatamonitor.entities;

import java.util.Date;

/**
 * Created by Ali on 7/15/2015.
 */
public class UsageLog {
    private int id;
    private long trafficBytes;
    private Date logDateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTrafficBytes() {
        return trafficBytes;
    }

    public void setTrafficBytes(long trafficBytes) {
        this.trafficBytes = trafficBytes;
    }

    public Date getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(Date logDateTime) {
        this.logDateTime = logDateTime;
    }
}
