package com.zohaltech.app.sigma.classes;


import android.os.Environment;

import com.zohaltech.app.sigma.R;

public final class ConstantParams {
    private static String licenseFilePath = Environment.getExternalStorageDirectory() + "/.vps/";
    private static String fileName        = App.context.getString(R.string.sys);
    private static String token           = "hdg@a7dt62$3ejh&";
    private static String secretKey       = "f77995466a906864";
    private static String iv              = "a052108fcc1c4fc5";
    private static String seven           = "71c450he388f1c2c";

    public static String getLicenseFilePath() {
        return licenseFilePath;
    }

    public static String getFileName() {
        return fileName;
    }

    public static String getToken() {
        return token;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public static String getIv() {
        return iv;
    }

    public static String getSeven() {
        return seven;
    }
}
