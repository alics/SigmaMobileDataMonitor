package com.zohaltech.app.sigma.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import widgets.MyToast;

public class AppListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            MyToast.show("ACTION_PACKAGE_REPLACED", Toast.LENGTH_LONG);
        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {

            Bundle b = intent.getExtras();
            int uid = b.getInt(Intent.EXTRA_UID);
            String[] packages = context.getPackageManager().getPackagesForUid(uid);

            MyToast.show("ACTION_PACKAGE_ADDED"+packages[1], Toast.LENGTH_LONG);
        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            Bundle b = intent.getExtras();
            int uid = b.getInt(Intent.EXTRA_UID);

            String[] packages = context.getPackageManager().getPackagesForUid(uid);

            MyToast.show("ACTION_PACKAGE_ADDED"+packages[0], Toast.LENGTH_LONG);
            MyToast.show("ACTION_PACKAGE_REMOVED", Toast.LENGTH_LONG);
        }
    }
}
