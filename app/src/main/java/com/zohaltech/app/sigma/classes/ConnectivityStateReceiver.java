package com.zohaltech.app.sigma.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zohaltech.app.sigma.dal.SnapshotStatus;

public class ConnectivityStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectionManager.setDataOrWifiConnectedStatus();
        WebApiClient.sendUserData(true);

        //todo : uncomment below lines for app usages
        if (ConnectionManager.getConnectivityStatus() == ConnectionManager.TYPE_NOT_CONNECTED) {
            SnapshotStatus status = SnapshotStatus.getCurrentSnapshotStatus();
            if (status.getStatus() != SnapshotStatus.Running) {
                AppDataUsageMeter.takeSnapshot();
            }
        }
    }
}