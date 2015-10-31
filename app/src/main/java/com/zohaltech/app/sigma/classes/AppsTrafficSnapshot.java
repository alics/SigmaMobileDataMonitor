package com.zohaltech.app.sigma.classes;

import com.zohaltech.app.sigma.dal.Applications;
import com.zohaltech.app.sigma.entities.Application;

import java.util.ArrayList;
import java.util.HashMap;

public class AppsTrafficSnapshot {
    HashMap<Integer, AppsTrafficRecord> apps = new HashMap<>();

    AppsTrafficSnapshot(AppsTrafficSnapshot previousSnapshot,int status) {

        ArrayList<Application> applications = Applications.select();
        for (Application app : applications) {
            AppsTrafficRecord previousRecord = null;
            if (previousSnapshot != null) {
                previousRecord = previousSnapshot.apps.get(app.getUid());
            }

            AppsTrafficRecord record = new AppsTrafficRecord(app.getUid(), app.getId(), app.getAppName(), app.getPackageName(), status, previousRecord);
         //   if (record.rx + record.tx != 0)
                apps.put(app.getUid(), record);

        }

//        PackageManager pm = App.context.getPackageManager();
//        Iterator iterator = pm.getInstalledPackages(12288).iterator();
//        PackageInfo packageInfo;

//        while (iterator.hasNext()) {
//            packageInfo = (PackageInfo) iterator.next();
//            String[] permissions = packageInfo.requestedPermissions;
//
//            if (permissions!=null && hasInternetAccess(permissions)) {
//                ApplicationInfo info = packageInfo.applicationInfo;
//                Application app = Applications.getAppByUid(packageInfo.applicationInfo.uid);
//                Integer appId;
//                String appName = pm.getApplicationLabel(info).toString();
//                if (app == null) {
//                    app = new Application(info.uid, appName, info.packageName);
//                    appId = (int) (long) Applications.insert(app);
//                } else
//                    appId = app.getId();
//                AppsTrafficRecord previousRecord = null;
//                if (previousSnapshot != null) {
//                    previousRecord = previousSnapshot.apps.get(info.uid);
//                }
//
//                AppsTrafficRecord record = new AppsTrafficRecord(info.uid, appId, appName, info.packageName, connectivityType, previousRecord);
//                if (record.rx + record.tx != 0)
//                    apps.put(info.uid, record);
//            }
//        }
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

