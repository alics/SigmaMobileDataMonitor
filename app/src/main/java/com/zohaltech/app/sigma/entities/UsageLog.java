package com.zohaltech.app.sigma.entities;

public class UsageLog
{

    private Integer id;
    private Long trafficBytes;
    private Long trafficBytesWifi;
    private String logDateTime;

    public UsageLog(Long trafficBytes, Long trafficBytesWifi)
    {
        setTrafficBytes(trafficBytes);
        setTrafficBytesWifi(trafficBytesWifi);
    }

    public UsageLog(Integer id, Long trafficBytes, Long trafficBytesWifi, String logDateTime)
    {
        this(trafficBytes, trafficBytesWifi);
        setId(id);
        setLogDateTime(logDateTime);
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Long getTrafficBytes()
    {
        return trafficBytes;
    }

    public void setTrafficBytes(Long trafficBytes)
    {
        this.trafficBytes = trafficBytes;
    }

    public Long getTrafficBytesWifi()
    {
        return trafficBytesWifi;
    }

    public void setTrafficBytesWifi(Long trafficBytesWifi)
    {
        this.trafficBytesWifi = trafficBytesWifi;
    }

    public String getLogDateTime()
    {
        return logDateTime;
    }

    public void setLogDateTime(String logDateTime)
    {
        this.logDateTime = logDateTime;
    }

}
