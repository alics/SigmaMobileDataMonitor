package com.zohaltech.app.sigma.classes;

import android.app.Service;
import android.content.Intent;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.dal.Settings;
import com.zohaltech.app.sigma.dal.UsageLogs;
import com.zohaltech.app.sigma.entities.Setting;
import com.zohaltech.app.sigma.entities.UsageLog;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataUsageMeter
{
    public static final String TODAY_USAGE_ACTION = "TODAY_USAGE_ACTION";
    public static final String APPLICATION_ALARM_ACTION = "APPLICATION_ALARM_ACTION";
    private static final String TODAY_USAGE_DATE = "TODAY_USAGE_DATE";

    // Mobile
    private static final String LAST_RECEIVED_BYTES = "LAST_RECEIVED_BYTES";
    private static final String LAST_SENT_BYTES = "LAST_SENT_BYTES";
    private static final String ONE_MINUTE_USED_BYTES = "ONE_MINUTE_USED_BYTES";
    public static final String TODAY_USAGE_BYTES = "TODAY_USAGE_BYTES";

    // Wifi
    private static final String LAST_RECEIVED_BYTES_WIFI = "LAST_RECEIVED_BYTES_WIFI";
    private static final String LAST_SENT_BYTES_WIFI = "LAST_SENT_BYTES_WIFI";
    private static final String ONE_MINUTE_USED_BYTES_WIFI = "ONE_MINUTE_USED_BYTES_WIFI";
    public static final String TODAY_USAGE_BYTES_WIFI = "TODAY_USAGE_BYTES_WIFI";

    private static final int USAGE_LOG_INTERVAL = 60;
    private static int usageLogInterval = 0;
    private static ScheduledExecutorService executorService;
    private boolean firstTime = true;
    private Service service;
    private Runnable runnable = new Runnable()
    {
        @Override
        public void run()
        {
            // Mobile
            long currentUsageBytesMobile = android.net.TrafficStats.getMobileRxBytes();
            long currentSentBytesMobile = android.net.TrafficStats.getMobileTxBytes();
            long receivedBytesMobile = 0;
            long sentBytesMobile = 0;

            // Wifi
            long currentUsageBytesWifi = android.net.TrafficStats.getTotalRxBytes() - currentSentBytesMobile;
            long currentSentBytesWifi = android.net.TrafficStats.getTotalTxBytes() - currentSentBytesMobile;
            long receivedBytesWifi = 0;
            long sentBytesWifi = 0;

            if (currentUsageBytesMobile + currentSentBytesMobile == 0)
            {
                firstTime = true;
            }
            else
            {
                if (firstTime)
                {
                    firstTime = false;
                    App.preferences.edit().putLong(LAST_RECEIVED_BYTES, currentUsageBytesMobile).apply();
                    App.preferences.edit().putLong(LAST_SENT_BYTES, currentSentBytesMobile).apply();

                    App.preferences.edit().putLong(LAST_RECEIVED_BYTES_WIFI, currentUsageBytesWifi).apply();
                    App.preferences.edit().putLong(LAST_SENT_BYTES_WIFI, currentSentBytesWifi).apply();
                }

                receivedBytesMobile = currentUsageBytesMobile - App.preferences.getLong(LAST_RECEIVED_BYTES, 0);
                sentBytesMobile = currentSentBytesMobile - App.preferences.getLong(LAST_SENT_BYTES, 0);

                receivedBytesWifi = currentUsageBytesWifi - App.preferences.getLong(LAST_RECEIVED_BYTES_WIFI, 0);
                sentBytesWifi = currentSentBytesWifi - App.preferences.getLong(LAST_SENT_BYTES_WIFI, 0);

                if (receivedBytesMobile < 0 || sentBytesMobile < 0)
                {
                    receivedBytesMobile = receivedBytesMobile >= 0 ? receivedBytesMobile : 0;
                    sentBytesMobile = sentBytesMobile >= 0 ? sentBytesMobile : 0;
                    firstTime = true;
                }

                App.preferences.edit().putLong(LAST_RECEIVED_BYTES, currentUsageBytesMobile).apply();
                App.preferences.edit().putLong(LAST_SENT_BYTES, currentSentBytesMobile).apply();

                App.preferences.edit().putLong(LAST_RECEIVED_BYTES_WIFI, currentUsageBytesWifi).apply();
                App.preferences.edit().putLong(LAST_SENT_BYTES_WIFI, currentSentBytesWifi).apply();
            }

            final long oneMinuteUsedBytes = App.preferences.getLong(ONE_MINUTE_USED_BYTES, 0) + receivedBytesMobile + sentBytesMobile;
            App.preferences.edit().putLong(ONE_MINUTE_USED_BYTES, oneMinuteUsedBytes).apply();

            final long oneMinuteUsedBytesWifi = App.preferences.getLong(ONE_MINUTE_USED_BYTES_WIFI, 0) + receivedBytesWifi + sentBytesWifi;
            App.preferences.edit().putLong(ONE_MINUTE_USED_BYTES_WIFI, oneMinuteUsedBytesWifi).apply();

            usageLogInterval++;
            if (usageLogInterval == USAGE_LOG_INTERVAL)
            {
                new Thread(new Runnable()
                {
                    public void run()
                    {
                        App.preferences.edit().putLong(ONE_MINUTE_USED_BYTES, 0).apply();
                        App.preferences.edit().putLong(ONE_MINUTE_USED_BYTES_WIFI, 0).apply();
                        try
                        {
                            UsageLogs.insert(new UsageLog(oneMinuteUsedBytes, oneMinuteUsedBytesWifi));
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
                usageLogInterval = 0;
                Intent intent = new Intent(APPLICATION_ALARM_ACTION);
                service.sendBroadcast(intent);
            }

            String currentDate = Helper.getCurrentDate();
            if (currentDate.equals(App.preferences.getString(TODAY_USAGE_DATE, currentDate)))
            {
                App.preferences.edit().putLong(TODAY_USAGE_BYTES, App.preferences.getLong(TODAY_USAGE_BYTES, 0) + receivedBytesMobile + sentBytesMobile).apply();
                App.preferences.edit().putLong(TODAY_USAGE_BYTES_WIFI, App.preferences.getLong(TODAY_USAGE_BYTES_WIFI, 0) + receivedBytesWifi + sentBytesWifi).apply();
            }
            else
            {
                App.preferences.edit().putLong(TODAY_USAGE_BYTES, receivedBytesMobile + sentBytesMobile).apply();
                App.preferences.edit().putLong(TODAY_USAGE_BYTES_WIFI, receivedBytesWifi + sentBytesWifi).apply();
            }

            App.preferences.edit().putString(TODAY_USAGE_DATE, currentDate).apply();

            Boolean isWifi = ConnectionManager.getConnectivityStatus() == ConnectionManager.TYPE_WIFI;
            long sumReceivedSent = isWifi ? (receivedBytesWifi + sentBytesWifi) / 1024 : (receivedBytesMobile + sentBytesMobile) / 1024;

            int iconId = R.drawable.wkb000;
            if (sumReceivedSent < 1000)
            {
                iconId = App.context.getResources().getIdentifier("wkb" + String.format("%03d", sumReceivedSent), "drawable", service.getPackageName());
            }
            else if (sumReceivedSent >= 1000 && sumReceivedSent <= 1024)
            {
                iconId = App.context.getResources().getIdentifier("wmb010", "drawable", service.getPackageName());
            }
            else if ((float) sumReceivedSent / 1024 > 1 && (float) sumReceivedSent / 1024 < 10)
            {
                BigDecimal decimal = Helper.round((float) sumReceivedSent / 1024, 1);
                iconId = App.context.getResources().getIdentifier("wmb0" + decimal.toString().replace(".", ""), "drawable", service.getPackageName());
            }
            else if (sumReceivedSent / 1024 >= 10 && sumReceivedSent / 1024 <= 200)
            {
                sumReceivedSent = (sumReceivedSent / 1024) + 90;
                iconId = App.context.getResources().getIdentifier("wmb" + sumReceivedSent, "drawable", service.getPackageName());
            }
            else if (sumReceivedSent / 1024 > 200)
            {
                sumReceivedSent = (sumReceivedSent / 1024) + 90;
                iconId = App.context.getResources().getIdentifier("wmb" + sumReceivedSent, "drawable", service.getPackageName());
            }

            String todayUsage = "Mobile: " + TrafficUnitsUtil.getUsedTrafficWithPoint(App.preferences.getLong(TODAY_USAGE_BYTES, 0)) +
                    "   Wifi: " + TrafficUnitsUtil.getUsedTrafficWithPoint(App.preferences.getLong(TODAY_USAGE_BYTES_WIFI, 0));

            Setting setting = Settings.getCurrentSettings();
            boolean showNotification;
            if (setting.getShowNotification())
            {
                if (setting.getShowNotificationWhenDataIsOn())
                {
                    showNotification = setting.getDataConnected();
                }
                else
                {
                    showNotification = true;
                }
            }
            else
            {
                showNotification = false;
            }

            String title = service.getString(R.string.speed) + TrafficUnitsUtil.getTransferRate(isWifi ? receivedBytesWifi + sentBytesWifi : receivedBytesMobile + sentBytesMobile);
            if (setting.getShowUpDownSpeed())
            {
                title = service.getString(R.string.down) + TrafficUnitsUtil.getTransferRate(isWifi ? receivedBytesWifi : receivedBytesMobile)
                        + service.getString(R.string.up) + TrafficUnitsUtil.getTransferRate(isWifi ? sentBytesWifi : sentBytesMobile);
            }

            if (showNotification)
            {
                service.startForeground(1, NotificationHandler.getDataUsageNotification(service,
                        iconId,
                        title,
                        todayUsage));
            }
            else
            {
                service.stopForeground(true);
            }

            Intent intent = new Intent(TODAY_USAGE_ACTION);
            intent.putExtra(TODAY_USAGE_BYTES, App.preferences.getLong(TODAY_USAGE_BYTES, 0));
            intent.putExtra(TODAY_USAGE_BYTES_WIFI, App.preferences.getLong(TODAY_USAGE_BYTES_WIFI, 0));
            service.sendBroadcast(intent);
        }
    };

    public DataUsageMeter(Service service)
    {
        this.service = service;
    }

    public void execute()
    {
        if (executorService == null)
        {
            executorService = Executors.newSingleThreadScheduledExecutor();
        }
        executorService.scheduleAtFixedRate(runnable, 0L, 1000L, TimeUnit.MILLISECONDS);
    }

    public void shutdown()
    {
        if (executorService != null)
        {
            executorService.shutdown();
            executorService = null;
            NotificationHandler.cancelNotification(1);
        }
    }
}