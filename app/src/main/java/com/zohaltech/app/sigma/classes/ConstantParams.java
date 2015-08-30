package com.zohaltech.app.sigma.classes;


import android.os.Environment;

import com.zohaltech.app.sigma.R;

public final class ConstantParams {
    private static String licenseFilePath = Environment.getExternalStorageDirectory() + App.context.getString(R.string.vps);
    private static String fileName = App.context.getString(R.string.sys);
    private static String token = App.context.getString(R.string.cs);
    private static String secretKey = App.context.getString(R.string.sdj);
    private static String iv = App.context.getString(R.string.v);
    private static String seven = App.context.getString(R.string.seven);

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
