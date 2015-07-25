package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.IntentService;
import android.content.Intent;

import com.zohaltech.app.mobiledatamonitor.dal.UsageLogs;
import com.zohaltech.app.mobiledatamonitor.entities.UsageLog;


public class DataUsageUpdateService extends IntentService {

    private static final int USAGE_LOG_INTERVAL = 5;

    private static long tempReceivedBytes = 0;
    private static long tempSentBytes     = 0;

    private static boolean firstTime = true;

    private static int  interval  = 0;
    private static long tempUsage = 0;

    public DataUsageUpdateService() {
        super("Data Usage Update Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        long currentReceivedBytes = android.net.TrafficStats.getMobileRxBytes();
        long currentSentBytes = android.net.TrafficStats.getMobileTxBytes();
        long receivedBytes = 0;
        long sentBytes = 0;

        if (currentReceivedBytes + currentSentBytes == 0) {
            firstTime = true;
        } else {
            if (firstTime) {
                firstTime = false;
                tempReceivedBytes = currentReceivedBytes;
                tempSentBytes = currentSentBytes;
            }
            receivedBytes = currentReceivedBytes - tempReceivedBytes;
            sentBytes = currentSentBytes - tempSentBytes;
            tempReceivedBytes = currentReceivedBytes;
            tempSentBytes = currentSentBytes;

            interval++;
            tempUsage = tempUsage + receivedBytes + sentBytes;
            if (interval == USAGE_LOG_INTERVAL) {
                UsageLogs.insert(new UsageLog(tempUsage));
                interval = 0;
                tempUsage = 0;
            }
        }

        //long tempCurrentDateSumTraffic = UsageLogs.getCurrentDateSumTraffic();
        //String strCurrentDateSumTraffic = tempCurrentDateSumTraffic + " B";
        //if (tempCurrentDateSumTraffic < 1024) {
        //    strCurrentDateSumTraffic = tempCurrentDateSumTraffic + " B";
        //} else if (tempCurrentDateSumTraffic >= 1024 && tempCurrentDateSumTraffic < (1024 * 1024)) {
        //    strCurrentDateSumTraffic = tempCurrentDateSumTraffic / 1024 + " KB";
        //} else if (tempCurrentDateSumTraffic > (1024 * 1024)) {
        //    strCurrentDateSumTraffic = tempCurrentDateSumTraffic / (1024 * 1024) + " MB";
        //} else if (tempCurrentDateSumTraffic > (1024 * 1024 * 1024)) {
        //    strCurrentDateSumTraffic = tempCurrentDateSumTraffic / (1024 * 1024 * 1024) + " GB";
        //}
        long tempCurrentDateSumTraffic = UsageLogs.getCurrentDateSumTraffic();
        String strCurrentDateSumTraffic = String.format("%.2f", (float) tempCurrentDateSumTraffic / (1024 * 1024)) + " MB";

        NotificationHandler.displayNotification(App.context, String.format("Down: %s B/s, Up:%s B/s", receivedBytes, sentBytes)
                , String.format("Total: %s", strCurrentDateSumTraffic)
                , "28% of 3 Gigabyte used");

        //Toast.makeText(context, String.format("Down: %s B/s, Up:%s B/s", receivedBytes, sentBytes), Toast.LENGTH_SHORT).show();

        //receivedBytes = android.net.TrafficStats.getMobileRxBytes();
        //sentBytes = android.net.TrafficStats.getMobileTxBytes();
    }
}
