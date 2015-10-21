package com.zohaltech.app.sigma.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConnectivityStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectionManager.setDataOrWifiConnectedStatus();
        WebApiClient.sendUserData(WebApiClient.PostAction.INSTALL, null);

        //if (ConnectionManager.getConnectivityStatus() == ConnectionManager.TYPE_WIFI ||
        //    ConnectionManager.getConnectivityStatus() == ConnectionManager.TYPE_MOBILE) {
        //    //todo : moshkel darad, Sigma is running in notification
        //    //AppDataUsageMeter.runnable.run();
        //    AppDataUsageMeter.takeSnapshot();
        //    App.connectivityType = ConnectionManager.getConnectivityStatus();
        //}
    }
}