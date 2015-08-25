package com.zohaltech.app.mobiledatamonitor.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zohaltech.app.mobiledatamonitor.dal.Settings;
import com.zohaltech.app.mobiledatamonitor.entities.Setting;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
                Intent service = new Intent(context, ZtDataService.class);
                context.startService(service);
                AlarmHandler.start(context);
    }
}