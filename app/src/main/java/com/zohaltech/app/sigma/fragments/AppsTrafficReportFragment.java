package com.zohaltech.app.sigma.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.AppsTrafficReportAdapter;
import com.zohaltech.app.sigma.classes.AppDataUsageMeter;
import com.zohaltech.app.sigma.dal.AppsUsageLogs;
import com.zohaltech.app.sigma.entities.AppsTrafficMonitor;

import java.util.ArrayList;

public class AppsTrafficReportFragment extends Fragment {
    ListView                      lstAppsTraffic;
    Button                        btnData;
    Button                        btnWifi;
    ArrayList<AppsTrafficMonitor> appsTrafficMonitors;
    AppsTrafficReportAdapter      adapter;

    public enum ReportType {
        WIFI,
        DATA,
        BOTH
    }

    public static AppsTrafficReportFragment newInstance() {
        Bundle args = new Bundle();
        AppsTrafficReportFragment fragment = new AppsTrafficReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apps_traffic, container, false);

        AppDataUsageMeter.takeSnapshot();

        lstAppsTraffic = (ListView) view.findViewById(R.id.lstAppsTraffic);
        btnData = (Button) view.findViewById(R.id.btnData);
        btnWifi = (Button) view.findViewById(R.id.btnWifi);

        appsTrafficMonitors = AppsUsageLogs.getAppsTrafficReport(ReportType.BOTH);
        adapter = new AppsTrafficReportAdapter(appsTrafficMonitors, ReportType.BOTH);
        lstAppsTraffic.setAdapter(adapter);

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appsTrafficMonitors = AppsUsageLogs.getAppsTrafficReport(ReportType.DATA);
                adapter = new AppsTrafficReportAdapter(appsTrafficMonitors, ReportType.DATA);
                lstAppsTraffic.setAdapter(adapter);
            }
        });


        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appsTrafficMonitors = AppsUsageLogs.getAppsTrafficReport(ReportType.WIFI);
                adapter = new AppsTrafficReportAdapter(appsTrafficMonitors, ReportType.WIFI);
                lstAppsTraffic.setAdapter(adapter);
            }
        });

        return view;
    }

    //private void populateSummery() {
    //    long sum = 0;
    //    long sumWifi = 0;
    //    for (AppsTrafficMonitor trafficMonitor : appsTrafficMonitors) {
    //        sum += trafficMonitor.getMobileTraffic();
    //        sumWifi += trafficMonitor.getWifiTraffic();
    //    }
    //    txtTotalTraffic.setText(TrafficUnitsUtil.getUsedTrafficWithPoint(sum));
    //    txtTotalTrafficWifi.setText(TrafficUnitsUtil.getUsedTrafficWithPoint(sumWifi));
    //}
}
