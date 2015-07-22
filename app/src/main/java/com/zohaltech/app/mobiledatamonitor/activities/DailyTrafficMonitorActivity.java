package com.zohaltech.app.mobiledatamonitor.activities;

import android.os.Bundle;
import android.widget.ListView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.adapters.TrafficMonitorAdapter;
import com.zohaltech.app.mobiledatamonitor.dal.DailyTrafficHistories;
import com.zohaltech.app.mobiledatamonitor.entities.TrafficMonitor;

import java.util.ArrayList;

public class DailyTrafficMonitorActivity extends EnhancedActivity {

    ListView                  lstTraffics;
    ArrayList<TrafficMonitor> trafficMonitors;
    TrafficMonitorAdapter     adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_traffic_monitor);
    }

    private void initialize() {
        lstTraffics = (ListView) findViewById(R.id.lstTraffics);
        trafficMonitors = DailyTrafficHistories.getMonthlyTraffic();
        adapter = new TrafficMonitorAdapter(trafficMonitors);
        lstTraffics.setAdapter(adapter);
    }
}
