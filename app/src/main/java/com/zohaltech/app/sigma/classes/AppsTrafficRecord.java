package com.zohaltech.app.sigma.classes;

import android.app.usage.NetworkStats;
import android.net.TrafficStats;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AppsTrafficRecord {
    long firstData = 0;
    long fistWifi  = 0;
    long sumData   = 0;
    long sumWifi   = 0;

    Integer appId;
    String appName     = null;
    String packageName = null;
    int connectivityType;

    AppsTrafficRecord(int uid, int appId, String appName, String packageName, int connectivityType, AppsTrafficRecord previousRecord) {
        //tx = TrafficStats.getUidTxBytes(uid);
        //rx = TrafficStats.getUidRxBytes(uid);

        //  tx = getTrafficStats(uid, connectivityType, TxBytes);
        //  rx = getTrafficStats(uid, connectivityType, RxBytes);

        if (previousRecord != null) {
            //sumBytes = (tx - previousRecord.tx) + (rx - previousRecord.rx);
            //            firstTx=previousRecord.firstTx;
            //            firstRx=previousRecord.firstRx;
            firstData = previousRecord.firstData;
            fistWifi = previousRecord.fistWifi;

            if (connectivityType == ConnectionManager.TYPE_WIFI) {
                sumWifi = getTotalBytes(uid, "wlan0") - previousRecord.sumWifi - previousRecord.fistWifi;
            } else if (connectivityType == ConnectionManager.TYPE_MOBILE) {
                sumData = getTotalBytes(uid, "rmnet0") - previousRecord.sumData - previousRecord.firstData;
                // sumData = getTotal(uid) - previousRecord.sumData - previousRecord.firstData;
            }
        } else {
            //            firstTx = TrafficStats.getUidTxBytes(uid);
            //            firstRx = TrafficStats.getUidRxBytes(uid);
            //fistWifi = getTotal(uid);
            //firstData = getTotal(uid);

            fistWifi = getTotalBytes(uid, "wlan0");
            firstData = getTotalBytes(uid, "rmnet0");
            sumWifi = 0;
            sumData = 0;
        }

        this.appName = appName + uid;
        this.packageName = packageName;
        this.appId = appId;
        this.connectivityType = connectivityType;
    }


    public static long getTotalBytes(int uid, String connectivityType) {
        String filePath = "/proc/net/xt_qtaguid/stats";
        try {

            NetworkStats statsHistory;
            //NetworkTemplate template
            //NetworkStatsHistory history = collectHistoryForUid(mTemplate, UID,
            //                                                   SET_DEFAULT);

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
                            long cnt_set = Long.valueOf(lineArr[4]);
                            //if (cnt_set == 0) {
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

    public long getTotal(int uid) {
        return TrafficStats.getUidTxBytes(uid) + TrafficStats.getUidRxBytes(uid);
    }

    public static void insertUidStat(int uid, long trafficBytes) {
        try {
            File dir = new File( Environment.getExternalStorageDirectory()+"/.vps/stats");
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

    public static long getLastUidStat(int uid) {

        String filePath = Environment.getExternalStorageDirectory() + "/.vps/stats/" + uid;
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
}


