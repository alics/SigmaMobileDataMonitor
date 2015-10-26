package com.zohaltech.app.sigma.classes;

import com.zohaltech.app.sigma.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class LicenseManager {

    public LicenseManager() {
    }

    public static void initializeLicenseFile(LicenseStatus status) {
        LicenseModifier.initializeLicenseFile(status);
    }

    //public static boolean validateLicense() {
    //    LicenseStatus status = LicenseModifier.getLicenceFile();
    //    if (status == null)
    //        return false;
    //    if (!status.getDeviceId().equals(Helper.getDeviceId())) {
    //        status.setStatus(Status.NOT_REGISTERED.ordinal());
    //        LicenseModifier.updateLicenseFile(status);
    //        return false;
    //    }
    //    return status.getStatus() == Status.REGISTERED.ordinal();
    //}

    public static Status getLicenseStatus() {
        LicenseStatus status = LicenseModifier.getLicenceFile();

        if (status == null) {
            return Status.NOT_REGISTERED;
        }
        if (status.getStatus() == Status.REGISTERED.ordinal()) {
            return Status.REGISTERED;
        }
        status.setStatus(Status.NOT_REGISTERED.ordinal());
        LicenseModifier.updateLicenseFile(status);
        return Status.NOT_REGISTERED;
    }

    public static void registerLicense() {
        LicenseStatus status = LicenseModifier.getLicenceFile();
        if (status == null) {
            status = new LicenseStatus("" + BuildConfig.VERSION_CODE,
                                       Helper.getDeviceId(),
                                       Helper.getCurrentDate(),
                                       Status.REGISTERED.ordinal());
            LicenseModifier.initializeLicenseFile(status);
        }
        status.setStatus(Status.REGISTERED.ordinal());
        LicenseModifier.updateLicenseFile(status);
    }

    public static LicenseStatus getExistingLicense() {
        return LicenseModifier.getLicenceFile();
    }

    public static void updateLicense(LicenseStatus status) {
        LicenseModifier.updateLicenseFile(status);
    }

    public enum Status {REGISTERED, NOT_REGISTERED}

    private static class LicenseModifier {
        private static void initializeLicenseFile(LicenseStatus licenseStatus) {
            try {
                File dir = new File(ConstantParams.getLicenseFilePath());
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                Boolean result = true;
                File file = new File(dir.getPath(), ConstantParams.getFileName());
                if (!file.exists())
                    result = file.createNewFile();
                if (result) {
                    FileWriter writer = new FileWriter(file, false);

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
                String[] fields = decryptedText.split("\n");
                for (int record = 0; record < fields.length; record++) {
                    String filedText = fields[record];
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
            File file = new File(ConstantParams.getLicenseFilePath(), ConstantParams.getFileName());
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

            return licenseStr.toString();
        }
    }
}
