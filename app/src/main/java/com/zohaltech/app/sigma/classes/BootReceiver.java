package com.zohaltech.app.sigma.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
                Intent service = new Intent(context, SigmaDataService.class);
                context.startService(service);
                AlarmHandler.start(context);
    }
}