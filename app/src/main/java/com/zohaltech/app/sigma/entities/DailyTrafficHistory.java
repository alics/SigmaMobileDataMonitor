package com.zohaltech.app.sigma.entities;


public class DailyTrafficHistory
{

    private Integer id;
    private Long traffic;
    private Long wifiTraffic;
    private String logDate;

    public DailyTrafficHistory(Long traffic,Long wifiTraffic, String logDate)
    {
        setTraffic(traffic);
        setWifiTraffic(wifiTraffic);
        setLogDate(logDate);
    }

    public DailyTrafficHistory(Integer id, Long traffic,Long wifiTraffic, String logDate)
    {
        this(traffic,wifiTraffic, logDate);
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

    public Long getWifiTraffic()
    {
        return wifiTraffic;
    }

    public void setWifiTraffic(Long wifiTraffic)
    {
        this.wifiTraffic = wifiTraffic;
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
