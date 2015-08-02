package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.zohaltech.app.mobiledatamonitor.R;

import java.math.BigDecimal;
import java.util.Timer;
import java.util.TimerTask;


public class DataUsageService_ extends Service {


    private static final int     USAGE_LOG_INTERVAL         = 60;
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
                firstTime = true;
            } else {
                if (firstTime) {
                    firstTime = false;
                    tempReceivedBytes = currentReceivedBytes;
                    App.preferences.edit().putLong("tempReceivedBytes", tempReceivedBytes).commit();
                    tempSentBytes = currentSentBytes;
                    App.preferences.edit().putLong("tempSentBytes", tempSentBytes).commit();
                }
                receivedBytes = currentReceivedBytes - App.preferences.getLong("tempReceivedBytes", 0);
                sentBytes = currentSentBytes - App.preferences.getLong("tempSentBytes", 0);
                tempReceivedBytes = currentReceivedBytes;
                App.preferences.edit().putLong("tempReceivedBytes", tempReceivedBytes).commit();
                tempSentBytes = currentSentBytes;
                App.preferences.edit().putLong("tempSentBytes", tempSentBytes).commit();

                tempUsage = App.preferences.getLong("tempUsage", 0);

                tempUsage = tempUsage + receivedBytes + sentBytes;

                App.preferences.edit().putLong("tempUsage", tempUsage).commit();

                //usageLogInterval++;
                //if (usageLogInterval == USAGE_LOG_INTERVAL) {
                //UsageLogs.insert(new UsageLog(tempUsage));
                //currentDateSumTraffic = UsageLogs.getCurrentDateSumTraffic();
                //strCurrentDateTotalTraffic = String.format("%.2f MB", (float) currentDateSumTraffic / (1024 * 1024));
                //usageLogInterval = 0;
                //tempUsage = 0;
                //}
                //strCurrentDateTotalTraffic = String.format("%.2f MB", (float) tempUsage / (1024 * 1024));

            }

            int iconId;
            long value = (receivedBytes + sentBytes) / 1024;
            //Random r = new Random();
            //int Low = 10;
            //int High = 1024 * 30;
            //int value = r.nextInt(High - Low) + Low;

            if (value < 1000) {
                iconId = App.context.getResources().getIdentifier("wkb" + String.format("%03d", value), "drawable", getPackageName());
            } else if (value >= 1000 && value <= 1024) {
                iconId = App.context.getResources().getIdentifier("wmb010", "drawable", getPackageName());
            } else if ((float) value / 1024 > 1 && (float) value / 1024 < 10) {
                BigDecimal decimal = Helper.round((float) value / 1024, 1);
                iconId = App.context.getResources().getIdentifier("wmb0" + decimal.toString().replace(".", ""), "drawable", getPackageName());
            } else if (value / 1024 >= 10 && value / 1024 <= 200) {
                value = (value / 1024) + 90;
                iconId = App.context.getResources().getIdentifier("wmb" + value, "drawable", getPackageName());
            } else if (value / 1024 > 200) {
                value = (value / 1024) + 90;
                iconId = App.context.getResources().getIdentifier("wmb" + value, "drawable", getPackageName());
            } else {
                iconId = R.drawable.wkb000;
            }

            String total = Helper.getUsedTraffic(App.preferences.getLong("tempUsage", 0));

            NotificationHandler.displayNotification(App.context, iconId, String.format("Down: %s, Up: %s", Helper.getTransferRate(receivedBytes), Helper.getTransferRate(sentBytes))
                    , "Total: " + total, "65% used");
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