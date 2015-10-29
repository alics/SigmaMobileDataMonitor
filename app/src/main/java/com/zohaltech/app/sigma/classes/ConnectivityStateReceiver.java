package com.zohaltech.app.sigma.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConnectivityStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectionManager.setDataOrWifiConnectedStatus();
        WebApiClient.sendUserData(true);

        //todo : uncomment below lines for app usages
        //if (ConnectionManager.getConnectivityStatus() == ConnectionManager.TYPE_WIFI ||
        //    ConnectionManager.getConnectivityStatus() == ConnectionManager.TYPE_MOBILE) {
        //    //AppDataUsageMeter.runnable.run();
        //    AppDataUsageMeter.takeSnapshot();
        //    App.connectivityType = ConnectionManager.getConnectivityStatus();
        //}

    }
}