package com.zohaltech.app.mobiledatamonitor.activities;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.adapters.ReportAdapter;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.DataUsageService;
import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.classes.TrafficUnitsUtil;
import com.zohaltech.app.mobiledatamonitor.dal.DailyTrafficHistories;
import com.zohaltech.app.mobiledatamonitor.entities.TrafficMonitor;

import java.util.ArrayList;

public class ReportsActivity extends EnhancedActivity {

    ListView lstTraffics;
    TextView txtTotalTraffic;
    ArrayList<TrafficMonitor> trafficMonitors = new ArrayList<>();
    ReportAdapter adapter;
    private BroadcastReceiver broadcastReceiver;


    @Override
    void onCreated() {
        setContentView(R.layout.activity_report);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long todayUsage = intent.getLongExtra(DataUsageService.DAILY_USAGE_BYTES, 0);
                updateUI(todayUsage);

                lstTraffics = (ListView) findViewById(R.id.lstTraffics);
                txtTotalTraffic = (TextView) findViewById(R.id.txtTotalTraffic);

                trafficMonitors = DailyTrafficHistories.getMonthlyTraffic();
                long bytes = App.preferences.getLong(DataUsageService.DAILY_USAGE_BYTES, 0);
                trafficMonitors.add(0, new TrafficMonitor(bytes, Helper.getCurrentDate()));
                adapter = new ReportAdapter(trafficMonitors);
                lstTraffics.setAdapter(adapter);
                populateSummery();
            }
        };
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
        txtToolbarTitle.setText("گزارش روزانه");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void populateSummery() {
        long sum = 0;
        for (TrafficMonitor trafficMonitor : trafficMonitors) {
            sum += trafficMonitor.getTotalTraffic();
        }
        txtTotalTraffic.setText(TrafficUnitsUtil.getUsedTraffic(sum));
    }

    private void updateUI(long todayUsage) {
        trafficMonitors.get(0).setTotalTraffic(todayUsage);
        populateSummery();
        adapter.notifyDataSetChanged();
    }
}


