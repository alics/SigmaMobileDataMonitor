package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.dal.Settings;
import com.zohaltech.app.mobiledatamonitor.dal.UsageLogs;
import com.zohaltech.app.mobiledatamonitor.entities.Setting;
import com.zohaltech.app.mobiledatamonitor.entities.UsageLog;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ZtDataService extends Service {

    public static final  String  TODAY_USAGE_BYTES        = "TODAY_USAGE_BYTES";
    public static final  String  TODAY_USAGE_ACTION       = "com.zohaltech.app.mobiledatamonitor.TODAY_USAGE_ACTION";
    public static final  String  APPLICATION_ALARM_ACTION = "com.zohaltech.app.mobiledatamonitor.APPLICATION_ALARM_ACTION";
    private static final String  LAST_RECEIVED_BYTES      = "LAST_RECEIVED_BYTES";
    private static final String  LAST_SENT_BYTES          = "LAST_SENT_BYTES";
    private static final String  TODAY_USAGE_DATE         = "TODAY_USAGE_DATE";
    private static final String  ONE_MINUTE_USED_BYTES    = "ONE_MINUTE_USED_BYTES";
    private static final int     USAGE_LOG_INTERVAL       = 60;
    private static       boolean firstTime                = true;
    private static       int     usageLogInterval         = 0;
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
            }

            final long oneMinuteUsedBytes = App.preferences.getLong(ONE_MINUTE_USED_BYTES, 0) + receivedBytes + sentBytes;
            App.preferences.edit().putLong(ONE_MINUTE_USED_BYTES, oneMinuteUsedBytes).commit();

            usageLogInterval++;
            if (usageLogInterval == USAGE_LOG_INTERVAL) {
                new Thread(new Runnable() {
                    public void run() {
                        App.preferences.edit().putLong(ONE_MINUTE_USED_BYTES, 0).commit();
                        UsageLogs.insert(new UsageLog(oneMinuteUsedBytes));
                    }
                }).start();
                usageLogInterval = 0;

                Intent intent = new Intent(APPLICATION_ALARM_ACTION);
                sendBroadcast(intent);
            }

            String currentDate = Helper.getCurrentDate();
            if (currentDate.equals(App.preferences.getString(TODAY_USAGE_DATE, currentDate))) {
                App.preferences.edit().putLong(TODAY_USAGE_BYTES, App.preferences.getLong(TODAY_USAGE_BYTES, 0) + receivedBytes + sentBytes).commit();
            } else {
                App.preferences.edit().putLong(TODAY_USAGE_BYTES, receivedBytes + sentBytes).commit();
            }
            App.preferences.edit().putString(TODAY_USAGE_DATE, currentDate).commit();

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

            String todayUsage = TrafficUnitsUtil.getUsedTraffic(App.preferences.getLong(TODAY_USAGE_BYTES, 0));

            Setting setting = Settings.getCurrentSettings();
            boolean showNotification;
            if (setting.getShowNotification()) {
                if (setting.getShowNotificationWhenDataIsOn()) {
                    showNotification = setting.getDataConnected();
                } else {
                    showNotification = true;
                }
            } else {
                showNotification = false;
            }
            if (showNotification) {
                startForeground(1, NotificationHandler.getDataUsageNotification(ZtDataService.this, iconId, getString(R.string.down) + TrafficUnitsUtil.getTransferRate(receivedBytes) + getString(R.string.up) + TrafficUnitsUtil.getTransferRate(sentBytes), getString(R.string.today) + todayUsage));
            } else {
                NotificationHandler.cancelNotification(1);
            }

            Intent intent = new Intent(TODAY_USAGE_ACTION);
            intent.putExtra(TODAY_USAGE_BYTES, App.preferences.getLong(TODAY_USAGE_BYTES, 0));
            sendBroadcast(intent);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

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
        NotificationHandler.cancelNotification(1);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public static void restart(Context context){
        Intent service = new Intent(context, ZtDataService.class);
        context.stopService(service);
        context.startService(service);
    }
}