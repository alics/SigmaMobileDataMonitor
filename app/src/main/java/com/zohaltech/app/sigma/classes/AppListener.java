package com.zohaltech.app.sigma.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import widgets.MyToast;

public class AppListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ApplicationInfo applicationInfo;
        Uri uri = intent.getData();
        try {
            String pkgName = uri.getSchemeSpecificPart();
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
                applicationInfo = context.getPackageManager().getApplicationInfo(pkgName, PackageManager.GET_META_DATA);
                MyToast.show("package added : " + applicationInfo.packageName, Toast.LENGTH_LONG);
                String appName = context.getPackageManager().getApplicationLabel(applicationInfo).toString();
                MyToast.show("app name : " + appName, Toast.LENGTH_LONG);
                MyToast.show("uid : " + applicationInfo.uid, Toast.LENGTH_LONG);
                MyToast.show("icon : " + applicationInfo.icon, Toast.LENGTH_LONG);
            } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
                MyToast.show("package removed : " + pkgName, Toast.LENGTH_LONG);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
