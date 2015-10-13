package com.zohaltech.app.sigma.classes;


import com.zohaltech.app.sigma.dal.AppsUsageLogs;
import com.zohaltech.app.sigma.entities.AppsUsageLog;

import java.util.HashSet;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppDataUsageMeter {
    private static ScheduledExecutorService executorService;
    private static AppsTrafficSnapshot latest = null;

    public static Runnable runnable = new Runnable() {
        @Override
        public void run() {
            takeSnapshot();
        }
    };

    public static void takeSnapshot() {
        AppsTrafficSnapshot previous = latest;
        latest = new AppsTrafficSnapshot(previous);

        HashSet<Integer> intersection = new HashSet<Integer>(latest.apps.keySet());

        if (previous != null) {
            intersection.retainAll(previous.apps.keySet());
        }

        for (Integer uid : intersection) {
            AppsTrafficRecord latest_rec = latest.apps.get(uid);
            //AppsTrafficRecord previous_rec = (previous == null ? null : previous.apps.get(uid));
            emitLog(latest_rec);
        }
    }

    private static void emitLog(AppsTrafficRecord latest_rec) {
        if (latest_rec.rx > 0 || latest_rec.tx > 0) {
            if (latest_rec.connectivityType == AppsTrafficRecord.ConnectivityType.WIFI) {

                AppsUsageLog log = new AppsUsageLog(latest_rec.appId, 0L, latest_rec.sumBytes, Helper.getCurrentDateTime());
                AppsUsageLogs.insert(log);
            } else {

                //  Long dataBytes = (latest_rec.rx + latest_rec.tx) - (latest_rec.sumWifi+ latest_rec.sumData);
                AppsUsageLog log = new AppsUsageLog(latest_rec.appId, latest_rec.sumBytes, 0L, Helper.getCurrentDateTime());
                AppsUsageLogs.insert(log);
            }
        }
    }

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
}
