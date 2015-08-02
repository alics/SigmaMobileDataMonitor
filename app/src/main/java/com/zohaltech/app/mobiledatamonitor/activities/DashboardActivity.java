package com.zohaltech.app.mobiledatamonitor.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;

import widgets.ArcProgress;

public class DashboardActivity extends EnhancedActivity {

    Button      btnPackageManagement;
    Button      btnPurchasePackage;
    Button      btnUsageReport;

    int    dayTraffic;
    int    dayTotalTraffic;
    String strDayTraffic;
    int    nightTraffic;
    int    nightTotalTraffic;
    String strNightTraffic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //AlarmHandler.start(App.context);

        btnPackageManagement = (Button) findViewById(R.id.btnPackageManagement);
        btnPurchasePackage = (Button) findViewById(R.id.btnPurchasePackage);
        btnUsageReport = (Button) findViewById(R.id.btnUsageReport);

        int size1 = (getWindowManager().getDefaultDisplay().getWidth()) / 2;
        int size2 = (getWindowManager().getDefaultDisplay().getWidth()) / 4;

        dayTraffic = 96;
        dayTotalTraffic = 100;
        nightTraffic = 25;
        nightTotalTraffic = 100;
        strDayTraffic = dayTraffic + "/" + dayTotalTraffic + "MB";
        strNightTraffic = nightTraffic + "/" + nightTotalTraffic + "MB";

        btnPackageManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(App.currentActivity, PackageManagementActivity.class);
                startActivity(myIntent);
            }
        });

        btnPurchasePackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.currentActivity, PackagesActivity.class);
                startActivity(intent);
            }
        });

        btnUsageReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.currentActivity, DailyTrafficMonitorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAnimation();
    }

    private void startAnimation() {
        //final Thread dayThread = new Thread(new Runnable() {
        //
        //    @Override
        //    public void run() {
        //        try {
        //            int percent = dayTraffic * 100 / dayTotalTraffic;
        //            int progress = 0;
        //            if (100 - percent >= 4) {
        //                while (progress < (percent + 4)) {
        //                    Thread.sleep(15 - (percent / 10));
        //                    progress++;
        //                    progressDay.setProgress(progress, strDayTraffic);
        //                }
        //                while (progress > percent) {
        //                    Thread.sleep(100);
        //                    progress--;
        //                    progressDay.setProgress(progress, strDayTraffic);
        //                }
        //            } else {
        //                while (progress < percent) {
        //                    Thread.sleep(15 - (percent / 10));
        //                    progress++;
        //                    progressDay.setProgress(progress, strDayTraffic);
        //                }
        //            }
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //    }
        //});

        //final Thread nightThread = new Thread(new Runnable() {
        //
        //    @Override
        //    public void run() {
        //        try {
        //            int percent = nightTraffic * 100 / nightTotalTraffic;
        //            int progress = 0;
        //            if (100 - percent >= 4) {
        //                while (progress < (percent + 4)) {
        //                    Thread.sleep(15 - (percent / 10));
        //                    progress++;
        //                    progressNight.setProgress(percent, strNightTraffic);
        //                }
        //                while (progress > percent) {
        //                    Thread.sleep(100);
        //                    progress--;
        //                    progressNight.setProgress(percent, strNightTraffic);
        //                }
        //            } else {
        //                while (progress < percent) {
        //                    Thread.sleep(15 - (percent / 10));
        //                    progress++;
        //                    progressNight.setProgress(percent, strNightTraffic);
        //                }
        //            }
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //    }
        //});

        //dayThread.start();
        //nightThread.start();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new ProgressDayTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new ProgressNightTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new ProgressDayTask().execute();
            new ProgressNightTask().execute();
        }
    }

    private class ProgressDayTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                int percent = dayTraffic * 100 / dayTotalTraffic;
                int progress = 0;
                if (100 - percent >= 4) {
                    while (progress < (percent + 4)) {
                        Thread.sleep(15 - (percent / 10));
                        progress++;
                        publishProgress(progress);
                    }
                    while (progress > percent) {
                        Thread.sleep(100);
                        progress--;
                        publishProgress(progress);
                    }
                } else {
                    while (progress < percent) {
                        Thread.sleep(15 - (percent / 10));
                        progress++;
                        publishProgress(progress);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    private class ProgressNightTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                int percent = nightTraffic * 100 / nightTotalTraffic;
                int progress = 0;
                if (100 - percent >= 4) {
                    while (progress < (percent + 4)) {
                        Thread.sleep(15 - (percent / 10));
                        progress++;
                        publishProgress(progress);
                    }
                    while (progress > percent) {
                        Thread.sleep(100);
                        progress--;
                        publishProgress(progress);
                    }
                } else {
                    while (progress < percent) {
                        Thread.sleep(15 - (percent / 10));
                        progress++;
                        publishProgress(progress);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
