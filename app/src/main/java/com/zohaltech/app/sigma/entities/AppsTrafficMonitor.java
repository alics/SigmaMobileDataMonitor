package com.zohaltech.app.sigma.entities;

public class AppsTrafficMonitor {
    private Integer uid;
    private String  appName;
    private String  packageName;
    private Long    mobileTraffic;
    private Long    wifiTraffic;


    public AppsTrafficMonitor(Integer uid, Long mobileTraffic, Long wifiTraffic) {
        setUid(uid);
        setMobileTraffic(mobileTraffic);
        setWifiTraffic(wifiTraffic);
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
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
