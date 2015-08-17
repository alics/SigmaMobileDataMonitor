package com.zohaltech.app.mobiledatamonitor.classes;


public class LicenseStatus {
    String appVersion;
    String androidId;
    String installDate;
    String status;
    int    checkFlag;

    public LicenseStatus(String appVersion, String androidId, String installDate, String status, int checkFlag) {
        setAppVersion(appVersion);
        setAndroidId(androidId);
        setInstallDate(installDate);
        setStatus(status);
        setCheckFlag(checkFlag);
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
