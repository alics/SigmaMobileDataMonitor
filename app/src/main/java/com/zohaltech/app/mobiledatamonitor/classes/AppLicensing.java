package com.zohaltech.app.mobiledatamonitor.classes;


import android.os.Environment;

import com.zohaltech.app.mobiledatamonitor.BuildConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class AppLicensing {

    static String currentDate;

    public AppLicensing() {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + "/" + App.context.getPackageName() + "/", "ZT_DATA_MONITOR.txt");
            if (!file.exists()) {
                file.mkdirs();
                Boolean result = file.createNewFile();
                if (result) {
                    setValidCurrentDate();
                    FileWriter writer = new FileWriter(file, true);

                    String appVersion = "app_version=" + BuildConfig.VERSION_CODE;
                    byte[] encryptedAppVersion = XsamCrypt.hexToBytes(appVersion);
                    writer.write(new String(encryptedAppVersion));
                    writer.append("\n");

                    String androidId = "android_id=" + Helper.getAndroidId();
                    byte[] encryptedAndroidId = XsamCrypt.hexToBytes(androidId);
                    writer.append(new String(encryptedAndroidId));
                    writer.append("\n");

                    setValidCurrentDate();
                    String installDate = "install_date=" + currentDate;
                    byte[] encryptedInstallDate = XsamCrypt.hexToBytes(installDate);
                    writer.append(new String(encryptedInstallDate));
                    writer.append("\n");

                    String flag = "flag=" + 1;
                    byte[] encryptedFlag = XsamCrypt.hexToBytes(flag);
                    writer.append(new String(encryptedFlag));
                    writer.append("\n");

                    String status = "status=not_registered";
                    byte[] encryptedStatus = XsamCrypt.hexToBytes(status);
                    writer.append(new String(encryptedStatus));
                    writer.append("\n");

                    writer.flush();
                    writer.close();
                }
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static Boolean existConfig() {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + App.context.getPackageName() + "/", "ZT_DATA_MONITOR.txt");
        return file.exists();
    }

    private void setValidCurrentDate() {
        int timeType = android.provider.Settings.System.getInt(App.context.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 1);
        android.provider.Settings.System.putInt(App.context.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 1);
        currentDate = Helper.getCurrentDate();

        if (timeType == 0) {
            android.provider.Settings.System.putInt(App.context.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 1);
            App.handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentDate = Helper.getCurrentDate();
                }
            }, 3000);
            android.provider.Settings.System.putInt(App.context.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0);
        }
    }
}
