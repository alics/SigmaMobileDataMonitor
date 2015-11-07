package com.zohaltech.app.sigma.classes;


import com.zohaltech.app.sigma.dal.SnapshotStatus;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppDataUsageMeter {
    private static ScheduledExecutorService executorService;
    //private static AppsTrafficSnapshot      latest;

    public static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            takeSnapshot();
        }
    };

    public static void takeSnapshot() {
        SnapshotStatus status = SnapshotStatus.getCurrentSnapshotStatus();
        int connectivityStatus = ConnectionManager.getConnectivityStatus();
        if ((connectivityStatus == ConnectionManager.TYPE_MOBILE || connectivityStatus == ConnectionManager.TYPE_WIFI) &&
            status.getStatus() != SnapshotStatus.Running) {

            status.setStatus(SnapshotStatus.Running);
            SnapshotStatus.update(status);
            AppsTrafficSnapshot.snapshot(status.getInitializationStatus());

            //  AppsTrafficSnapshot snapshot=new AppsTrafficSnapshot()
            //AppsTrafficSnapshot previous = latest;
            //latest = new AppsTrafficSnapshot(previous, connectivityStatus);
            //
            //HashSet<Integer> intersection = new HashSet<Integer>(latest.apps.keySet());
            //
            ////if (previous != null) {
            ////    intersection.retainAll(previous.apps.keySet());
            ////}
            //
            //for (Integer uid : intersection) {
            //    AppsTrafficRecord latest_rec = latest.apps.get(uid);
            //    //AppsTrafficRecord previous_rec = (previous == null ? null : previous.apps.get(uid));
            //    emitLog(latest_rec);
            //}

            status.setStatus(SnapshotStatus.Stopped);
            SnapshotStatus.update(status);
        }
    }

    //private static void emitLog(AppsTrafficRecord latest_rec) {
    //    AppsUsageLog log = new AppsUsageLog(latest_rec.appId, latest_rec.sumData, latest_rec.sumWifi, Helper.getCurrentDateTime());
    //    AppsUsageLogs.insert(log);
    //    //  if (latest_rec.rx > 0 || latest_rec.tx > 0) {
    //    //if (latest_rec.connectivityType == AppsTrafficRecord.ConnectivityType.WIFI) {
    //    //
    //    //    AppsUsageLog log = new AppsUsageLog(latest_rec.appId, 0L, latest_rec.sumBytes, Helper.getCurrentDateTime());
    //    //    AppsUsageLogs.insert(log);
    //    //} else if (latest_rec.connectivityType == AppsTrafficRecord.ConnectivityType.DATA) {
    //    //
    //    //    //  Long dataBytes = (latest_rec.rx + latest_rec.tx) - (latest_rec.sumWifi+ latest_rec.sumData);
    //    //    AppsUsageLog log = new AppsUsageLog(latest_rec.appId, latest_rec.sumBytes, 0L, Helper.getCurrentDateTime());
    //    //    AppsUsageLogs.insert(log);
    //    //}
    //    //  }
    //}

    public void execute() {
        if (executorService == null) {
            executorService = Executors.newSingleThreadScheduledExecutor();
        }
        executorService.scheduleAtFixedRate(runnable, 0L, 60000L, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        if (executorService != null) {
            executorService.shutdown();
            executorService = null;
        }
    }

    //public static void takeSnapshot1(){
    //    ArrayList<Application> applications = Applications.select();
    //    for (Application app : applications) {
    //        long totalBytes=AppsTrafficRecord.getTotalBytes(app.getUid(), "wlan0");
    //
    //
    //        long previousStat=AppsTrafficRecord.getLastUidStat(app.getUid());
    //        AppsTrafficRecord.insertUidStat(app.getUid(),totalBytes);
    //        long s=totalBytes-previousStat;
    //
    //
    //        AppsUsageLog log = new AppsUsageLog(app.getId(), 0L, s, Helper.getCurrentDateTime());
    //        AppsUsageLogs.insert(log);
    //
    //
    //
    //    }
    //
    //}


}
