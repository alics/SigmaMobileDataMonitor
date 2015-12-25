package com.zohaltech.app.sigma.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.AppsTrafficReportAdapter;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.AppDataUsageMeter;
import com.zohaltech.app.sigma.classes.DialogManager;
import com.zohaltech.app.sigma.classes.Helper;
import com.zohaltech.app.sigma.classes.SolarCalendar;
import com.zohaltech.app.sigma.dal.AppsUsageLogs;
import com.zohaltech.app.sigma.entities.AppsTrafficMonitor;

import java.util.ArrayList;

public class AppsTrafficReportFragment extends Fragment {
    ListView                      lstAppsTraffic;
    Button                        btnData;
    Button                        btnWifi;
    Button                        btnPickDate;
    ArrayList<AppsTrafficMonitor> appsTrafficMonitors;
    AppsTrafficReportAdapter      adapter;
    AppCompatSpinner              spinnerFrom;

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
        btnPickDate = (Button) view.findViewById(R.id.btnPickDate);
        spinnerFrom = (AppCompatSpinner) view.findViewById(R.id.spinnerFrom);


        initSpinner();

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

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showDatePickerDialog(App.currentActivity, 1394, 10, 2, new Runnable() {
                    @Override
                    public void run() {
                        String selectedDate = DialogManager.dateResult;
                        btnPickDate.setText(SolarCalendar.getShamsiDate(Helper.getDate(selectedDate)));
                    }
                });
            }
        });

        return view;
    }

    private void initSpinner() {
        ArrayAdapter<String> selectTypesAdapter;
        ArrayList<String> typeList = new ArrayList<>();

        typeList.add("در تاریخ");
        typeList.add("از تاریخ");

        selectTypesAdapter = new ArrayAdapter<>(App.context, R.layout.spinner_current_item, typeList);
        selectTypesAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerFrom.setAdapter(selectTypesAdapter);
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
