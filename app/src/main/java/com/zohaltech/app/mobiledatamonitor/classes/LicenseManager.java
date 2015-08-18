package com.zohaltech.app.mobiledatamonitor.classes;

import android.os.SystemClock;

import com.zohaltech.app.mobiledatamonitor.BuildConfig;

public class LicenseManager {

    public LicenseManager() {


    }

    public void initializeLicenseFile() {

        LicenseStatus status1 = new LicenseStatus("" + BuildConfig.VERSION_CODE,
                                                  Helper.getDeviceId(),
                                                  getCurrentDate(),
                                                  1,
                                                  1);
        LicenseModifier.initializeLicenseFile(status1);
    }


    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String currentDate;


}
