package com.zohaltech.app.mobiledatamonitor.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ApplicationAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals(ZTDataService.APPLICATION_ALARM_ACTION)) {

            //ArrayList<AlarmObject> objects = PackageStatus.getCurrentAlarms();
            //Intent intent1 = new Intent(context, ApplicationMessageActivity.class);
            //intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //context.startActivity(intent1);
        }
    }
}
