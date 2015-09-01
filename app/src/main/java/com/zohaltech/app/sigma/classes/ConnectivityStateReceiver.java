package com.zohaltech.app.sigma.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConnectivityStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectionManager.setDataConnectedStatus();

        if (App.preferences.getBoolean(WebApiClient.SUCCESS_INSTALL, false) == false && ConnectionManager.getInternetStatus() == ConnectionManager.InternetStatus.Connected) {
            WebApiClient.sendUserData(WebApiClient.PostAction.INSTALL);
        }
    }
}