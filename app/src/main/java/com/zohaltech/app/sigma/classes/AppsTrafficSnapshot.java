package com.zohaltech.app.sigma.classes;

import android.os.Environment;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.dal.Applications;
import com.zohaltech.app.sigma.dal.AppsUsageLogs;
import com.zohaltech.app.sigma.dal.SnapshotStatus;
import com.zohaltech.app.sigma.entities.Application;
import com.zohaltech.app.sigma.entities.AppsUsageLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AppsTrafficSnapshot {
    public static void snapshot(int initStatus) {
        final String wifiIface = "wlan0";
        final String dataIface = "rmnet0";
        
        ArrayList<Application> applications = Applications.select();
        for (Application app : applications) {
            if (initStatus == SnapshotStatus.InitStatus.FIRST_SNAPSHOT.ordinal()) {
                long totalWifi = getTotalBytes(app.getUid(), wifiIface);
                long totalData = getTotalBytes(app.getUid(), dataIface);
                logUidStat(app.getUid(), totalData, "init/" + dataIface);
                logUidStat(app.getUid(), totalWifi, "init/" + wifiIface);

                //AppsUsageLog log = new AppsUsageLog(app.getId(), 0L, 0L, Helper.getCurrentDateTime());
                //AppsUsageLogs.insert(log);

                SnapshotStatus status = SnapshotStatus.getCurrentSnapshotStatus();
                status.setInitializationStatus(SnapshotStatus.InitStatus.BEFORE_FIRST_BOOT.ordinal());
                SnapshotStatus.update(status);
            } else if (initStatus == SnapshotStatus.InitStatus.BEFORE_FIRST_BOOT.ordinal()) {
                long totalWifi = getTotalBytes(app.getUid(), wifiIface);
                long previousWifiStat = getLastUidStat(app.getUid(), wifiIface);
                long initWifiStat = getLastUidStat(app.getUid(), "init/" + wifiIface);
                long wifi = totalWifi - previousWifiStat - initWifiStat;

                long totalData = getTotalBytes(app.getUid(), dataIface);
                long previousDataStat = getLastUidStat(app.getUid(), dataIface);
                long initDataStat = getLastUidStat(app.getUid(), "init/" + dataIface);
                long data = totalData - previousDataStat - initDataStat;

                if (wifi + data != 0) {
                    AppsUsageLog log = new AppsUsageLog(app.getId(), data, wifi, Helper.getCurrentDateTime());
                    AppsUsageLogs.insert(log);
                }
            } else if (initStatus == SnapshotStatus.InitStatus.NORMAL.ordinal()) {
                long totalWifi = getTotalBytes(app.getUid(), wifiIface);
                long previousWifiStat = getLastUidStat(app.getUid(), wifiIface);

                logUidStat(app.getUid(), totalWifi, wifiIface);

                long wifi = totalWifi;
                if (totalWifi > previousWifiStat)
                    wifi = totalWifi - previousWifiStat;

                long totalData = getTotalBytes(app.getUid(), dataIface);
                long previousDataStat = getLastUidStat(app.getUid(), dataIface);
                logUidStat(app.getUid(), totalData, dataIface);

                long data = totalData;
                if (totalData > previousDataStat)
                    data = totalData - previousDataStat;

                if (wifi + data != 0) {
                    AppsUsageLog log = new AppsUsageLog(app.getId(), data, wifi, Helper.getCurrentDateTime());
                    AppsUsageLogs.insert(log);
                }
            }
        }
    }
    
    private static long getTotalBytes(int uid, String connectivityType) {
        String filePath = App.context.getString(R.string.alicsfh);
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            int hitCount = 0;
            int i = 0;
            long tx_bytes = 0;          // index 7
            long rx_bytes = 0;          // index 5
            long rx_udp_bytes = 0;      // index 11
            long tx_udp_bytes = 0;      // index 17
            long rx_other_bytes = 0;    // index 13
            long tx_other_bytes = 0;    // index 19
            
            while ((line = br.readLine()) != null) {
                if (i != 0) {
                    String lineArr[] = line.split(" ");
                    String iface = lineArr[1];
                    if (hitCount == 2) {
                        return tx_bytes + rx_bytes;
                    }
                    
                    int uid_tag_int = Integer.valueOf(lineArr[3]);
                    if (uid_tag_int == uid) {
                        if (connectivityType.equals(iface)) {
                            hitCount++;
                            
                            tx_bytes += Long.valueOf(lineArr[7]);
                            rx_bytes += Long.valueOf(lineArr[5]);
                        }
                    }
                }
                i++;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    private static void logUidStat(int uid, long trafficBytes, String type) {
        try {
            File dir = new File(App.context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/" + type);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Boolean result = true;
            File file = new File(dir.getPath(), uid + "");
            if (!file.exists())
                result = file.createNewFile();
            if (result) {
                FileWriter writer = new FileWriter(file, false);
                
                writer.write(trafficBytes + "");
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static long getLastUidStat(int uid, String type) {
        String filePath = App.context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + type + "/" + uid;
        long bytes = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            bytes = Long.valueOf(br.readLine());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
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
    
    //HashMap<Integer, AppsTrafficRecord> apps = new HashMap<>();
    //
    //AppsTrafficSnapshot(AppsTrafficSnapshot previousSnapshot, int status) {
    //
    //    ArrayList<Application> applications = Applications.select();
    //    for (Application app : applications) {
    //
    //        long totalWifi = getTotalBytes(app.getUid(), "wlan0");
    //        long previousWifiStat = getLastUidStat(app.getUid(), "wlan0");
    //        logUidStat(app.getUid(), totalWifi, "wlan0");
    //        long wifi = totalWifi - previousWifiStat;
    //
    //        long totalData = getTotalBytes(app.getUid(), "rmnet0");
    //        long previousDataStat = getLastUidStat(app.getUid(), "rmnet0");
    //        logUidStat(app.getUid(), totalData, "rmnet0");
    //        long data = totalData - previousDataStat;
    //
    //        if (wifi + data != 0) {
    //            AppsUsageLog log = new AppsUsageLog(app.getId(), data, wifi, Helper.getCurrentDateTime());
    //            AppsUsageLogs.insert(log);
    //        }
    //
    //        //AppsTrafficRecord previousRecord = null;
    //        //if (previousSnapshot != null) {
    //        //    previousRecord = previousSnapshot.apps.get(app.getUid());
    //        //}
    //        //
    //        //AppsTrafficRecord record = new AppsTrafficRecord(app.getUid(), app.getId(), app.getAppName(), app.getPackageName(), status, previousRecord);
    //        ////   if (record.rx + record.tx != 0)
    //        //apps.put(app.getUid(), record);
    //
    //    }
    //}
    
    
}

