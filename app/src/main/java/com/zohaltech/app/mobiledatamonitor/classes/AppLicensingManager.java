package com.zohaltech.app.mobiledatamonitor.classes;


import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class AppLicensingManager {

    public static String currentDate;
    private static final String LICENSE_FILE_NAME = "ZT_DATA_MONITOR.TXT";

    public static void initializeLicenseFile(LicenseStatus licenseStatus) {
        try {
            File dir = new File(Environment.getExternalStorageDirectory() + "/" + App.context.getPackageName() + "/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir.getPath(), LICENSE_FILE_NAME);
            Boolean result = file.createNewFile();
            if (result) {
                setValidCurrentDate();
                FileWriter writer = new FileWriter(file, true);

                XsamCrypt xsamCrypt = new XsamCrypt();
                String appVersion = licenseStatus.getAppVersion();
                String encryptedAppVersion = XsamCrypt.bytesToHex(xsamCrypt.encrypt(appVersion));
                writer.write(encryptedAppVersion);
                writer.append("\n");

                String androidId = licenseStatus.getAndroidId();
                String encryptedAndroidId = XsamCrypt.bytesToHex(xsamCrypt.encrypt(androidId));
                writer.append(encryptedAndroidId);
                writer.append("\n");

                setValidCurrentDate();
                String installDate = licenseStatus.getInstallDate();
                String encryptedInstallDate = XsamCrypt.bytesToHex(xsamCrypt.encrypt(installDate));
                writer.append(encryptedInstallDate);
                writer.append("\n");

                String flag = String.valueOf(licenseStatus.getCheckFlag());
                String encryptedFlag = XsamCrypt.bytesToHex(xsamCrypt.encrypt(flag));
                writer.append(encryptedFlag);
                writer.append("\n");

                String status = licenseStatus.getStatus()+"";
                String encryptedStatus = XsamCrypt.bytesToHex(xsamCrypt.encrypt(status));
                writer.append(encryptedStatus);
                writer.append("\n");

                writer.flush();
                writer.close();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static LicenseStatus getLicenceFile() {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + App.context.getPackageName() + "/", LICENSE_FILE_NAME);
        LicenseStatus licenseStatus=new LicenseStatus();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int record=0;
            XsamCrypt xsamCrypt = new XsamCrypt();
            while ((line = br.readLine()) != null) {
                switch (record) {
                    case 0:
                        licenseStatus.setAppVersion(XsamCrypt.bytesToHex(xsamCrypt.decrypt(line)));
                        break;
                    case 1:
                        licenseStatus.setAndroidId(XsamCrypt.bytesToHex(xsamCrypt.decrypt(line)));
                        break;
                    case 2:
                        licenseStatus.setInstallDate(XsamCrypt.bytesToHex(xsamCrypt.decrypt(line)));
                        break;
                    case 3:
                        licenseStatus.setCheckFlag(Integer.valueOf(XsamCrypt.bytesToHex(xsamCrypt.decrypt(line))));
                        break;
                    case 4:
                        licenseStatus.setStatus(Integer.valueOf(XsamCrypt.bytesToHex(xsamCrypt.decrypt(line))));
                        break;
                }
                record++;
            }
            br.close();
        } catch (Exception e) {

        }
        return licenseStatus;
    }

    public static Boolean existConfig() {
        File file = new File(Environment.getExternalStorageDirectory() + "/" + App.context.getPackageName() + "/", "ZT_DATA_MONITOR.txt");
        return file.exists();
    }

    public static void setValidCurrentDate() {
        int timeType = android.provider.Settings.System.getInt(App.context.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 1);
        android.provider.Settings.System.putInt(App.context.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 1);

        if (timeType == 1)
            currentDate = Helper.getCurrentDate();
        else if (timeType == 0) {
            android.provider.Settings.System.putInt(App.context.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 1);
            App.handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentDate = Helper.getCurrentDate();
                    android.provider.Settings.System.putInt(App.context.getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0);
                }
            }, 3000);
        }
    }
}
