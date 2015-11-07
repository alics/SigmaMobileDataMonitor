package com.zohaltech.app.sigma.entities;

public class AppsTrafficMonitor {
    private String appName;
    private int    appIcon;
    private Long   mobileTraffic;
    private Long   wifiTraffic;

    public AppsTrafficMonitor(String appName, int appIcon, Long mobileTraffic, Long wifiTraffic) {
        setAppName(appName);
        setAppIcon(appIcon);
        setMobileTraffic(mobileTraffic);
        setWifiTraffic(wifiTraffic);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(int appIcon) {
        this.appIcon = appIcon;
    }

    public Long getMobileTraffic() {
        return mobileTraffic;
    }

    public void setMobileTraffic(Long mobileTraffic) {
        this.mobileTraffic = mobileTraffic;
    }

    public Long getWifiTraffic() {
        return wifiTraffic;
    }

    public void setWifiTraffic(Long wifiTraffic) {
        this.wifiTraffic = wifiTraffic;
    }
}
