package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;


public class DataUsageService extends Service {


    private static final int     USAGE_LOG_INTERVAL         = 10;
    private static       long    tempReceivedBytes          = 0;
    private static       long    tempSentBytes              = 0;
    private static       boolean firstTime                  = true;
    private static       int     usageLogInterval           = 0;
    private static       long    tempUsage                  = 0;
    private static       long    currentDateSumTraffic      = 0;
    private static       String  strCurrentDateTotalTraffic = "0.00000 MB";
    private Timer timer;

    private TimerTask updateTask = new TimerTask() {
        @Override
        public void run() {
            long currentReceivedBytes = android.net.TrafficStats.getMobileRxBytes();
            long currentSentBytes = android.net.TrafficStats.getMobileTxBytes();
            long receivedBytes = 0;
            long sentBytes = 0;

            if (currentReceivedBytes + currentSentBytes == 0) {
                //log("transfer = 0");
                firstTime = true;
            } else {
                if (firstTime) {
                    firstTime = false;
                    tempReceivedBytes = currentReceivedBytes;
                    App.preferences.edit().putLong("tempReceivedBytes", tempReceivedBytes).commit();
                    tempSentBytes = currentSentBytes;
                    App.preferences.edit().putLong("tempSentBytes", tempSentBytes).commit();
                }
                //////////receivedBytes = currentReceivedBytes - tempReceivedBytes;
                receivedBytes = currentReceivedBytes - App.preferences.getLong("tempReceivedBytes", 0);
                //log("receivedBytes = " + receivedBytes);
                //////////sentBytes = currentSentBytes - tempSentBytes;
                sentBytes = currentSentBytes - App.preferences.getLong("tempSentBytes", 0);
                ;
                //log("sentBytes = " + sentBytes);
                tempReceivedBytes = currentReceivedBytes;
                App.preferences.edit().putLong("tempReceivedBytes", tempReceivedBytes).commit();
                tempSentBytes = currentSentBytes;
                App.preferences.edit().putLong("tempSentBytes", tempSentBytes).commit();

                tempUsage = App.preferences.getLong("tempUsage", 0);

                tempUsage = tempUsage + receivedBytes + sentBytes;

                App.preferences.edit().putLong("tempUsage", tempUsage).commit();

                //log("tempUsage = " + tempUsage);

                //usageLogInterval++;
                //if (usageLogInterval == USAGE_LOG_INTERVAL) {
                //UsageLogs.insert(new UsageLog(tempUsage));
                //log(tempUsage + " inserted");
                //currentDateSumTraffic = UsageLogs.getCurrentDateSumTraffic();
                //log("currentDateSumTraffic = " + currentDateSumTraffic);
                //strCurrentDateTotalTraffic = String.format("%.2f MB", (float) currentDateSumTraffic / (1024 * 1024));
                //log("strCurrentDateTotalTraffic = " + strCurrentDateTotalTraffic);
                //usageLogInterval = 0;
                //tempUsage = 0;
                //}
                //strCurrentDateTotalTraffic = String.format("%.2f MB", (float) tempUsage / (1024 * 1024));

            }

            int iconId;
            long value1 = App.preferences.getLong("tempUsage", 0) / 1024;
            if (value1 <= 999) {
                iconId = getResources().getIdentifier("wkb" + value1, "drawable", getPackageName());
            } else {
                iconId = getResources().getIdentifier("wmb291", "drawable", getPackageName());
            }

            NotificationHandler.displayNotification(App.context,iconId, String.format("Down: %s, Up: %s", Helper.getTransferRate(receivedBytes), Helper.getTransferRate(sentBytes))
                    , String.format("Total: %s", String.format("%.2f MB", (float) App.preferences.getLong("tempUsage", 0) / (1024 * 1024)))
                    , "28% of 3 Gigabyte used");

            //log("Notification : receivedBytes = " + receivedBytes + ", sentBytes = " + sentBytes + ", total = " + strCurrentDateTotalTraffic);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer("DataUsageUpdate");
        timer.schedule(updateTask, 0L, 1000L);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
        NotificationHandler.cancelNotification(App.context);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }
}