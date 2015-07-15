package com.zohaltech.app.mobiledatamonitor.classes;

/**
 * Created by Ali on 7/15/2015.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent arg1) {
        Intent intent = new Intent(context, DataUsageUpdateService.class);
        context.startService(intent);
    }
}