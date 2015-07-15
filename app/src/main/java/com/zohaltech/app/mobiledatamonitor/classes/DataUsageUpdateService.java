package com.zohaltech.app.mobiledatamonitor.classes;

/**
 * Created by Ali on 7/15/2015.
 */
import android.app.IntentService;
import android.content.Intent;

public class DataUsageUpdateService extends IntentService {

    private static long    totalUsage   = 0;
    private static long    receiveBytes = 0;
    private static long    sentBytes    = 0;
    private static boolean firstTime    = true;

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
            totalUsage = totalUsage + receiveBytes + sentBytes;
        }
        NotificationHandler.displayNotification(App.context, String.format("Down: %s B/s, Up:%s B/s", receiveBytes, sentBytes)
                , String.format("Total: %s B", totalUsage)
                , "28% of 3 Gigabyte used");

        //Toast.makeText(context, String.format("Down: %s B/s, Up:%s B/s", receiveBytes, sentBytes), Toast.LENGTH_SHORT).show();

        receiveBytes = android.net.TrafficStats.getMobileRxBytes();
        sentBytes = android.net.TrafficStats.getMobileTxBytes();
    }
}
