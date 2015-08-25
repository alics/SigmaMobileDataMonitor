package com.zohaltech.app.mobiledatamonitor.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zohaltech.app.mobiledatamonitor.dal.Settings;
import com.zohaltech.app.mobiledatamonitor.entities.Setting;

public class ConnectivityStateReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Setting setting = Settings.getCurrentSettings();
        if (ConnectionManager.getConnectivityStatus() == ConnectionManager.TYPE_MOBILE) {
            setting.setDataConnected(true);
        } else {
            setting.setDataConnected(false);
        }
        Settings.update(setting);

        if (setting.getShowNotificationWhenDataIsOn()) {
            ZtDataService.restart(context);
        }
    }
}