package com.zohaltech.app.sigma.entities;


public class DailyTrafficHistory
{

    private Integer id;
    private Long traffic;
    private Long trafficWifi;
    private String logDate;

    public DailyTrafficHistory(Long traffic,Long trafficWifi, String logDate)
    {
        setTraffic(traffic);
        setTrafficWifi(trafficWifi);
        setLogDate(logDate);
    }

    public DailyTrafficHistory(Integer id, Long traffic,Long trafficWifi, String logDate)
    {
        this(traffic,trafficWifi, logDate);
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Long getTraffic()
    {
        return traffic;
    }

    public void setTraffic(Long traffic)
    {
        this.traffic = traffic;
    }

    public Long getTrafficWifi()
    {
        return trafficWifi;
    }

    public void setTrafficWifi(Long trafficWifi)
    {
        this.trafficWifi = trafficWifi;
    }

    public String getLogDate()
    {
        return logDate;
    }

    public void setLogDate(String logDate)
    {
        this.logDate = logDate;
    }

}
