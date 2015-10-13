package com.zohaltech.app.sigma.classes;


import android.net.TrafficStats;

public class AppsTrafficRecord {
    public enum ConnectivityType {DATA, WIFI}

    long tx       = 0;
    long rx       = 0;
    long wifi     = 0;
    long data     = 0;
    long sumBytes = 0;
    Integer appId;
    String appName     = null;
    String packageName = null;
    ConnectivityType connectivityType;

    AppsTrafficRecord() {
    }

    AppsTrafficRecord(int uid, int appId, String appName, String packageName, ConnectivityType connectivityType, AppsTrafficRecord previousRecord) {
        tx = TrafficStats.getUidTxBytes(uid);
        rx = TrafficStats.getUidRxBytes(uid);

        if (previousRecord != null) {
            sumBytes = (tx - previousRecord.tx) + (rx - previousRecord.rx);
        } else {
            sumBytes = tx + rx;
        }


        this.appName = appName;
        this.packageName = packageName;
        this.appId = appId;
        this.connectivityType = connectivityType;
    }
}
