package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.IntentService;
import android.content.Intent;

import com.zohaltech.app.mobiledatamonitor.dal.UsageLogs;
import com.zohaltech.app.mobiledatamonitor.entities.UsageLog;


public class DataUsageUpdateService extends IntentService {

    private static final int USAGE_LOG_INTERVAL       = 10;

    private static long tempReceivedBytes = 0;
    private static long tempSentBytes     = 0;

    private static boolean firstTime = true;

    private static int    usageLogInterval           = 0;
    private static long   tempUsage                  = 0;
    private static long   currentDateSumTraffic      = 0;
    private static String strCurrentDateTotalTraffic = "0.00 MB";

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

            tempUsage = tempUsage + receivedBytes + sentBytes;

            usageLogInterval++;
            if (usageLogInterval == USAGE_LOG_INTERVAL) {
                UsageLogs.insert(new UsageLog(tempUsage));
                currentDateSumTraffic = UsageLogs.getCurrentDateSumTraffic();
                strCurrentDateTotalTraffic = String.format("%.2f MB", (float) currentDateSumTraffic / (1024 * 1024));
                usageLogInterval = 0;
                tempUsage = 0;
            }
        }

        NotificationHandler.displayNotification(App.context, String.format("Down: %s B/s, Up:%s B/s", receivedBytes, sentBytes)
                , String.format("Total: %s", strCurrentDateTotalTraffic)
                , "28% of 3 Gigabyte used");
    }
}
