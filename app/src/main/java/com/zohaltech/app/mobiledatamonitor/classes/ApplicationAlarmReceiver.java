package com.zohaltech.app.mobiledatamonitor.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.ApplicationMessageActivity;
import com.zohaltech.app.mobiledatamonitor.dal.Settings;
import com.zohaltech.app.mobiledatamonitor.entities.Setting;

import java.util.ArrayList;

public class ApplicationAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals(ZtDataService.APPLICATION_ALARM_ACTION)) {

            ArrayList<AlarmObject> objects = PackageStatus.getCurrentAlarms();


            for (AlarmObject object : objects) {
                if (object.alarmType == AlarmObject.AlarmType.REMINDED_DAYS_ALARM ||
                    object.alarmType == AlarmObject.AlarmType.REMINDED_TRAFFIC_ALARM ||
                    object.alarmType == AlarmObject.AlarmType.FINISH_SECONDARY_TRAFFIC_ALARM) {
                    NotificationHandler.displayAlarmNotification(context, 2, "اخطار " + context.getResources().getString(R.string.app_name), object.getAlarmMessage());
                    Setting setting = Settings.getCurrentSettings();
                    //todo update setting
                } else if (object.alarmType == AlarmObject.AlarmType.FINISH_TRAFFIC_ALARM ||
                           object.alarmType == AlarmObject.AlarmType.FINISH_VALIDATION_DATE_ALARM) {
                    Intent intent1 = new Intent(context, ApplicationMessageActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.putExtra(ApplicationMessageActivity.MESSAGES_KEY, object.getAlarmMessage());
                    context.startActivity(intent1);
                }
            }
        }
    }
}
