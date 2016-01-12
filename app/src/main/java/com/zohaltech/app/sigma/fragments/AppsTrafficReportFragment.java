package com.zohaltech.app.sigma.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    CheckBox                      chkData;
    CheckBox                      chkWifi;
    Button                        btnPickDate;
    ArrayList<AppsTrafficMonitor> appsTrafficMonitors;
    AppsTrafficReportAdapter      adapter;
    AppCompatSpinner              spinnerFrom;

    String          selectedDate;

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
        chkData = (CheckBox) view.findViewById(R.id.chkData);
        chkWifi = (CheckBox) view.findViewById(R.id.chkWifi);
        btnPickDate = (Button) view.findViewById(R.id.btnPickDate);
        spinnerFrom = (AppCompatSpinner) view.findViewById(R.id.spinnerFrom);


        initSpinner();

        selectedDate = Helper.getCurrentDateTime().substring(0, 10);
        btnPickDate.setText(SolarCalendar.getCurrentShamsiDateTime().substring(0, 10));
        //appsTrafficMonitors = AppsUsageLogs.getAppsTrafficReport(ReportType.BOTH, "", RestrictionType.ON);
        //adapter = new AppsTrafficReportAdapter(appsTrafficMonitors, ReportType.BOTH);
        //lstAppsTraffic.setAdapter(adapter);
        bindReport();

        //btnData.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        reportType = ReportType.DATA;
        //        bindReport();
        //        //appsTrafficMonitors = AppsUsageLogs.getAppsTrafficReport(ReportType.DATA);
        //        //adapter = new AppsTrafficReportAdapter(appsTrafficMonitors, ReportType.DATA);
        //        //lstAppsTraffic.setAdapter(adapter);
        //    }
        //});
        //
        //
        //btnWifi.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        reportType = ReportType.WIFI;
        //        bindReport();
        //        //reportType = ReportType.WIFI;
        //        //appsTrafficMonitors = AppsUsageLogs.getAppsTrafficReport(ReportType.WIFI);
        //        //adapter = new AppsTrafficReportAdapter(appsTrafficMonitors, ReportType.WIFI);
        //        //lstAppsTraffic.setAdapter(adapter);
        //    }
        //});

        chkData.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bindReport();
            }
        });

        chkWifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bindReport();
            }
        });

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentSolarDate = SolarCalendar.getCurrentShamsiDateTime().substring(0, 10);
                String dareParts[] = currentSolarDate.split("/");
                DialogManager.showDatePickerDialog(App.currentActivity, Integer.parseInt(dareParts[0]), Integer.parseInt(dareParts[1]), Integer.parseInt(dareParts[2]), new Runnable() {
                    @Override
                    public void run() {
                        selectedDate = DialogManager.dateResult;
                        btnPickDate.setText(SolarCalendar.getShamsiDate(Helper.getDate(selectedDate)));
                        bindReport();
                    }
                });
            }
        });

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bindReport();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                bindReport();
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

    private void bindReport() {
        //RestrictionType restrictionType;
        ReportType      reportType;

        if (chkData.isChecked() && chkWifi.isChecked()) {
            reportType = ReportType.BOTH;
        } else if (chkData.isChecked()) {
            reportType = ReportType.DATA;
        } else {
            reportType = ReportType.WIFI;
        }
        appsTrafficMonitors = AppsUsageLogs.getAppsTrafficReport(reportType, selectedDate, spinnerFrom.getSelectedItemPosition() == 0 ? RestrictionType.ON : RestrictionType.FROM);
        adapter = new AppsTrafficReportAdapter(appsTrafficMonitors, reportType);
        lstAppsTraffic.setAdapter(adapter);
    }

    public enum ReportType {
        WIFI,
        DATA,
        BOTH
    }

    public enum RestrictionType {
        ON,
        FROM
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
