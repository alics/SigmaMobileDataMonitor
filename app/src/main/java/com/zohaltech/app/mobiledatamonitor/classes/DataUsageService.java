package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.dal.UsageLogs;
import com.zohaltech.app.mobiledatamonitor.entities.UsageLog;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class DataUsageService extends Service {

    private static final String LAST_RECEIVED_BYTES = "LAST_RECEIVED_BYTES";
    private static final String LAST_SENT_BYTES     = "LAST_SENT_BYTES";
    private static final String DAILY_USAGE_DATE    = "DAILY_USAGE_DATE";
    private static final String DAILY_USAGE_BYTES   = "DAILY_USAGE_BYTES";
    private static final String TOTAL_USAGE_BYTES   = "TOTAL_USAGE_BYTES";
    private static final int    USAGE_LOG_INTERVAL  = 60;

    private static boolean firstTime        = true;
    private static int     usageLogInterval = 0;
    private static ScheduledExecutorService executorService;

    private Runnable runnable = new Runnable() {
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
                    App.preferences.edit().putLong(LAST_RECEIVED_BYTES, currentReceivedBytes).commit();
                    App.preferences.edit().putLong(LAST_SENT_BYTES, currentSentBytes).commit();
                }
                receivedBytes = currentReceivedBytes - App.preferences.getLong(LAST_RECEIVED_BYTES, 0);
                sentBytes = currentSentBytes - App.preferences.getLong(LAST_SENT_BYTES, 0);
                App.preferences.edit().putLong(LAST_RECEIVED_BYTES, currentReceivedBytes).commit();
                App.preferences.edit().putLong(LAST_SENT_BYTES, currentSentBytes).commit();

                String currentDate = Helper.getCurrentDate();
                if (App.preferences.getString(DAILY_USAGE_DATE, currentDate).equals(currentDate)) {
                    App.preferences.edit().putLong(DAILY_USAGE_BYTES, App.preferences.getLong(DAILY_USAGE_BYTES, 0) + receivedBytes + sentBytes).commit();
                } else {
                    App.preferences.edit().putLong(DAILY_USAGE_BYTES, receivedBytes + sentBytes).commit();
                }
                App.preferences.edit().putString(DAILY_USAGE_DATE, currentDate).commit();

                App.preferences.edit().putLong(TOTAL_USAGE_BYTES, App.preferences.getLong(TOTAL_USAGE_BYTES, 0) + receivedBytes + sentBytes).commit();

                final long currentUsedTraffic = receivedBytes + sentBytes;
                usageLogInterval++;
                if (usageLogInterval == USAGE_LOG_INTERVAL) {
                    new Thread(new Runnable() {
                        public void run() {
                            UsageLogs.insert(new UsageLog(currentUsedTraffic));
                        }
                    }).start();
                    usageLogInterval = 0;
                }
            }

            //Random r = new Random();
            //int Low = 10;
            //int High = 1024 * 30;
            //int sumReceivedSent = r.nextInt(High - Low) + Low;

            int iconId = R.drawable.wkb000;
            long sumReceivedSent = (receivedBytes + sentBytes) / 1024;

            if (sumReceivedSent < 1000) {
                iconId = App.context.getResources().getIdentifier("wkb" + String.format("%03d", sumReceivedSent), "drawable", getPackageName());
            } else if (sumReceivedSent >= 1000 && sumReceivedSent <= 1024) {
                iconId = App.context.getResources().getIdentifier("wmb010", "drawable", getPackageName());
            } else if ((float) sumReceivedSent / 1024 > 1 && (float) sumReceivedSent / 1024 < 10) {
                BigDecimal decimal = Helper.round((float) sumReceivedSent / 1024, 1);
                iconId = App.context.getResources().getIdentifier("wmb0" + decimal.toString().replace(".", ""), "drawable", getPackageName());
            } else if (sumReceivedSent / 1024 >= 10 && sumReceivedSent / 1024 <= 200) {
                sumReceivedSent = (sumReceivedSent / 1024) + 90;
                iconId = App.context.getResources().getIdentifier("wmb" + sumReceivedSent, "drawable", getPackageName());
            } else if (sumReceivedSent / 1024 > 200) {
                sumReceivedSent = (sumReceivedSent / 1024) + 90;
                iconId = App.context.getResources().getIdentifier("wmb" + sumReceivedSent, "drawable", getPackageName());
            }

            String dailyUsage = Helper.getUsedTraffic(App.preferences.getLong(DAILY_USAGE_BYTES, 0));
            String totalUsage = Helper.getUsedTraffic(App.preferences.getLong(TOTAL_USAGE_BYTES, 0));

            startForeground(1, NotificationHandler.getNotification(DataUsageService.this, iconId, String.format("Down: %s, Up: %s", Helper.getTransferRate(receivedBytes), Helper.getTransferRate(sentBytes)), "Daily Usage : " + dailyUsage, "Total Usage : " + totalUsage));

            //NotificationHandler.displayNotification(App.context, iconId, String.format("Down: %s, Up: %s", Helper.getTransferRate(receivedBytes), Helper.getTransferRate(sentBytes))
            //        , "Total: " + totalUsage, "65% used");

            //log("Notification : receivedBytes = " + receivedBytes + ", sentBytes = " + sentBytes + ", totalUsage = " + strCurrentDateTotalTraffic);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        if (executorService == null) {
            executorService = Executors.newSingleThreadScheduledExecutor();
        }
        executorService.scheduleAtFixedRate(runnable, 0L, 1000L, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
        executorService = null;
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