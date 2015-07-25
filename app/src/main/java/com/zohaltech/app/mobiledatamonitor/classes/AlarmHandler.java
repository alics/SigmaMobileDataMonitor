package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmHandler {

    private static final int INTERVAL = 1000;

    private static AlarmManager  manager;
    private static PendingIntent pendingIntent;

    //static {
    //manager = (AlarmManager) App.context.getSystemService(Context.ALARM_SERVICE);
    //Intent alarmIntent = new Intent(App.context, AlarmReceiver.class);
    //pendingIntent = PendingIntent.getBroadcast(App.context, 0, alarmIntent, 0);
    //}

    public static void start(Context context) {

        Intent intent = new Intent(context,DataUsageUpdateService.class);
        context.startService(intent);

        //Calendar calendar = Calendar.getInstance();
        //calendar.set(Calendar.MONTH, 0);
        //calendar.set(Calendar.YEAR, 0);
        //calendar.set(Calendar.DAY_OF_MONTH, 0);
        //calendar.set(Calendar.HOUR_OF_DAY, 0);
        //calendar.set(Calendar.MINUTE, 0);
        //calendar.set(Calendar.SECOND, 0);
        //calendar.set(Calendar.MILLISECOND, 0);
        //calendar.set(Calendar.AM_PM, Calendar.PM);

        manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        cancel();
        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), INTERVAL, pendingIntent);
        //manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis()+2000, pendingIntent);
    }

    public static void cancel() {
        if (manager != null) {
            manager.cancel(pendingIntent);
            NotificationHandler.cancelNotification(App.context);
        }
    }
}
