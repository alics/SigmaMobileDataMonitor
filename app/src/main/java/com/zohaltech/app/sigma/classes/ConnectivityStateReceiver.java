package com.zohaltech.app.sigma.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConnectivityStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectionManager.setDataOrWifiConnectedStatus();
        WebApiClient.sendUserData(WebApiClient.PostAction.INSTALL);
        App.connectivityType=ConnectionManager.getConnectivityStatus();
        //AppDataUsageMeter.runnable.run();
    }
}