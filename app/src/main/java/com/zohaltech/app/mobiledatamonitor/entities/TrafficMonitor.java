package com.zohaltech.app.mobiledatamonitor.entities;


import java.util.Date;

public class TrafficMonitor {

    private Long totalTraffic;
    private String date;


    public TrafficMonitor(Long totalTraffic, String date) {
        setTotalTraffic(totalTraffic);
        setDate(date);
    }

    public Long getTotalTraffic() {
        return totalTraffic;
    }

    public void setTotalTraffic(Long totalTraffic) {
        this.totalTraffic = totalTraffic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
