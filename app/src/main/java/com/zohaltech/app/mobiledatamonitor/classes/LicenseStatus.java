package com.zohaltech.app.mobiledatamonitor.classes;


public class LicenseStatus {
    private String appVersion;
    private String androidId;
    private String installDate;
    private int status;
    private int    checkFlag;

    public LicenseStatus(String appVersion, String androidId, String installDate, int status, int checkFlag) {
        setAppVersion(appVersion);
        setAndroidId(androidId);
        setInstallDate(installDate);
        setStatus(status);
        setCheckFlag(checkFlag);
    }

    public LicenseStatus(){}

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }

    public int getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(int checkFlag) {
        this.checkFlag = checkFlag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
