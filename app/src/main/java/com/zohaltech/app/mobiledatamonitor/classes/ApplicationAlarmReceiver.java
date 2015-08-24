package com.zohaltech.app.mobiledatamonitor.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zohaltech.app.mobiledatamonitor.activities.ApplicationMessageActivity;

import java.util.ArrayList;

public class ApplicationAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals(ZtDataService.APPLICATION_ALARM_ACTION)) {

            ArrayList<AlarmObject> objects = PackageStatus.getCurrentAlarms();
//            Intent intent1 = new Intent(context, ApplicationMessageActivity.class);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent1);
        }
    }
}
