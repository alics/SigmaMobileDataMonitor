package com.zohaltech.app.mobiledatamonitor.classes;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

//public class AlarmReceiver extends BroadcastReceiver {
//
//    @Override
//    public void onReceive(Context context, Intent arg1) {
//        //Intent intent = new Intent(context, DataUsageUpdateService.class);
//        //context.startService(intent);
//    }
//}

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent arg1) {
        //Log.i("SimpleWakefulReceiver", "Starting service @ " + SystemClock.elapsedRealtime());

        Intent service = new Intent(context, ZtDataService.class);
        startWakefulService(context, service);
    }
}