package com.zohaltech.app.mobiledatamonitor.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zohaltech.app.mobiledatamonitor.BuildConfig;
import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.AlarmHandler;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.classes.TrafficDisplay;

import java.math.BigDecimal;

public class MainActivity extends EnhancedActivity {

    private static final int USAGE_LOG_INTERVAL = 10;
    private static long tempReceivedBytes = 0;
    private static long tempSentBytes = 0;
    private static boolean firstTime = true;
    private static int usageLogInterval = 0;
    private static long tempUsage = 0;
    private static long currentDateSumTraffic = 0;
    private static String strCurrentDateTotalTraffic = "0.00000 MB";
    TextView txtTransferRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTransferRate = (TextView) findViewById(R.id.txtTransferRate);

        ((ImageView) findViewById(R.id.img)).setImageResource(getResources().getIdentifier("wkb" + 999, "drawable", BuildConfig.APPLICATION_ID));

        //new TransferTask().execute();

        findViewById(R.id.btnStartMonitoring).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmHandler.start(App.context);
                Toast.makeText(App.currentActivity, "Monitoring has been started!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnStopMonitoring).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmHandler.cancel();
                Toast.makeText(App.currentActivity, "Monitoring has been stopped!", Toast.LENGTH_SHORT).show();
            }
        });

        //Intent service = new Intent(App.context, DataUsageUpdateService.class);
        //startService(service);
    }

    private class TransferTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            while (true) {
                try {
                    Thread.sleep(1000);
                    //return "Transfer Rate : " + TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes() + " Bytes";

                    //NotificationHandler.displayNotification(App.context,"Transfer Rate",TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes() + " Bytes","");

//                    Intent intent = new Intent(App.context, DataUsageUpdateService.class);
//                    App.context.startService(intent);

                    long currentReceivedBytes = android.net.TrafficStats.getMobileRxBytes();
                    long currentSentBytes = android.net.TrafficStats.getMobileTxBytes();
                    long receivedBytes = 0;
                    long sentBytes = 0;

                    if (currentReceivedBytes + currentSentBytes == 0) {
                        //log("transfer = 0");
                        firstTime = true;
                    } else {
                        if (firstTime) {
                            firstTime = false;
                            tempReceivedBytes = currentReceivedBytes;
                            App.preferences.edit().putLong("tempReceivedBytes", tempReceivedBytes).commit();
                            tempSentBytes = currentSentBytes;
                            App.preferences.edit().putLong("tempSentBytes", tempSentBytes).commit();
                        }
                        //////////receivedBytes = currentReceivedBytes - tempReceivedBytes;
                        receivedBytes = currentReceivedBytes - App.preferences.getLong("tempReceivedBytes", 0);
                        //log("receivedBytes = " + receivedBytes);
                        //////////sentBytes = currentSentBytes - tempSentBytes;
                        sentBytes = currentSentBytes - App.preferences.getLong("tempSentBytes", 0);
                        ;
                        //log("sentBytes = " + sentBytes);
                        tempReceivedBytes = currentReceivedBytes;
                        App.preferences.edit().putLong("tempReceivedBytes", tempReceivedBytes).commit();
                        tempSentBytes = currentSentBytes;
                        App.preferences.edit().putLong("tempSentBytes", tempSentBytes).commit();

                        tempUsage = App.preferences.getLong("tempUsage", 0);

                        tempUsage = tempUsage + receivedBytes + sentBytes;

                        App.preferences.edit().putLong("tempUsage", tempUsage).commit();

                        //log("tempUsage = " + tempUsage);

                        //usageLogInterval++;
                        //if (usageLogInterval == USAGE_LOG_INTERVAL) {
                        //UsageLogs.insert(new UsageLog(tempUsage));
                        //log(tempUsage + " inserted");
                        //currentDateSumTraffic = UsageLogs.getCurrentDateSumTraffic();
                        //log("currentDateSumTraffic = " + currentDateSumTraffic);
                        //strCurrentDateTotalTraffic = String.format("%.2f MB", (float) currentDateSumTraffic / (1024 * 1024));
                        //log("strCurrentDateTotalTraffic = " + strCurrentDateTotalTraffic);
                        //usageLogInterval = 0;
                        //tempUsage = 0;
                        //}
                        //strCurrentDateTotalTraffic = String.format("%.2f MB", (float) tempUsage / (1024 * 1024));

                    }

                    int iconId;
                    long value = (receivedBytes + sentBytes) / 1024;
                    //Random r = new Random();
                    //int Low = 10;
                    //int High = 1024 * 30;
                    //int value = r.nextInt(High - Low) + Low;

                    if (value < 1000) {
                        iconId = App.context.getResources().getIdentifier("wkb" + String.format("%03d", value), "drawable", getPackageName());
                    } else if (value >= 1000 && value <= 1024) {
                        iconId = App.context.getResources().getIdentifier("wmb010", "drawable", getPackageName());
                    } else if ((float)value / 1024 > 1 && (float)value / 1024 < 10) {
                        BigDecimal decimal = Helper.round((float) value / 1024, 1);
                        iconId = App.context.getResources().getIdentifier("wmb0" + decimal.toString().replace(".", ""), "drawable", getPackageName());
                    } else if (value / 1024 >= 10 && value / 1024 <= 200) {
                        value = (value / 1024) + 90;
                        iconId = App.context.getResources().getIdentifier("wmb" + value, "drawable", getPackageName());
                    } else if (value / 1024 > 200) {
                        value = (value / 1024) + 90;
                        iconId = App.context.getResources().getIdentifier("wmb" + value, "drawable", getPackageName());
                    } else {
                        iconId = R.drawable.wkb000;
                    }

                    String total = TrafficDisplay.getUsedTraffic(App.preferences.getLong("tempUsage", 0));

                    //NotificationHandler.displayNotification(App.context, iconId, String.format("Down: %s, Up: %s", Helper.getTransferRate(receivedBytes), Helper.getTransferRate(sentBytes))
                    //        , "Total: " + total);

                    //log("Notification : receivedBytes = " + receivedBytes + ", sentBytes = " + sentBytes + ", total = " + strCurrentDateTotalTraffic);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // AlarmHandler.start(App.context);
            //return WebserviceHandler.verify();

            //return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // txtTransferRate.setText(s);
        }
    }
}
