package com.zohaltech.app.mobiledatamonitor.classes;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;


public class LicenseModifier {



    public static void initializeLicenseFile(LicenseStatus licenseStatus) {
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
