package com.zohaltech.app.sigma.classes;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.zohaltech.app.sigma.dal.Applications;
import com.zohaltech.app.sigma.entities.Application;

import java.util.HashMap;
import java.util.Iterator;

public class AppsTrafficSnapshot {
    HashMap<Integer, AppsTrafficRecord> apps = new HashMap<>();

    AppsTrafficSnapshot(AppsTrafficSnapshot previousSnapshot) {
        AppsTrafficRecord.ConnectivityType connectivityType = AppsTrafficRecord.ConnectivityType.WIFI;
        int status = ConnectionManager.getConnectivityStatus();
        switch (status) {
            case 1:
                connectivityType = AppsTrafficRecord.ConnectivityType.WIFI;
                break;
            case 2:
                connectivityType = AppsTrafficRecord.ConnectivityType.DATA;
                break;
            case 0:
                if (App.connectivityType == 1)
                    connectivityType = AppsTrafficRecord.ConnectivityType.WIFI;
                else
                    connectivityType = AppsTrafficRecord.ConnectivityType.DATA;
                break;
        }

        PackageManager pm = App.context.getPackageManager();
        Iterator iterator = pm.getInstalledPackages(12288).iterator();
        PackageInfo packageInfo;

        while (iterator.hasNext()) {
            packageInfo = (PackageInfo) iterator.next();
            String[] permissions = packageInfo.requestedPermissions;

            if (permissions!=null && hasInternetAccess(permissions)) {
                ApplicationInfo info = packageInfo.applicationInfo;
                Application app = Applications.getAppByUid(packageInfo.applicationInfo.uid);
                Integer appId;
                String appName = pm.getApplicationLabel(info).toString();
                if (app == null) {
                    app = new Application(info.uid, appName, info.packageName);
                    appId = (int) (long) Applications.insert(app);
                } else
                    appId = app.getId();
                AppsTrafficRecord previousRecord = null;
                if (previousSnapshot != null) {
                    previousRecord = previousSnapshot.apps.get(info.uid);
                }

                AppsTrafficRecord record = new AppsTrafficRecord(info.uid, appId, appName, info.packageName, connectivityType, previousRecord);
                if (record.rx + record.tx != 0)
                    apps.put(info.uid, record);
            }
        }
    }

    private Boolean hasInternetAccess(String[] permissions) {
        for (String permission : permissions) {
            if ("android.permission.INTERNET".equals(permission)) {
                return true;
            }
        }
        return false;
    }

    //for (ApplicationInfo info : pm.getInstalledApplications(PackageManager.GET_PERMISSIONS | PackageManager.GET_META_DATA)) {
    //
    //    String permission = info.permission;
    //    Application app = Applications.getAppByUid(info.uid);
    //    Integer appId;
    //    String appName = pm.getApplicationLabel(info).toString();
    //    if (app == null) {
    //        app = new Application(info.uid, appName, info.packageName);
    //        appId = (int) (long) Applications.insert(app);
    //    } else
    //        appId = app.getId();
    //    AppsTrafficRecord previousRecord = null;
    //    if (previousSnapshot != null) {
    //        previousRecord = previousSnapshot.apps.get(info.uid);
    //    }
    //
    //    AppsTrafficRecord record = new AppsTrafficRecord(info.uid, appId, appName, info.packageName, connectivityType, previousRecord);
    //    if (record.rx + record.tx != 0)
    //        apps.put(info.uid, record);
    //}



}

