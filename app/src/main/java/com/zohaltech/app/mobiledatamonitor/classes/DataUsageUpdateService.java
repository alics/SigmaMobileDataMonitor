package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.IntentService;
import android.content.Intent;

public class DataUsageUpdateService extends IntentService {

    //private static long    totalUsage   = 0;
    private static long    receiveBytes = 0;
    private static long    sentBytes    = 0;
    private static boolean firstTime    = true;

    private static int interval    = 0;
    private static long tempUsage    = 0;

    public DataUsageUpdateService() {
        super("Data Usage Update Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (firstTime) {
            firstTime = false;
        } else {
            receiveBytes = android.net.TrafficStats.getMobileRxBytes() - receiveBytes;
            sentBytes = android.net.TrafficStats.getMobileTxBytes() - sentBytes;
            //totalUsage = totalUsage + receiveBytes + sentBytes;
        }

        interval++;
        tempUsage = tempUsage + receiveBytes + sentBytes;
        if (interval == 60){
            //todo : import tempUsage to database
            interval = 0;
            tempUsage = 0;
        }
        NotificationHandler.displayNotification(App.context, String.format("Down: %s B/s, Up:%s B/s", receiveBytes, sentBytes)
                , String.format("Total: %s B", "per day total from database")
                , "28% of 3 Gigabyte used");

        //Toast.makeText(context, String.format("Down: %s B/s, Up:%s B/s", receiveBytes, sentBytes), Toast.LENGTH_SHORT).show();

        receiveBytes = android.net.TrafficStats.getMobileRxBytes();
        sentBytes = android.net.TrafficStats.getMobileTxBytes();
    }
}
