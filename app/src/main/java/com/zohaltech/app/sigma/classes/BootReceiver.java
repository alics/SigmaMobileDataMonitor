package com.zohaltech.app.sigma.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent dataService = new Intent(context, SigmaDataService.class);
        context.startService(dataService);
        AlarmHandler.start(context);

        //Intent appsService = new Intent(context, SigmaAppsService.class);
        //context.startService(appsService);
    }
}