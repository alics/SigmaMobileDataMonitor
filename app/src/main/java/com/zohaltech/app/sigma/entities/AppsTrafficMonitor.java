package com.zohaltech.app.sigma.entities;

public class AppsTrafficMonitor {
    private String appName;
    private Long   totalTrafficData;
    private Long   totalTrafficWifi;

    public AppsTrafficMonitor(Long totalTrafficData, Long totalTrafficWifi, String appName) {
        setTotalTrafficData(totalTrafficData);
        setTotalTrafficWifi(totalTrafficWifi);
        setAppName(appName);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getTotalTrafficData() {
        return totalTrafficData;
    }

    public void setTotalTrafficData(Long totalTrafficData) {
        this.totalTrafficData = totalTrafficData;
    }

    public Long getTotalTrafficWifi() {
        return totalTrafficWifi;
    }

    public void setTotalTrafficWifi(Long totalTrafficWifi) {
        this.totalTrafficWifi = totalTrafficWifi;
    }
}
