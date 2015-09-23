package com.zohaltech.app.sigma.entities;


public class AppsUsageLog {
    private Long id;
    private int appId;
    private Long trafficBytes;
    private String logDateTime;

    public AppsUsageLog(int appId, Long trafficBytes, String logDateTime) {
        setAppId(appId);
        setTrafficBytes(trafficBytes);
        setLogDateTime(logDateTime);
    }

    public AppsUsageLog(Long id, int appId, Long trafficBytes, String logDateTime) {
        this(appId, trafficBytes, logDateTime);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
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
