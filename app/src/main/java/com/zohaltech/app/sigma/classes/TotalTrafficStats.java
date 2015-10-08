package com.zohaltech.app.sigma.classes;


import android.content.Intent;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.dal.UsageLogs;
import com.zohaltech.app.sigma.entities.UsageLog;

import java.math.BigDecimal;

public class TotalTrafficStats {

    public static final String TODAY_USAGE_BYTES = "TODAY_USAGE_BYTES";
    public static final String TODAY_USAGE_ACTION = "TODAY_USAGE_ACTION";
    public static final String APPLICATION_ALARM_ACTION = "APPLICATION_ALARM_ACTION";
    private static final String LAST_RECEIVED_BYTES = "LAST_RECEIVED_BYTES";
    private static final String LAST_SENT_BYTES = "LAST_SENT_BYTES";
    private static final String TODAY_USAGE_DATE = "TODAY_USAGE_DATE";
    private static final String ONE_MINUTE_USED_BYTES = "ONE_MINUTE_USED_BYTES";
    private static final int USAGE_LOG_INTERVAL = 60;
    private static int usageLogInterval = 0;

    public static void proceed(boolean firstTime) {

        long currentReceivedBytes = android.net.TrafficStats.getMobileRxBytes();
        long currentSentBytes = android.net.TrafficStats.getMobileTxBytes();
        long receivedBytes = 0;
        long sentBytes = 0;

        if (currentReceivedBytes + currentSentBytes == 0) {
            firstTime = true;
        } else {
            if (firstTime) {
                firstTime = false;
                App.preferences.edit().putLong(LAST_RECEIVED_BYTES, currentReceivedBytes).apply();
                App.preferences.edit().putLong(LAST_SENT_BYTES, currentSentBytes).apply();
            }
            receivedBytes = currentReceivedBytes - App.preferences.getLong(LAST_RECEIVED_BYTES, 0);
            sentBytes = currentSentBytes - App.preferences.getLong(LAST_SENT_BYTES, 0);
            if (receivedBytes < 0 || sentBytes < 0) {
                receivedBytes = receivedBytes >= 0 ? receivedBytes : 0;
                sentBytes = sentBytes >= 0 ? sentBytes : 0;
                firstTime = true;
            }
            App.preferences.edit().putLong(LAST_RECEIVED_BYTES, currentReceivedBytes).apply();
            App.preferences.edit().putLong(LAST_SENT_BYTES, currentSentBytes).apply();
        }

        final long oneMinuteUsedBytes = App.preferences.getLong(ONE_MINUTE_USED_BYTES, 0) + receivedBytes + sentBytes;
        App.preferences.edit().putLong(ONE_MINUTE_USED_BYTES, oneMinuteUsedBytes).apply();


        usageLogInterval++;
        if (usageLogInterval == USAGE_LOG_INTERVAL) {
            new Thread(new Runnable() {
                public void run() {
                    App.preferences.edit().putLong(ONE_MINUTE_USED_BYTES, 0).apply();
                    try {
                        UsageLogs.insert(new UsageLog(oneMinuteUsedBytes));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            usageLogInterval = 0;
            Intent intent = new Intent(APPLICATION_ALARM_ACTION);
            //  sendBroadcast(intent);
        }

        String currentDate = Helper.getCurrentDate();
        if (currentDate.equals(App.preferences.getString(TODAY_USAGE_DATE, currentDate))) {
            App.preferences.edit().putLong(TODAY_USAGE_BYTES, App.preferences.getLong(TODAY_USAGE_BYTES, 0) + receivedBytes + sentBytes).apply();
        } else {
            App.preferences.edit().putLong(TODAY_USAGE_BYTES, receivedBytes + sentBytes).apply();
        }
        App.preferences.edit().putString(TODAY_USAGE_DATE, currentDate).apply();

        int iconId = R.drawable.wkb000;
        long sumReceivedSent = (receivedBytes + sentBytes) / 1024;

        if (sumReceivedSent < 1000) {
            iconId = App.context.getResources().getIdentifier("wkb" + String.format("%03d", sumReceivedSent), "drawable", App.context.getPackageName());
        } else if (sumReceivedSent >= 1000 && sumReceivedSent <= 1024) {
            iconId = App.context.getResources().getIdentifier("wmb010", "drawable", App.context.getPackageName());
        } else if ((float) sumReceivedSent / 1024 > 1 && (float) sumReceivedSent / 1024 < 10) {
            BigDecimal decimal = Helper.round((float) sumReceivedSent / 1024, 1);
            iconId = App.context.getResources().getIdentifier("wmb0" + decimal.toString().replace(".", ""), "drawable", App.context.getPackageName());
        } else if (sumReceivedSent / 1024 >= 10 && sumReceivedSent / 1024 <= 200) {
            sumReceivedSent = (sumReceivedSent / 1024) + 90;
            iconId = App.context.getResources().getIdentifier("wmb" + sumReceivedSent, "drawable", App.context.getPackageName());
        } else if (sumReceivedSent / 1024 > 200) {
            sumReceivedSent = (sumReceivedSent / 1024) + 90;
            iconId = App.context.getResources().getIdentifier("wmb" + sumReceivedSent, "drawable", App.context.getPackageName());
        }
    }
}
