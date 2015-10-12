package com.zohaltech.app.sigma.classes;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent arg1) {
        Intent dataService = new Intent(context, SigmaDataService.class);
        startWakefulService(context, dataService);

        Intent appsService = new Intent(context, SigmaAppsService.class);
        startWakefulService(context, appsService);
    }
}