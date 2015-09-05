package com.zohaltech.app.sigma.activities;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.ReportAdapter;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.Helper;
import com.zohaltech.app.sigma.classes.SigmaDataService;
import com.zohaltech.app.sigma.classes.TrafficUnitsUtil;
import com.zohaltech.app.sigma.dal.DailyTrafficHistories;
import com.zohaltech.app.sigma.entities.TrafficMonitor;

import java.util.ArrayList;
import java.util.Random;

public class ReportActivity extends EnhancedActivity {

    ListView                  lstTraffics;
    TextView                  txtTotalTraffic;
    ArrayList<TrafficMonitor> trafficMonitors;
    ReportAdapter             adapter;
    private BroadcastReceiver broadcastReceiver;

    private long todayUsage;

    public long getTodayUsage() {
        return todayUsage;
    }

    public void setTodayUsage(long todayUsage) {
        this.todayUsage = todayUsage;
    }

    @Override
    void onCreated() {
        setContentView(R.layout.activity_report);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long usage = intent.getLongExtra(SigmaDataService.TODAY_USAGE_BYTES, 0);
                updateUI(usage);
            }
        };

        lstTraffics = (ListView) findViewById(R.id.lstTraffics);
        txtTotalTraffic = (TextView) findViewById(R.id.txtTotalTraffic);

        trafficMonitors = DailyTrafficHistories.getMonthlyTraffic();
        //trafficMonitors = new ArrayList<>();
        //for (int i = 0; i < 29; i++) {
        //    Random r = new Random();
        //    int Low = 10;
        //    int High = 100*1024*1024;
        //    int sumReceivedSent = r.nextInt(High - Low) + Low;
        //    TrafficMonitor trafficMonitor = new TrafficMonitor((long)sumReceivedSent, Helper.addDay(i - 29));
        //    trafficMonitors.add(0, trafficMonitor);
        //}
        setTodayUsage(App.preferences.getLong(SigmaDataService.TODAY_USAGE_BYTES, 0));
        trafficMonitors.add(0, new TrafficMonitor(getTodayUsage(), Helper.getCurrentDate()));

        adapter = new ReportAdapter(trafficMonitors);
        lstTraffics.setAdapter(adapter);
        populateSummery();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
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
        for (TrafficMonitor trafficMonitor : trafficMonitors) {
            sum += trafficMonitor.getTotalTraffic();
        }
        txtTotalTraffic.setText(TrafficUnitsUtil.getUsedTrafficWithPoint(sum));
    }

    private void updateUI(long usage) {
        setTodayUsage(usage);
        trafficMonitors.get(0).setTotalTraffic(getTodayUsage());
        populateSummery();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, new IntentFilter(SigmaDataService.TODAY_USAGE_ACTION));
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
}


