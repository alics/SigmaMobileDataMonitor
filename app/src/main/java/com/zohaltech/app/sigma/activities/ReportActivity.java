package com.zohaltech.app.sigma.activities;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.rey.material.widget.CheckBox;
import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.ReportAdapter;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.DataUsageMeter;
import com.zohaltech.app.sigma.classes.Helper;
import com.zohaltech.app.sigma.classes.TrafficUnitsUtil;
import com.zohaltech.app.sigma.dal.DailyTrafficHistories;
import com.zohaltech.app.sigma.entities.TrafficMonitor;

import java.util.ArrayList;
import java.util.Random;

public class ReportActivity extends EnhancedActivity {

    ListView lstTraffics;
    TextView txtTotalTraffic;
    TextView txtTotalTrafficWifi;
    ArrayList<TrafficMonitor> trafficMonitors = new ArrayList<>();
    ReportAdapter adapter;
    private BroadcastReceiver broadcastReceiver;

    private long todayUsage;
    private long todayUsageWifi;

    public long getTodayUsage() {
        return todayUsage;
    }

    public void setTodayUsage(long todayUsage) {
        this.todayUsage = todayUsage;
    }

    public long getTodayUsageWifi() {
        return todayUsageWifi;
    }

    public void setTodayUsageWifi(long todayUsageWifi) {
        this.todayUsageWifi = todayUsageWifi;
    }

    @Override
    void onCreated() {
        setContentView(R.layout.activity_report);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long usage = intent.getLongExtra(DataUsageMeter.TODAY_USAGE_BYTES, 0);
                long usageWifi = intent.getLongExtra(DataUsageMeter.TODAY_USAGE_BYTES_WIFI, 0);
                updateUI(usage, usageWifi);
            }
        };

        lstTraffics = (ListView) findViewById(R.id.lstTraffics);
        txtTotalTraffic = (TextView) findViewById(R.id.txtTotalTraffic);
        txtTotalTrafficWifi = (TextView) findViewById(R.id.txtTotalTrafficWifi);

        populateTraffics();

        adapter = new ReportAdapter(trafficMonitors);
        lstTraffics.setAdapter(adapter);
        populateSummery();
    }

    private void populateTraffics() {
        trafficMonitors.clear();
        trafficMonitors.addAll(DailyTrafficHistories.getMonthlyTraffic());
        //for (int i = 0; i < 29; i++) {
        //    Random r = new Random();
        //    int Low = 10;
        //    int High = 100 * 1024 * 1024;
        //    int HighWifi = 200 * 1024 * 1024;
        //    int sumReceivedSent = r.nextInt(High - Low) + Low;
        //    int sumReceivedSentWifi = r.nextInt(HighWifi - Low) + Low;
        //    TrafficMonitor trafficMonitor = new TrafficMonitor((long) sumReceivedSent, (long) sumReceivedSentWifi, Helper.addDay(i - 29));
        //    trafficMonitors.add(0, trafficMonitor);
        //}

        //setTodayUsage(App.preferences.getLong(DataUsageMeter.TODAY_USAGE_BYTES, 0));
        //setTodayUsageWifi(App.preferences.getLong(DataUsageMeter.TODAY_USAGE_BYTES_WIFI, 0));
        SharedPreferences preferences;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            preferences = PreferenceManager.getDefaultSharedPreferences(this);
        } else {
            preferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_MULTI_PROCESS);
        }
        setTodayUsage(preferences.getLong(DataUsageMeter.TODAY_USAGE_BYTES, 0));
        setTodayUsageWifi(preferences.getLong(DataUsageMeter.TODAY_USAGE_BYTES_WIFI, 0));

        trafficMonitors.add(0, new TrafficMonitor(getTodayUsage(), getTodayUsageWifi(), Helper.getCurrentDate()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_reset) {
            showResetDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    void onToolbarCreated() {
        txtToolbarTitle.setText(getString(R.string.usage_report));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void populateSummery() {
        long sum = 0;
        long sumWifi = 0;
        for (TrafficMonitor trafficMonitor : trafficMonitors) {
            sum += trafficMonitor.getTotalTraffic();
            sumWifi += trafficMonitor.getTotalTrafficWifi();
        }
        txtTotalTraffic.setText(TrafficUnitsUtil.getUsedTrafficWithPoint(sum));
        txtTotalTrafficWifi.setText(TrafficUnitsUtil.getUsedTrafficWithPoint(sumWifi));
    }

    private void updateUI(long usage, long usageWifi) {
        setTodayUsage(usage);
        setTodayUsageWifi(usageWifi);
        trafficMonitors.get(0).setTotalTraffic(getTodayUsage());
        trafficMonitors.get(0).setTotalTrafficWifi(getTodayUsageWifi());
        populateSummery();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, new IntentFilter(DataUsageMeter.TODAY_USAGE_ACTION));
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    private void showResetDialog() {
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(ReportActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_reset_stats);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(true);
                //final AppCompatCheckBox chkMobile = (AppCompatCheckBox) dialog.findViewById(R.id.chkMobile);
                //final AppCompatCheckBox chkWifi = (AppCompatCheckBox) dialog.findViewById(R.id.chkWifi);
                final CheckBox chkMobile = (CheckBox) dialog.findViewById(R.id.chkMobile);
                final CheckBox chkWifi = (CheckBox) dialog.findViewById(R.id.chkWifi);
                Button positiveButton = (Button) dialog.findViewById(R.id.positiveButton);
                Button negativeButton = (Button) dialog.findViewById(R.id.negativeButton);

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (chkMobile.isChecked() || chkWifi.isChecked()) {
                            //Intent dataService = new Intent(App.context, SigmaDataService.class);
                            //App.context.stopService(dataService);

                            DailyTrafficHistories.reset(chkMobile.isChecked(), chkWifi.isChecked());
                            SharedPreferences preferences;
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                                preferences = PreferenceManager.getDefaultSharedPreferences(ReportActivity.this);
                            } else {
                                preferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_MULTI_PROCESS);
                            }
                            if (chkMobile.isChecked()) {
                                //App.preferences.edit().putLong(DataUsageMeter.TODAY_USAGE_BYTES, 0).apply();
                                preferences.edit().putLong(DataUsageMeter.TODAY_USAGE_BYTES, 0).apply();
                            }
                            if (chkWifi.isChecked()) {
                                //App.preferences.edit().putLong(DataUsageMeter.TODAY_USAGE_BYTES_WIFI, 0).apply();
                                preferences.edit().putLong(DataUsageMeter.TODAY_USAGE_BYTES_WIFI, 0).apply();
                            }

                            //App.context.startService(dataService);

                            populateTraffics();
                            //updateUI(getTodayUsage(), getTodayUsageWifi());
                            adapter.notifyDataSetChanged();
                        }

                        dialog.dismiss();
                    }
                });

                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

}


