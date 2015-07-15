package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class AlarmHandler {

    private static final int INTERVAL = 1000;

    private static AlarmManager  manager;
    private static PendingIntent pendingIntent;

    static {
        manager = (AlarmManager) App.context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(App.context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(App.context, 0, alarmIntent, 0);
    }

    public static void start(Context context) {
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), INTERVAL, pendingIntent);
    }

    public static void cancel() {
        if (manager != null) {
            manager.cancel(pendingIntent);
            NotificationHandler.cancelNotification(App.context);
        }
    }
}
