package com.zohaltech.app.datausagemonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    private static long    totalUsage = 0;
    private static long    receiveBytes = 0;
    private static long    sentBytes    = 0;
    private static boolean firstTime    = true;

    @Override
    public void onReceive(Context context, Intent arg1) {

        //Toast.makeText(context, String.format("Received:%s\nSent:%s",
        //                                      android.net.TrafficStats.getMobileRxBytes() / 1024,
        //                                      android.net.TrafficStats.getMobileTxBytes() / 1024),
        //               Toast.LENGTH_SHORT).show();

        if (firstTime) {
            NotificationHandler.displayNotification(context, "Data Usage", String.format("Received: %s Bytes, Sent:%s Bytes", 0, 0));
            firstTime = false;
        } else {
            receiveBytes = android.net.TrafficStats.getMobileRxBytes() - receiveBytes;
            sentBytes = android.net.TrafficStats.getMobileTxBytes() - sentBytes;
            NotificationHandler.displayNotification(context, String.format("دریافت: %s B/s, ارسال:%s B/s", receiveBytes, sentBytes), String.format("کل مصرف: %s B", 100));
        }
        receiveBytes = android.net.TrafficStats.getMobileRxBytes();
        sentBytes = android.net.TrafficStats.getMobileTxBytes();


    }
}
