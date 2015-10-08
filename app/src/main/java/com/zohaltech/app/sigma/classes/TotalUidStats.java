package com.zohaltech.app.sigma.classes;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.zohaltech.app.sigma.dal.Applications;
import com.zohaltech.app.sigma.dal.AppsUsageLogs;
import com.zohaltech.app.sigma.entities.Application;
import com.zohaltech.app.sigma.entities.AppsUsageLog;

import java.util.List;

public class TotalUidStats {

    public static void proceed() {
        final PackageManager pm = App.context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            Application app = Applications.getAppByUid(packageInfo.uid);
            Integer appId;
            if (app == null) {
                String name = pm.getApplicationLabel(packageInfo).toString();
                app = new Application(packageInfo.uid, packageInfo.packageName, name);
                appId = (int) (long) Applications.insert(app);
            } else
                appId = app.getId();

            long appReceivedBytes = android.net.TrafficStats.getUidRxBytes(packageInfo.uid);
            long appSentBytes = android.net.TrafficStats.getUidTxBytes(packageInfo.uid);

            if (appReceivedBytes > 0) {
                AppsUsageLog log = new AppsUsageLog(appId, appReceivedBytes + appSentBytes, Helper.getCurrentDate());
                AppsUsageLogs.insert(log);
            }
        }
    }
}
