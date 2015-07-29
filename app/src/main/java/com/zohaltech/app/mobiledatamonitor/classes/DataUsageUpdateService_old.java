//
//package com.zohaltech.app.mobiledatamonitor.classes;
//
//        import android.app.IntentService;
//        import android.content.Intent;
//        import android.util.Log;
//
//        import com.zohaltech.app.mobiledatamonitor.dal.UsageLogs;
//        import com.zohaltech.app.mobiledatamonitor.entities.UsageLog;
//
//
//public class DataUsageUpdateService_old extends IntentService {
//
//    private static final int USAGE_LOG_INTERVAL       = 10;
//
//    private static long tempReceivedBytes = 0;
//    private static long tempSentBytes     = 0;
//
//    private static boolean firstTime = true;
//
//    private static int    usageLogInterval           = 0;
//    private static long   tempUsage                  = 0;
//    private static long   currentDateSumTraffic      = 0;
//    private static String strCurrentDateTotalTraffic = "0.000 MB";
//
//    public DataUsageUpdateService_old() {
//        super("DataUsageUpdateService");
//    }
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        long currentReceivedBytes = android.net.TrafficStats.getMobileRxBytes();
//        long currentSentBytes = android.net.TrafficStats.getMobileTxBytes();
//        long receivedBytes = 0;
//        long sentBytes = 0;
//
//        if (currentReceivedBytes + currentSentBytes == 0) {
//            log("transfer = 0");
//            firstTime = true;
//        } else {
//            if (firstTime) {
//                firstTime = false;
//                tempReceivedBytes = currentReceivedBytes;
//                tempSentBytes = currentSentBytes;
//            }
//            receivedBytes = currentReceivedBytes - tempReceivedBytes;
//            log("receivedBytes = " + receivedBytes);
//            sentBytes = currentSentBytes - tempSentBytes;
//            log("sentBytes = " + sentBytes);
//            tempReceivedBytes = currentReceivedBytes;
//            tempSentBytes = currentSentBytes;
//
//            tempUsage = tempUsage + receivedBytes + sentBytes;
//            log("tempUsage = " + tempUsage);
//
//            usageLogInterval++;
//            if (usageLogInterval == USAGE_LOG_INTERVAL) {
//                UsageLogs.insert(new UsageLog(tempUsage));
//                log(tempUsage + " inserted");
//                currentDateSumTraffic = UsageLogs.getCurrentDateSumTraffic();
//                log("currentDateSumTraffic = " + currentDateSumTraffic);
//                strCurrentDateTotalTraffic = String.format("%.2f MB", (float) currentDateSumTraffic / (1024 * 1024));
//                log("strCurrentDateTotalTraffic = " + strCurrentDateTotalTraffic);
//                usageLogInterval = 0;
//                tempUsage = 0;
//            }
//        }
//
//        NotificationHandler.displayNotification(App.context, String.format("Down: %s B/s, Up:%s B/s", receivedBytes, sentBytes)
//                , String.format("Total: %s", strCurrentDateTotalTraffic)
//                , "28% of 3 Gigabyte used");
//        log("Notification : receivedBytes = " + receivedBytes + ", sentBytes = " + sentBytes + ", total = " + strCurrentDateTotalTraffic);
//
//        AlarmReceiver.completeWakefulIntent(intent);
//    }
//
//    public static void log(String logText){
//        Log.i("LOG", SolarCalendar.getCurrentShamsiDateTime() + " - " + logText);
//    }
//}
//
