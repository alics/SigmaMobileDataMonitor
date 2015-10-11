package com.zohaltech.app.sigma.entities;

public class UsageLog
{

    private Integer id;
    private Long trafficBytes;
    private Long wifiTrafficBytes;
    private String logDateTime;

    public UsageLog(Long trafficBytes, Long wifiTrafficBytes)
    {
        setTrafficBytes(trafficBytes);
        setWifiTrafficBytes(wifiTrafficBytes);
    }

    public UsageLog(Integer id, Long trafficBytes, Long wifiTrafficBytes, String logDateTime)
    {
        this(trafficBytes, wifiTrafficBytes);
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

    public Long getWifiTrafficBytes()
    {
        return wifiTrafficBytes;
    }

    public void setWifiTrafficBytes(Long wifiTrafficBytes)
    {
        this.wifiTrafficBytes = wifiTrafficBytes;
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
