package com.zohaltech.app.sigma.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.AppsTrafficReportAdapter;
import com.zohaltech.app.sigma.classes.AppDataUsageMeter;
import com.zohaltech.app.sigma.classes.TrafficUnitsUtil;
import com.zohaltech.app.sigma.dal.AppsUsageLogs;
import com.zohaltech.app.sigma.entities.AppsTrafficMonitor;

import java.util.ArrayList;

public class AppsTrafficReportFragment extends Fragment {
    ListView                      lstAppsTraffic;
    //TextView                      txtTotalTraffic;
    //TextView                      txtTotalTrafficWifi;
    ArrayList<AppsTrafficMonitor> appsTrafficMonitors;
    AppsTrafficReportAdapter      adapter;

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
        //txtTotalTraffic = (TextView) view.findViewById(R.id.txtTotalTraffic);
        //txtTotalTrafficWifi = (TextView) view.findViewById(R.id.txtTotalTrafficWifi);

        appsTrafficMonitors = AppsUsageLogs.getAppsTrafficReport();
        adapter = new AppsTrafficReportAdapter(appsTrafficMonitors);
        lstAppsTraffic.setAdapter(adapter);

      //  populateSummery();

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
