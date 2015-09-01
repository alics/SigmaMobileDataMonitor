package com.zohaltech.app.sigma.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.activities.ApplicationAlarmActivity;
import com.zohaltech.app.sigma.dal.Settings;
import com.zohaltech.app.sigma.entities.Setting;

import java.util.ArrayList;

public class PackageAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (intent.getAction().equals(SigmaDataService.APPLICATION_ALARM_ACTION)) {

            ArrayList<AlarmObject> objects = PackageStatus.getCurrentAlarms();

            Setting setting = Settings.getCurrentSettings();
            for (AlarmObject object : objects) {
                if (object.alarmType == AlarmObject.AlarmType.REMINDED_DAYS_ALARM) {
                    if (setting.getLeftDaysAlarmHasShown() == false) {
                        NotificationHandler.displayAlarmNotification(context, 2, "هشدار " + context.getResources().getString(R.string.app_name), object.getAlarmMessage());
                        setting.setLeftDaysAlarmHasShown(true);
                        Settings.update(setting);
                    }
                } else if (object.alarmType == AlarmObject.AlarmType.REMINDED_TRAFFIC_ALARM) {
                    if (setting.getTrafficAlarmHasShown() == false) {
                        NotificationHandler.displayAlarmNotification(context, 2, "هشدار " + context.getResources().getString(R.string.app_name), object.getAlarmMessage());
                        setting.setTrafficAlarmHasShown(true);
                        Settings.update(setting);
                    }
                } else if (object.alarmType == AlarmObject.AlarmType.FINISH_SECONDARY_TRAFFIC_ALARM) {
                    if (setting.getSecondaryTrafficAlarmHasShown() == false) {
                        NotificationHandler.displayAlarmNotification(context, 2, "هشدار " + context.getResources().getString(R.string.app_name), object.getAlarmMessage());
                        setting.setSecondaryTrafficAlarmHasShown(true);
                        Settings.update(setting);
                    }
                } else if (object.alarmType == AlarmObject.AlarmType.FINISH_TRAFFIC_ALARM ||
                           object.alarmType == AlarmObject.AlarmType.FINISH_VALIDATION_DATE_ALARM) {
                    if (setting.getShowAlarmAfterTerminate()) {
                        Intent intent1 = new Intent(context, ApplicationAlarmActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent1.putExtra(ApplicationAlarmActivity.MESSAGES_KEY, object.getAlarmMessage());
                        context.startActivity(intent1);
                    }
                }
            }
        }
    }
}
