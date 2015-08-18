package com.zohaltech.app.mobiledatamonitor.classes;

import com.zohaltech.app.mobiledatamonitor.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class LicenseManager {

    public enum Status {TESTING_TIME, REGISTERED, EXPIRED}

    public void initializeLicenseFile(LicenseStatus status) {
        LicenseModifier.initializeLicenseFile(status);
    }

    public static boolean validateLicense() {
        LicenseStatus status = LicenseModifier.getLicenceFile();
        if (status == null)
            return false;

        if (!status.getDeviceId().equals(Helper.getDeviceId())) {
            status.setStatus(Status.EXPIRED.ordinal());
            LicenseModifier.updateLicenseFile(status);
            return false;
        }
        String installDateStr = status.getInstallDate();
        if (Helper.getCurrentDate().equals(installDateStr)) {
            status.setCheckCount(status.getCheckCount() + 1);
            status.setAppVersion(BuildConfig.VERSION_CODE + "");
            LicenseModifier.updateLicenseFile(status);
            return true;
        }

        int checkCount = status.getCheckCount();
        if (checkCount > Integer.valueOf(ConstantParams.SEVEN.substring(0, 1))) {
            status.setStatus(Status.EXPIRED.ordinal());
            LicenseModifier.updateLicenseFile(status);
            return false;
        }

        String calculatedInstallDate = Helper.addDay(0 - checkCount);
        if (!calculatedInstallDate.equals(installDateStr)) {
            status.setStatus(Status.EXPIRED.ordinal());
            LicenseModifier.updateLicenseFile(status);
            return false;
        }

        status.setCheckCount(status.getCheckCount() + 1);
        status.setAppVersion(BuildConfig.VERSION_CODE + "");
        LicenseModifier.updateLicenseFile(status);

        return true;
    }

    public static Boolean getLicenseStatus() {
        LicenseStatus status = LicenseModifier.getLicenceFile();
        return status != null &&
               (status.getStatus() == Status.REGISTERED.ordinal() ||
                status.getStatus() == Status.TESTING_TIME.ordinal());
    }

    public static void registerLicense() {
        LicenseStatus status = LicenseModifier.getLicenceFile();
        if (status == null) {
            status = new LicenseStatus(BuildConfig.VERSION_CODE + "",
                                       Helper.getDeviceId(),
                                       Helper.getCurrentDate(),
                                       Status.REGISTERED.ordinal(),
                                       1);
            LicenseModifier.initializeLicenseFile(status);
        }
        status.setStatus(Status.REGISTERED.ordinal());
        LicenseModifier.updateLicenseFile(status);
    }


    private static class LicenseModifier {
        private static void initializeLicenseFile(LicenseStatus licenseStatus) {
            try {
                File dir = new File(ConstantParams.LICENSE_FILE_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir.getPath(), ConstantParams.FILE_NAME);
                Boolean result = file.createNewFile();
                if (result) {
                    FileWriter writer = new FileWriter(file, true);

                    XsamCrypt xsamCrypt = new XsamCrypt();
                    String textFileStr = createLicenseFileText(licenseStatus);
                    String encryptedTextFile = XsamCrypt.bytesToHex(xsamCrypt.encrypt(textFileStr));

                    writer.write(encryptedTextFile);
                    writer.flush();
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static LicenseStatus getLicenceFile() {
            String decryptedText = getCurrentLicenseStr();
            if (decryptedText != null) {
                LicenseStatus licenseStatus = new LicenseStatus();
                String[] fileds = decryptedText.split("\n");
                for (int record = 0; record < fileds.length; record++) {
                    String filedText = fileds[record];
                    switch (record) {
                        case 0:
                            licenseStatus.setAppVersion(filedText);
                            break;
                        case 1:
                            licenseStatus.setDeviceId(filedText);
                            break;
                        case 2:
                            licenseStatus.setInstallDate(filedText);
                            break;
                        case 3:
                            licenseStatus.setStatus(Integer.valueOf(filedText));
                            break;
                        case 4:
                            licenseStatus.setCheckCount(Integer.valueOf(filedText));
                            break;
                    }
                }
                return licenseStatus;
            }
            return null;
        }

        public static void updateLicenseFile(LicenseStatus licenseStatus) {
            File file = existLicenseFile();
            if (file != null) {
                try {
                    FileWriter writer = new FileWriter(file, false);
                    XsamCrypt xsamCrypt = new XsamCrypt();
                    String textFileStr = createLicenseFileText(licenseStatus);
                    String encryptedTextFile = XsamCrypt.bytesToHex(xsamCrypt.encrypt(textFileStr));

                    writer.write(encryptedTextFile);
                    writer.flush();
                    writer.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public static File existLicenseFile() {
            File file = new File(ConstantParams.LICENSE_FILE_PATH, ConstantParams.FILE_NAME);
            if (file.exists())
                return file;
            return null;
        }

        private static String getCurrentLicenseStr() {
            File file = existLicenseFile();
            if (file != null) {
                StringBuilder text = new StringBuilder();
                String decryptedText = null;
                XsamCrypt xsamCrypt = new XsamCrypt();

                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = br.readLine()) != null) {
                        text.append(line);
                    }
                    br.close();

                    decryptedText = new String(xsamCrypt.decrypt(text.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return decryptedText;
            }
            return null;
        }

        private static String createLicenseFileText(LicenseStatus licenseStatus) {
            StringBuilder licenseStr = new StringBuilder();

            String appVersion = licenseStatus.getAppVersion();
            licenseStr.append(appVersion);
            licenseStr.append("\n");

            String androidId = licenseStatus.getDeviceId();
            licenseStr.append(androidId);
            licenseStr.append("\n");

            String installDate = licenseStatus.getInstallDate();
            licenseStr.append(installDate);
            licenseStr.append("\n");

            String status = licenseStatus.getStatus() + "";
            licenseStr.append(status);
            licenseStr.append("\n");

            String executionCount = licenseStatus.getCheckCount() + "";
            licenseStr.append(executionCount);

            return licenseStr.toString();
        }
    }
}
