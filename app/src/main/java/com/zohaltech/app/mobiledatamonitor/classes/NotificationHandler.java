package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.DashboardActivity;
import com.zohaltech.app.mobiledatamonitor.activities.MainActivity;

public class NotificationHandler {

    private static NotificationManager notificationManager;

    static {
        notificationManager = (NotificationManager) App.context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public static void displayAlarmNotification(Context context, int notificationId, int iconId, String title, String text, boolean showWhen, boolean ongoing, int priority, int visibility) {
        notificationManager.notify(notificationId, getDataUsageNotification(context, iconId, title, text));
    }

    public static void cancelNotification(int notificationId) {
        notificationManager.cancel(notificationId);
    }

    //public static Notification getDataUsageNotification(Context context, int iconId, String title, String text, String subText) {
    public static Notification getDataUsageNotification(Context context, int iconId, String title, String text) {
        //if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
        //            Notification notification;
        //            PendingIntent resultPendingIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, DashboardActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        //            notification = new Notification(iconId, title, System.currentTimeMillis());
        //            notification.setLatestEventInfo(context, title, text, resultPendingIntent);
        //            notification.flags = Notification.FLAG_ONGOING_EVENT;
        //            notificationManager.notify(NOTIFICATION_ID, notification);
        //        } else {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(iconId)
                                //.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), iconId))
                        .setContentTitle(title)
                        .setContentText(text)
                                //.setSubText(subText)
                                //.setVisibility(NotificationCompat.VISIBILITY_SECRET) //invisible in lock screen
                        .setShowWhen(false)
                        .setOngoing(true)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) //visible in lock screen
                        .setColor(App.context.getResources().getColor(R.color.primary))
                        .setAutoCancel(false);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, DashboardActivity.class);
        //todo
        //resultIntent.putExtra("NOTIFIED", true);

        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        //// The stack builder object will contain an artificial back stack for the
        //// started Activity.
        //// This ensures that navigating backward from the Activity leads out of
        //// your application to the Home screen.
        //TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        //// Adds the back stack for the Intent (but not the Intent itself)
        //stackBuilder.addParentStack(MainActivity.class);
        //// Adds the Intent that starts the Activity to the top of the stack
        //stackBuilder.addNextIntent(resultIntent);
        //PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        return builder.build();
    }
}