package com.zohaltech.app.datausagemonitor;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private PendingIntent pendingIntent;
    private AlarmManager  manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log.e("bytes recvd", "" + android.net.TrafficStats.getMobileRxBytes());
        //
        //Log.e("Total", "Bytes received" + android.net.TrafficStats.getTotalRxBytes());

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        //displayNotification();
    }

    public void startMonitoring(View view) {
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000;

        manager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        Toast.makeText(this, "Monitoring has been started!", Toast.LENGTH_SHORT).show();
    }

    public void stopMonitoring(View view) {
        if (manager != null) {
            manager.cancel(pendingIntent);
            NotificationHandler.cancelNotification(this);
            Toast.makeText(this, "Monitoring has been stopped!", Toast.LENGTH_SHORT).show();
        }
    }
}
