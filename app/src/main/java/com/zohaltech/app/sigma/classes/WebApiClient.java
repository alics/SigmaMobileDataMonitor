package com.zohaltech.app.sigma.classes;

import android.os.Build;

import com.zohaltech.app.sigma.BuildConfig;
import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.dal.SystemSettings;
import com.zohaltech.app.sigma.entities.SystemSetting;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebApiClient {

    private static final int    APP_ID   = 1;
    private static final String HOST_URL = App.context.getString(R.string.host_name);

    public static void sendUserData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SystemSetting setting = SystemSettings.getCurrentSettings();
                    JSONObject jsonObject = new JSONObject();

                    String token = App.uiPreferences.getString("PURCHASE_TOKEN", null);

                    if (LicenseManager.getLicenseStatus() == LicenseManager.Status.NOT_REGISTERED) {
                        if (!setting.getInstalled()) {
                            if (ConnectionManager.getInternetStatus() == ConnectionManager.InternetStatus.Connected) {
                                jsonObject.accumulate("SecurityKey", ConstantParams.getApiSecurityKey());
                                jsonObject.accumulate("AppId", APP_ID);
                                jsonObject.accumulate("DeviceId", Helper.getDeviceId());
                                jsonObject.accumulate("DeviceBrand", Build.MANUFACTURER);
                                jsonObject.accumulate("DeviceModel", Build.MODEL);
                                jsonObject.accumulate("AndroidVersion", Build.VERSION.RELEASE);
                                jsonObject.accumulate("ApiVersion", Build.VERSION.SDK_INT);
                                jsonObject.accumulate("OperatorId", Helper.getOperator().ordinal());
                                jsonObject.accumulate("IsPurchased", false);
                                jsonObject.accumulate("MarketId", App.market);
                                jsonObject.accumulate("AppVersion", BuildConfig.VERSION_CODE);
                                jsonObject.accumulate("PurchaseToken", token);
                                Boolean result = post(jsonObject);
                                if (result) {
                                    setting.setInstalled(true);
                                    SystemSettings.update(setting);
                                }
                            }
                        }
                    } else {
                        if (!setting.getRegistered()) {
                            if (ConnectionManager.getInternetStatus() == ConnectionManager.InternetStatus.Connected) {
                                jsonObject.accumulate("SecurityKey", ConstantParams.getApiSecurityKey());
                                jsonObject.accumulate("AppId", APP_ID);
                                jsonObject.accumulate("DeviceId", Helper.getDeviceId());
                                jsonObject.accumulate("DeviceBrand", Build.MANUFACTURER);
                                jsonObject.accumulate("DeviceModel", Build.MODEL);
                                jsonObject.accumulate("AndroidVersion", Build.VERSION.RELEASE);
                                jsonObject.accumulate("ApiVersion", Build.VERSION.SDK_INT);
                                jsonObject.accumulate("OperatorId", Helper.getOperator().ordinal());
                                jsonObject.accumulate("IsPurchased", true);
                                jsonObject.accumulate("MarketId", App.market);
                                jsonObject.accumulate("AppVersion", BuildConfig.VERSION_CODE);
                                jsonObject.accumulate("PurchaseToken", token);
                                Boolean result = post(jsonObject);
                                if (result) {
                                    setting.setRegistered(true);
                                    SystemSettings.update(setting);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public static void checkForUpdate() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String lastUpdateCheckDate = App.uiPreferences.getString("UPDATE_CHECK_DATE", "");
                    if (lastUpdateCheckDate.equals("") || (lastUpdateCheckDate.equals(Helper.getCurrentDate()) == false)) {
                        App.uiPreferences.edit().putString("UPDATE_CHECK_DATE", Helper.getCurrentDate()).apply();
                        String queryString = String.format("?SecurityKey=%s&AppId=%s&MarketId=%s&AppVersion=%s&DeviceId=%s",
                                                           ConstantParams.getApiSecurityKey(), APP_ID, App.market, BuildConfig.VERSION_CODE, Helper.getDeviceId());
                        if (get("http://zohaltech.com/api/appversion", queryString) == 1) {
                            NotificationHandler.displayUpdateNotification(App.context, 3, "پیغام سیگما", "نسخه جدید سیگما آماده بروزرسانی میباشد");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    private static Boolean post(JSONObject jsonObject) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(HOST_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonObject.toString());
            out.close();

            return urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK;

        } catch (MyRuntimeException | IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return false;
    }

    private static int get(String urlAddress, String queryString) {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(urlAddress + queryString);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String result = Helper.inputStreamToString(in).trim();
            if (result.length() == 1) {
                return Integer.parseInt(result);
            }
        } catch (MyRuntimeException | IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return 0;
    }
}
