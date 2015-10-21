package com.zohaltech.app.sigma.classes;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.dal.Settings;
import com.zohaltech.app.sigma.dal.UsageLogs;
import com.zohaltech.app.sigma.entities.Setting;
import com.zohaltech.app.sigma.entities.UsageLog;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataUsageMeter {
    public static final  String TODAY_USAGE_ACTION         = "TODAY_USAGE_ACTION";
    public static final  String APPLICATION_ALARM_ACTION   = "APPLICATION_ALARM_ACTION";
    public static final  String TODAY_USAGE_BYTES          = "TODAY_USAGE_BYTES";
    public static final  String TODAY_USAGE_BYTES_WIFI     = "TODAY_USAGE_BYTES_WIFI";
    private static final String TODAY_USAGE_DATE           = "TODAY_USAGE_DATE";
    // Mobile
    private static final String LAST_RECEIVED_BYTES        = "LAST_RECEIVED_BYTES";
    private static final String LAST_SENT_BYTES            = "LAST_SENT_BYTES";
    private static final String ONE_MINUTE_USED_BYTES      = "ONE_MINUTE_USED_BYTES";
    // Wifi
    private static final String LAST_RECEIVED_BYTES_WIFI   = "LAST_RECEIVED_BYTES_WIFI";
    private static final String LAST_SENT_BYTES_WIFI       = "LAST_SENT_BYTES_WIFI";
    private static final String ONE_MINUTE_USED_BYTES_WIFI = "ONE_MINUTE_USED_BYTES_WIFI";
    private static final String LAST_RECEIVED_BYTES_TETHER = "LAST_RECEIVED_BYTES_TETHER";
    private static final String LAST_SENT_BYTES_TETHER     = "LAST_SENT_BYTES_TETHER";

    private static final int USAGE_LOG_INTERVAL = 60;
    private static       int usageLogInterval   = 0;
    private static ScheduledExecutorService executorService;
    private boolean firstTimeMobile = true;
    private boolean firstTimeWifi   = true;
    private boolean firstTimeTether = true;
    private Service           service;
    private SharedPreferences preferences;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Mobile
            long currentUsageBytesMobile = android.net.TrafficStats.getMobileRxBytes();
            long currentSentBytesMobile = android.net.TrafficStats.getMobileTxBytes();
            long receivedBytesMobile = 0;
            long sentBytesMobile = 0;


            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                preferences = PreferenceManager.getDefaultSharedPreferences(service);
            } else {
                preferences = service.getSharedPreferences(service.getPackageName() + "_preferences", Context.MODE_MULTI_PROCESS);
            }

            if (currentUsageBytesMobile + currentSentBytesMobile == 0) {
                firstTimeMobile = true;
            } else {
                if (firstTimeMobile) {
                    firstTimeMobile = false;

                    //App.preferences.edit().putLong(LAST_RECEIVED_BYTES, currentUsageBytesMobile).apply();
                    //App.preferences.edit().putLong(LAST_SENT_BYTES, currentSentBytesMobile).apply();
                    preferences.edit().putLong(LAST_RECEIVED_BYTES, currentUsageBytesMobile).apply();
                    preferences.edit().putLong(LAST_SENT_BYTES, currentSentBytesMobile).apply();

                }

                //receivedBytesMobile = currentUsageBytesMobile - App.preferences.getLong(LAST_RECEIVED_BYTES, 0);
                //sentBytesMobile = currentSentBytesMobile - App.preferences.getLong(LAST_SENT_BYTES, 0);
                receivedBytesMobile = currentUsageBytesMobile - preferences.getLong(LAST_RECEIVED_BYTES, 0);
                sentBytesMobile = currentSentBytesMobile - preferences.getLong(LAST_SENT_BYTES, 0);

                if (receivedBytesMobile < 0 || sentBytesMobile < 0) {
                    receivedBytesMobile = receivedBytesMobile >= 0 ? receivedBytesMobile : 0;
                    sentBytesMobile = sentBytesMobile >= 0 ? sentBytesMobile : 0;
                    firstTimeMobile = true;
                }

                //App.preferences.edit().putLong(LAST_RECEIVED_BYTES, currentUsageBytesMobile).apply();
                //App.preferences.edit().putLong(LAST_SENT_BYTES, currentSentBytesMobile).apply();
                preferences.edit().putLong(LAST_RECEIVED_BYTES, currentUsageBytesMobile).apply();
                preferences.edit().putLong(LAST_SENT_BYTES, currentSentBytesMobile).apply();
            }

            //final long oneMinuteUsedBytesMobile = App.preferences.getLong(ONE_MINUTE_USED_BYTES, 0) + receivedBytesMobile + sentBytesMobile;
            //App.preferences.edit().putLong(ONE_MINUTE_USED_BYTES, oneMinuteUsedBytesMobile).apply();
            final long oneMinuteUsedBytesMobile = preferences.getLong(ONE_MINUTE_USED_BYTES, 0) + receivedBytesMobile + sentBytesMobile;
            preferences.edit().putLong(ONE_MINUTE_USED_BYTES, oneMinuteUsedBytesMobile).apply();

            // Wifi And Tether
            long receivedBytesWifi = 0;
            long sentBytesWifi = 0;

            int connectionStatus = ConnectionManager.getConnectivityStatus();
            if (connectionStatus != ConnectionManager.TYPE_WIFI) {
                long currentUsageBytesTether = WifiStats.getTotalBytes(WifiStats.RxBytes);
                long currentSentBytesTether = WifiStats.getTotalBytes(WifiStats.TxBytes);

                if (currentSentBytesTether + currentSentBytesTether == 0) {
                    firstTimeTether = true;
                } else {
                    if (!firstTimeWifi) {
                        //currentUsageBytesTether = WifiStats.getTotalBytes(WifiStats.RxBytes) - App.preferences.getLong(LAST_RECEIVED_BYTES_WIFI, 0);
                        //currentSentBytesTether = WifiStats.getTotalBytes(WifiStats.TxBytes) - App.preferences.getLong(LAST_SENT_BYTES_WIFI, 0);
                        currentUsageBytesTether = WifiStats.getTotalBytes(WifiStats.RxBytes) - preferences.getLong(LAST_RECEIVED_BYTES_WIFI, 0);
                        currentSentBytesTether = WifiStats.getTotalBytes(WifiStats.TxBytes) - preferences.getLong(LAST_SENT_BYTES_WIFI, 0);
                    }

                    firstTimeTether = false;

                    //long receivedBytesTether = currentUsageBytesTether - App.preferences.getLong(LAST_RECEIVED_BYTES_TETHER, 0);
                    //long sentBytesTether = currentSentBytesTether - App.preferences.getLong(LAST_SENT_BYTES_TETHER, 0);
                    long receivedBytesTether = currentUsageBytesTether - preferences.getLong(LAST_RECEIVED_BYTES_TETHER, 0);
                    long sentBytesTether = currentSentBytesTether - preferences.getLong(LAST_SENT_BYTES_TETHER, 0);

                    if (receivedBytesTether < 0 || sentBytesTether < 0) {
                        firstTimeTether = true;
                    }

                    //App.preferences.edit().putLong(LAST_RECEIVED_BYTES_TETHER, currentUsageBytesTether).apply();
                    //App.preferences.edit().putLong(LAST_SENT_BYTES_TETHER, currentSentBytesTether).apply();
                    preferences.edit().putLong(LAST_RECEIVED_BYTES_TETHER, currentUsageBytesTether).apply();
                    preferences.edit().putLong(LAST_SENT_BYTES_TETHER, currentSentBytesTether).apply();
                }
            } else {
                long currentUsageBytesWifi = WifiStats.getTotalBytes(WifiStats.RxBytes);
                long currentSentBytesWifi = WifiStats.getTotalBytes(WifiStats.TxBytes);

                if (currentUsageBytesWifi + currentSentBytesWifi == 0) {
                    firstTimeWifi = true;
                } else {
                    if (!firstTimeTether) {
                        //currentUsageBytesWifi -= App.preferences.getLong(LAST_RECEIVED_BYTES_TETHER, 0);
                        //currentSentBytesWifi -= App.preferences.getLong(LAST_SENT_BYTES_TETHER, 0);
                        currentUsageBytesWifi -= preferences.getLong(LAST_RECEIVED_BYTES_TETHER, 0);
                        currentSentBytesWifi -= preferences.getLong(LAST_SENT_BYTES_TETHER, 0);
                    }

                    if (firstTimeWifi) {
                        firstTimeWifi = false;

                        //App.preferences.edit().putLong(LAST_RECEIVED_BYTES_WIFI, currentUsageBytesWifi).apply();
                        //App.preferences.edit().putLong(LAST_SENT_BYTES_WIFI, currentSentBytesWifi).apply();
                        preferences.edit().putLong(LAST_RECEIVED_BYTES_WIFI, currentUsageBytesWifi).apply();
                        preferences.edit().putLong(LAST_SENT_BYTES_WIFI, currentSentBytesWifi).apply();
                    }

                    //receivedBytesWifi = currentUsageBytesWifi - App.preferences.getLong(LAST_RECEIVED_BYTES_WIFI, 0);
                    //sentBytesWifi = currentSentBytesWifi - App.preferences.getLong(LAST_SENT_BYTES_WIFI, 0);
                    receivedBytesWifi = currentUsageBytesWifi - preferences.getLong(LAST_RECEIVED_BYTES_WIFI, 0);
                    sentBytesWifi = currentSentBytesWifi - preferences.getLong(LAST_SENT_BYTES_WIFI, 0);

                    if (receivedBytesWifi < 0 || sentBytesWifi < 0) {
                        receivedBytesWifi = receivedBytesWifi >= 0 ? receivedBytesWifi : 0;
                        sentBytesWifi = sentBytesWifi >= 0 ? sentBytesWifi : 0;
                        firstTimeWifi = true;
                    }

                    //App.preferences.edit().putLong(LAST_RECEIVED_BYTES_WIFI, currentUsageBytesWifi).apply();
                    //App.preferences.edit().putLong(LAST_SENT_BYTES_WIFI, currentSentBytesWifi).apply();
                    preferences.edit().putLong(LAST_RECEIVED_BYTES_WIFI, currentUsageBytesWifi).apply();
                    preferences.edit().putLong(LAST_SENT_BYTES_WIFI, currentSentBytesWifi).apply();
                }
            }

            //final long oneMinuteUsedBytesWifi = App.preferences.getLong(ONE_MINUTE_USED_BYTES_WIFI, 0) + receivedBytesWifi + sentBytesWifi;
            //App.preferences.edit().putLong(ONE_MINUTE_USED_BYTES_WIFI, oneMinuteUsedBytesWifi).apply();
            final long oneMinuteUsedBytesWifi = preferences.getLong(ONE_MINUTE_USED_BYTES_WIFI, 0) + receivedBytesWifi + sentBytesWifi;
            preferences.edit().putLong(ONE_MINUTE_USED_BYTES_WIFI, oneMinuteUsedBytesWifi).apply();

            usageLogInterval++;
            if (usageLogInterval == USAGE_LOG_INTERVAL) {
                new Thread(new Runnable() {
                    public void run() {
                        //App.preferences.edit().putLong(ONE_MINUTE_USED_BYTES, 0).apply();
                        //App.preferences.edit().putLong(ONE_MINUTE_USED_BYTES_WIFI, 0).apply();
                        preferences.edit().putLong(ONE_MINUTE_USED_BYTES, 0).apply();
                        preferences.edit().putLong(ONE_MINUTE_USED_BYTES_WIFI, 0).apply();
                        try {
                            UsageLogs.insert(new UsageLog(oneMinuteUsedBytesMobile, oneMinuteUsedBytesWifi));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                usageLogInterval = 0;
                Intent intent = new Intent(APPLICATION_ALARM_ACTION);
                service.sendBroadcast(intent);
            }

            String currentDate = Helper.getCurrentDate();
            //if (currentDate.equals(App.preferences.getString(TODAY_USAGE_DATE, currentDate))) {
            //    App.preferences.edit().putLong(TODAY_USAGE_BYTES, App.preferences.getLong(TODAY_USAGE_BYTES, 0) + receivedBytesMobile + sentBytesMobile).apply();
            //    App.preferences.edit().putLong(TODAY_USAGE_BYTES_WIFI, App.preferences.getLong(TODAY_USAGE_BYTES_WIFI, 0) + receivedBytesWifi + sentBytesWifi).apply();
            //} else {
            //    App.preferences.edit().putLong(TODAY_USAGE_BYTES, receivedBytesMobile + sentBytesMobile).apply();
            //    App.preferences.edit().putLong(TODAY_USAGE_BYTES_WIFI, receivedBytesWifi + sentBytesWifi).apply();
            //}
            //App.preferences.edit().putString(TODAY_USAGE_DATE, currentDate).apply();
            if (currentDate.equals(preferences.getString(TODAY_USAGE_DATE, currentDate))) {
                preferences.edit().putLong(TODAY_USAGE_BYTES, preferences.getLong(TODAY_USAGE_BYTES, 0) + receivedBytesMobile + sentBytesMobile).apply();
                preferences.edit().putLong(TODAY_USAGE_BYTES_WIFI, preferences.getLong(TODAY_USAGE_BYTES_WIFI, 0) + receivedBytesWifi + sentBytesWifi).apply();
            } else {
                preferences.edit().putLong(TODAY_USAGE_BYTES, receivedBytesMobile + sentBytesMobile).apply();
                preferences.edit().putLong(TODAY_USAGE_BYTES_WIFI, receivedBytesWifi + sentBytesWifi).apply();
            }
            preferences.edit().putString(TODAY_USAGE_DATE, currentDate).apply();

            Setting setting = Settings.getCurrentSettings();

            Boolean showWifi = setting.getShowWifiUsage();
            long sumReceivedSent = (receivedBytesMobile + sentBytesMobile + (showWifi ? receivedBytesWifi + sentBytesWifi : 0)) / 1024;

            int iconId = R.drawable.wkb000;
            if (sumReceivedSent < 1000) {
                iconId = App.context.getResources().getIdentifier("wkb" + String.format("%03d", sumReceivedSent), "drawable", service.getPackageName());
            } else if (sumReceivedSent >= 1000 && sumReceivedSent <= 1024) {
                iconId = App.context.getResources().getIdentifier("wmb010", "drawable", service.getPackageName());
            } else if ((float) sumReceivedSent / 1024 > 1 && (float) sumReceivedSent / 1024 < 10) {
                BigDecimal decimal = Helper.round((float) sumReceivedSent / 1024, 1);
                iconId = App.context.getResources().getIdentifier("wmb0" + decimal.toString().replace(".", ""), "drawable", service.getPackageName());
            } else if (sumReceivedSent / 1024 >= 10 && sumReceivedSent / 1024 <= 200) {
                sumReceivedSent = (sumReceivedSent / 1024) + 90;
                iconId = App.context.getResources().getIdentifier("wmb" + sumReceivedSent, "drawable", service.getPackageName());
            } else if (sumReceivedSent / 1024 > 200) {
                sumReceivedSent = (sumReceivedSent / 1024) + 90;
                iconId = App.context.getResources().getIdentifier("wmb" + sumReceivedSent, "drawable", service.getPackageName());
            }

            //String todayUsage = "Mobile: " + TrafficUnitsUtil.getUsedTrafficWithPoint(App.preferences.getLong(TODAY_USAGE_BYTES, 0)) +
            //                    (showWifi ? "   WiFi: " + TrafficUnitsUtil.getUsedTrafficWithPoint(App.preferences.getLong(TODAY_USAGE_BYTES_WIFI, 0)) : "");
            String todayUsage = "Mobile: " + TrafficUnitsUtil.getUsedTrafficWithPoint(preferences.getLong(TODAY_USAGE_BYTES, 0)) +
                                (showWifi ? "   WiFi: " + TrafficUnitsUtil.getUsedTrafficWithPoint(preferences.getLong(TODAY_USAGE_BYTES_WIFI, 0)) : "");

            boolean showNotification;
            if (setting.getShowNotification()) {
                if (setting.getShowNotificationWhenDataOrWifiIsOn()) {
                    showNotification = setting.getDataConnected();
                } else {
                    showNotification = true;
                }
            } else {
                showNotification = false;
            }

            String title = service.getString(R.string.speed) +
                           TrafficUnitsUtil.getTransferRate(showWifi ? receivedBytesWifi + sentBytesWifi + receivedBytesMobile + sentBytesMobile : receivedBytesMobile + sentBytesMobile);
            if (setting.getShowUpDownSpeed()) {
                title = service.getString(R.string.down) + TrafficUnitsUtil.getTransferRate(showWifi ? receivedBytesWifi + receivedBytesMobile : receivedBytesMobile)
                        + service.getString(R.string.up) + TrafficUnitsUtil.getTransferRate(showWifi ? sentBytesWifi + sentBytesMobile : sentBytesMobile);
            }

            if (showNotification) {
                service.startForeground(1, NotificationHandler.getDataUsageNotification(service,
                                                                                        iconId,
                                                                                        title,
                                                                                        todayUsage));
            } else {
                service.stopForeground(true);
            }

            Intent intent = new Intent(TODAY_USAGE_ACTION);
            //intent.putExtra(TODAY_USAGE_BYTES, App.preferences.getLong(TODAY_USAGE_BYTES, 0));
            //intent.putExtra(TODAY_USAGE_BYTES_WIFI, App.preferences.getLong(TODAY_USAGE_BYTES_WIFI, 0));
            intent.putExtra(TODAY_USAGE_BYTES, preferences.getLong(TODAY_USAGE_BYTES, 0));
            intent.putExtra(TODAY_USAGE_BYTES_WIFI, preferences.getLong(TODAY_USAGE_BYTES_WIFI, 0));
            service.sendBroadcast(intent);
        }
    };

    public DataUsageMeter(Service service) {
        this.service = service;
    }

    public void execute() {
        if (executorService == null) {
            executorService = Executors.newSingleThreadScheduledExecutor();
        }
        executorService.scheduleAtFixedRate(runnable, 0L, 1000L, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        if (executorService != null) {
            executorService.shutdown();
            executorService = null;
            NotificationHandler.cancelNotification(1);
        }
    }
}
