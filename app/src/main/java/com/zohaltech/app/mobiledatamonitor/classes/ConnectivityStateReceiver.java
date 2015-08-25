package com.zohaltech.app.mobiledatamonitor.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConnectivityStateReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, ZtDataService.class);
        context.stopService(service);
        ConnectionManager.setDataConnectionStatus();
        context.startService(service);
    }
}