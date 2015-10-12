package com.zohaltech.app.sigma.classes;


import android.net.TrafficStats;

public class AppsTrafficRecord {
    public enum ConnectivityType {DATA, WIFI}

    long tx = 0;
    long rx = 0;
    long sumWifi=0;
    long sumData=0;
    Integer appId;
    String appName = null;
    String packageName = null;
    ConnectivityType connectivityType;

    AppsTrafficRecord() {
    }

    AppsTrafficRecord(int uid, int appId, String appName, String packageName, ConnectivityType connectivityType, AppsTrafficRecord previousRecord) {
        tx = TrafficStats.getUidTxBytes(uid);
        rx = TrafficStats.getUidRxBytes(uid);

        if (previousRecord != null) {
            if (previousRecord.connectivityType == ConnectivityType.WIFI) {
                sumWifi = sumWifi + previousRecord.rx + previousRecord.tx;
            } else {
                sumData = sumData + previousRecord.rx + previousRecord.tx;
            }
        }

        this.appName = appName;
        this.packageName = packageName;
        this.appId = appId;
        this.connectivityType = connectivityType;
    }
}
