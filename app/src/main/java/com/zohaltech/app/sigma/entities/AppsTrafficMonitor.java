package com.zohaltech.app.sigma.entities;

public class AppsTrafficMonitor {
    private String appName;
    private String    packageName;
    private Long   mobileTraffic;
    private Long   wifiTraffic;

    public AppsTrafficMonitor(String appName, String packageName, Long mobileTraffic, Long wifiTraffic) {
        setAppName(appName);
        setPackageName(packageName);
        setMobileTraffic(mobileTraffic);
        setWifiTraffic(wifiTraffic);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
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
