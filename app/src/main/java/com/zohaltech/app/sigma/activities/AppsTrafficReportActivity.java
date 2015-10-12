package com.zohaltech.app.sigma.activities;

import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.AppsTrafficReportAdapter;
import com.zohaltech.app.sigma.dal.AppsUsageLogs;
import com.zohaltech.app.sigma.entities.AppsTrafficMonitor;

import java.util.ArrayList;

public class AppsTrafficReportActivity extends EnhancedActivity {
    ListView                      lstAppsTraffic;
    TextView                      txtTotalTraffic;
    TextView                      txtTotalTrafficWifi;
    ArrayList<AppsTrafficMonitor> appsTrafficMonitors;
    AppsTrafficReportAdapter      adapter;

    @Override
    void onCreated() {
        setContentView(R.layout.activity_apps_traffic_report);

        lstAppsTraffic = (ListView) findViewById(R.id.lstAppsTraffic);
        txtTotalTraffic = (TextView) findViewById(R.id.txtTotalTraffic);
        txtTotalTrafficWifi = (TextView) findViewById(R.id.txtTotalTrafficWifi);

        appsTrafficMonitors = AppsUsageLogs.getAppsTrafficReport();
        adapter = new AppsTrafficReportAdapter(appsTrafficMonitors);
        lstAppsTraffic.setAdapter(adapter);
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
        txtToolbarTitle.setText("گزارش مصرف برنامه ها");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
