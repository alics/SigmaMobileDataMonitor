package com.zohaltech.app.sigma.entities;


public class TrafficMonitor
{

    private Long totalTraffic;
    private Long totalTrafficWifi;
    private String date;

    public TrafficMonitor(Long totalTraffic, Long totalTrafficWifi, String date)
    {
        setTotalTraffic(totalTraffic);
        setTotalTrafficWifi(totalTrafficWifi);
        setDate(date);
    }

    public Long getTotalTraffic()
    {
        return totalTraffic;
    }

    public void setTotalTraffic(Long totalTraffic)
    {
        this.totalTraffic = totalTraffic;
    }

    public Long getTotalTrafficWifi()
    {
        return totalTrafficWifi;
    }

    public void setTotalTrafficWifi(Long totalTrafficWifi)
    {
        this.totalTrafficWifi = totalTrafficWifi;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }


}
