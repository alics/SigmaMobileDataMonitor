package com.zohaltech.app.mobiledatamonitor.entities;


import java.util.Date;

public class TrafficMonitor {

    private Long totalTraffic;
    private Date date;


    public TrafficMonitor(Long totalTraffic, Date date) {
        setTotalTraffic(totalTraffic);
        setDate(date);
    }

    public Long getTotalTraffic() {
        return totalTraffic;
    }

    public void setTotalTraffic(Long totalTraffic) {
        this.totalTraffic = totalTraffic;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
