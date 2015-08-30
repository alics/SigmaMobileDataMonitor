package com.zohaltech.app.sigma.classes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.activities.DashboardActivity;
import com.zohaltech.app.sigma.dal.Settings;

public class NotificationHandler {

    private static NotificationManager notificationManager;

    static {
        notificationManager = (NotificationManager) App.context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static void displayAlarmNotification(Context context, int notificationId, String title, String text) {
        notificationManager.notify(notificationId, getAlarmNotification(context, title, text));
    }

    public static void cancelNotification(int notificationId) {
        notificationManager.cancel(notificationId);
    }

    public static Notification getDataUsageNotification(Context context, int iconId, String title, String text) {
        int priority = NotificationCompat.PRIORITY_HIGH;
        int visibility = NotificationCompat.VISIBILITY_PUBLIC;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (!Settings.getCurrentSettings().getShowNotificationInLockScreen()) {
                if (App.keyguardManager.isKeyguardLocked()) {
                    priority = NotificationCompat.PRIORITY_MIN;
                    visibility = NotificationCompat.VISIBILITY_SECRET;
                }
            }
        }
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(iconId)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setShowWhen(false)
                        .setOngoing(true)
                        .setPriority(priority)
                        .setVisibility(visibility)
                        .setColor(App.context.getResources().getColor(R.color.primary))
                        .setAutoCancel(false);

        Intent resultIntent = new Intent(context, DashboardActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        return builder.build();
    }

    private static Notification getAlarmNotification(Context context, String title, String text) {
        int lockScreenVisibility;
        if (Settings.getCurrentSettings().getShowNotificationInLockScreen()) {
            lockScreenVisibility = NotificationCompat.VISIBILITY_PUBLIC;//visible in lock screen
        } else {
            lockScreenVisibility = NotificationCompat.VISIBILITY_SECRET;//invisible in lock screen
        }
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_warning_white)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                        .setContentTitle(title)
                        .setContentText(text)
                        .setShowWhen(true)
                        .setOngoing(false)
                        //.setVibrate({10L,20L})
                        //.setSound()
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setVisibility(lockScreenVisibility)
                        .setColor(App.context.getResources().getColor(R.color.primary))
                        .setAutoCancel(true);

        Intent resultIntent = new Intent(context, DashboardActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        return builder.build();
    }
}