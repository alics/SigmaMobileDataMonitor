package com.zohaltech.app.sigma.activities;

import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.AppsTrafficReportAdapter;
import com.zohaltech.app.sigma.classes.AppDataUsageMeter;
import com.zohaltech.app.sigma.classes.TrafficUnitsUtil;
import com.zohaltech.app.sigma.dal.AppsUsageLogs;
import com.zohaltech.app.sigma.entities.AppsTrafficMonitor;
import com.zohaltech.app.sigma.fragments.AppsTrafficReportFragment;

import java.util.ArrayList;

public class AppsTrafficReportActivity extends EnhancedActivity {
    ListView lstAppsTraffic;
    TextView txtTotalTraffic;
    TextView txtTotalTrafficWifi;
    ArrayList<AppsTrafficMonitor> appsTrafficMonitors;
    AppsTrafficReportAdapter adapter;

    @Override
    void onCreated() {
        setContentView(R.layout.activity_apps_traffic_report);

        AppDataUsageMeter.takeSnapshot();

        //lstAppsTraffic = (ListView) findViewById(R.id.lstAppsTraffic);
        //txtTotalTraffic = (TextView) findViewById(R.id.txtTotalTraffic);
        //txtTotalTrafficWifi = (TextView) findViewById(R.id.txtTotalTrafficWifi);
        //
        //appsTrafficMonitors = AppsUsageLogs.getAppsTrafficReport(AppsTrafficReportFragment.ReportType.BOTH);
        //adapter = new AppsTrafficReportAdapter(appsTrafficMonitors, AppsTrafficReportFragment.ReportType.BOTH);
        //lstAppsTraffic.setAdapter(adapter);

        populateSummery();
    }

    private void populateSummery() {
        long sum = 0;
        long sumWifi = 0;
        for (AppsTrafficMonitor trafficMonitor : appsTrafficMonitors) {
            sum += trafficMonitor.getMobileTraffic();
            sumWifi += trafficMonitor.getWifiTraffic();
        }
        txtTotalTraffic.setText(TrafficUnitsUtil.getUsedTrafficWithPoint(sum));
        txtTotalTrafficWifi.setText(TrafficUnitsUtil.getUsedTrafficWithPoint(sumWifi));
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
