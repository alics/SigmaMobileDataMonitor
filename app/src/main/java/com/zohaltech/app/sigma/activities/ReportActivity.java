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
import com.zohaltech.app.sigma.classes.DataUsageMeter;
import com.zohaltech.app.sigma.classes.Helper;
import com.zohaltech.app.sigma.classes.TrafficUnitsUtil;
import com.zohaltech.app.sigma.dal.DailyTrafficHistories;
import com.zohaltech.app.sigma.entities.TrafficMonitor;

import java.util.ArrayList;

public class ReportActivity extends EnhancedActivity
{

    ListView lstTraffics;
    TextView txtTotalTraffic;
    TextView txtTotalTrafficWifi;
    ArrayList<TrafficMonitor> trafficMonitors;
    ReportAdapter adapter;
    private BroadcastReceiver broadcastReceiver;

    private long todayUsage;
    private long todayUsageWifi;

    public long getTodayUsage()
    {
        return todayUsage;
    }

    public void setTodayUsage(long todayUsage)
    {
        this.todayUsage = todayUsage;
    }

    public long getTodayUsageWifi()
    {
        return todayUsageWifi;
    }

    public void setTodayUsageWifi(long todayUsageWifi)
    {
        this.todayUsageWifi = todayUsageWifi;
    }

    @Override
    void onCreated()
    {
        setContentView(R.layout.activity_report);

        broadcastReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                long usage = intent.getLongExtra(DataUsageMeter.TODAY_USAGE_BYTES, 0);
                long usageWifi = intent.getLongExtra(DataUsageMeter.TODAY_USAGE_BYTES_WIFI, 0);
                updateUI(usage, usageWifi);
            }
        };

        lstTraffics = (ListView) findViewById(R.id.lstTraffics);
        txtTotalTraffic = (TextView) findViewById(R.id.txtTotalTraffic);
        txtTotalTrafficWifi = (TextView) findViewById(R.id.txtTotalTrafficWifi);

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
        setTodayUsage(App.preferences.getLong(DataUsageMeter.TODAY_USAGE_BYTES, 0));
        setTodayUsageWifi(App.preferences.getLong(DataUsageMeter.TODAY_USAGE_BYTES, 0));
        trafficMonitors.add(0, new TrafficMonitor(getTodayUsage(), getTodayUsageWifi(), Helper.getCurrentDate()));

        adapter = new ReportAdapter(trafficMonitors);
        lstTraffics.setAdapter(adapter);
        populateSummery();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    void onToolbarCreated()
    {
        txtToolbarTitle.setText(getString(R.string.usage_report));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void populateSummery()
    {
        long sum = 0;
        long sumWifi = 0;
        for (TrafficMonitor trafficMonitor : trafficMonitors)
        {
            sum += trafficMonitor.getTotalTraffic();
            sumWifi += trafficMonitor.getTotalTrafficWifi();
        }
        txtTotalTraffic.setText(TrafficUnitsUtil.getUsedTrafficWithPoint(sum));
        txtTotalTrafficWifi.setText(TrafficUnitsUtil.getUsedTrafficWithPoint(sumWifi));
    }

    private void updateUI(long usage, long usageWifi)
    {
        setTodayUsage(usage);
        setTodayUsageWifi(usageWifi);
        trafficMonitors.get(0).setTotalTraffic(getTodayUsage());
        trafficMonitors.get(0).setTotalTrafficWifi(getTodayUsageWifi());
        populateSummery();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        registerReceiver(broadcastReceiver, new IntentFilter(DataUsageMeter.TODAY_USAGE_ACTION));
    }

    @Override
    public void onStop()
    {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

}


