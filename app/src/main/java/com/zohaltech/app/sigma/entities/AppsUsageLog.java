package com.zohaltech.app.sigma.entities;


public class AppsUsageLog {
    private Long id;
    private Integer appId;
    private Long trafficBytes;
    private String logDateTime;

    public AppsUsageLog(Integer appId, Long trafficBytes, String logDateTime) {
        setAppId(appId);
        setTrafficBytes(trafficBytes);
        setLogDateTime(logDateTime);
    }

    public AppsUsageLog(Long id, Integer appId, Long trafficBytes, String logDateTime) {
        this(appId, trafficBytes, logDateTime);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
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
