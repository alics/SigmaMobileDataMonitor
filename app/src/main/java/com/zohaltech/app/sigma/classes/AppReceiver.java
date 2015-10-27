package com.zohaltech.app.sigma.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.zohaltech.app.sigma.dal.Applications;
import com.zohaltech.app.sigma.entities.Application;

public class AppReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ApplicationInfo info;
        Uri uri = intent.getData();
        try {
            String pkgName = uri.getSchemeSpecificPart();
            if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
                info = context.getPackageManager().getApplicationInfo(pkgName, PackageManager.GET_META_DATA);
                String appName = context.getPackageManager().getApplicationLabel(info).toString();
                Application app = new Application(info.uid, appName, pkgName, false);
                Applications.insert(app);
                //MyToast.show("package added : " + info.packageName, Toast.LENGTH_LONG);
                //MyToast.show("app name : " + appName, Toast.LENGTH_LONG);
                //MyToast.show("uid : " + info.uid, Toast.LENGTH_LONG);
                //MyToast.show("icon : " + info.icon, Toast.LENGTH_LONG);
            } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
                Application app = Applications.getAppByPackage(pkgName);
                app.setRemoved(true);
                Applications.update(app);
                //MyToast.show("package removed : " + pkgName, Toast.LENGTH_LONG);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
