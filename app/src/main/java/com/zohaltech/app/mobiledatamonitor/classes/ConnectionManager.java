package com.zohaltech.app.mobiledatamonitor.classes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionManager {

    public enum NetworkStatus
    {
        Connected,
        NotConnected,
        Error
    }

    private static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    public static NetworkStatus getNetworkStatus() {
        NetworkStatus result = NetworkStatus.NotConnected;
        if (isNetworkAvailable()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://clients3.google.com/generate_204").openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                if (urlc.getResponseCode() == 204 &&
                    urlc.getContentLength() == 0) {
                    result = NetworkStatus.Connected;
                } else {
                    result = NetworkStatus.Connected;
                }
            } catch (Exception e) {
                //Log.d("TAG", "Error checking internet connection", e);
                result = NetworkStatus.Error;
            }
        }
        return result;
    }
}