package com.zohaltech.app.sigma.classes;


import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class TotalUidStats {

    public static void proceed() {
        final PackageManager pm = App.context.getPackageManager();
        //get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);


        for (ApplicationInfo packageInfo : packages) {
            long currentReceivedBytes = android.net.TrafficStats.getMobileRxBytes();
            long appReceivedBytes = android.net.TrafficStats.getUidRxBytes(packageInfo.uid);
            int j = 0;
            String name = packageInfo.processName;
            String pname = packageInfo.packageName;
            if (appReceivedBytes > 0) {
                //txtText.append(pname + ":" + appReceivedBytes);
                // txtText.append("\n");}
            }
            //speakOut();
            ActivityManager activityManager = (ActivityManager) App.currentActivity.getSystemService(App.context.ACTIVITY_SERVICE);

            ActivityManager.MemoryInfo mInfo = new ActivityManager.MemoryInfo();

            activityManager.getMemoryInfo(mInfo);

            List<ActivityManager.RunningAppProcessInfo> listOfRunningProcess = activityManager.getRunningAppProcesses();

            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : listOfRunningProcess) {
                if (runningAppProcessInfo.uid > 1026) {
                    currentReceivedBytes = android.net.TrafficStats.getMobileRxBytes();
                    appReceivedBytes = android.net.TrafficStats.getUidRxBytes(runningAppProcessInfo.uid);
                    int s = 0;
                }
            }
        }
    }
}
