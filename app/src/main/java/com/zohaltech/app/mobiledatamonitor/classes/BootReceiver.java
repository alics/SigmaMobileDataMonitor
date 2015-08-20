package com.zohaltech.app.mobiledatamonitor.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
            if (SettingsHandler.isMonitoringServiceActive()) {

                AlarmHandler.start(context);

                Intent service = new Intent(context, ZtDataService.class);
                context.startService(service);
            }
    }
}