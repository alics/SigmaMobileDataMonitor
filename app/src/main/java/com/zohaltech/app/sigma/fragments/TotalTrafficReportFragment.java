package com.zohaltech.app.sigma.fragments;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.rey.material.widget.CheckBox;
import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.ReportAdapter;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.DataUsageMeter;
import com.zohaltech.app.sigma.classes.Helper;
import com.zohaltech.app.sigma.classes.TrafficUnitsUtil;
import com.zohaltech.app.sigma.dal.DailyTrafficHistories;
import com.zohaltech.app.sigma.entities.TrafficMonitor;

import java.util.ArrayList;

public class TotalTrafficReportFragment extends Fragment {
    ListView lstTraffics;
    TextView txtTotalTraffic;
    TextView txtTotalTrafficWifi;
    ArrayList<TrafficMonitor> trafficMonitors = new ArrayList<>();
    ReportAdapter adapter;
    private BroadcastReceiver broadcastReceiver;

    public static TotalTrafficReportFragment newInstance() {
        Bundle args = new Bundle();
        TotalTrafficReportFragment fragment = new TotalTrafficReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private long todayUsage;
    private long todayUsageWifi;

    public long getTodayUsage() {
        return todayUsage;
    }

    public void setTodayUsage(long todayUsage) {
        this.todayUsage = todayUsage;
    }

    public long getTodayUsageWifi() {
        return todayUsageWifi;
    }

    public void setTodayUsageWifi(long todayUsageWifi) {
        this.todayUsageWifi = todayUsageWifi;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total_traffic, container, false);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long usage = intent.getLongExtra(DataUsageMeter.TODAY_USAGE_BYTES, 0);
                long usageWifi = intent.getLongExtra(DataUsageMeter.TODAY_USAGE_BYTES_WIFI, 0);
                updateUI(usage, usageWifi);
            }
        };

        lstTraffics = (ListView) view.findViewById(R.id.lstTraffics);
        txtTotalTraffic = (TextView) view.findViewById(R.id.txtTotalTraffic);
        txtTotalTrafficWifi = (TextView) view.findViewById(R.id.txtTotalTrafficWifi);

        populateTraffics();

        adapter = new ReportAdapter(trafficMonitors);
        lstTraffics.setAdapter(adapter);
        populateSummery();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(DataUsageMeter.TODAY_USAGE_ACTION));
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    private void populateSummery() {
        long sum = 0;
        long sumWifi = 0;
        for (TrafficMonitor trafficMonitor : trafficMonitors) {
            sum += trafficMonitor.getTotalTraffic();
            sumWifi += trafficMonitor.getTotalTrafficWifi();
        }
        txtTotalTraffic.setText(TrafficUnitsUtil.getUsedTrafficWithPoint(sum));
        txtTotalTrafficWifi.setText(TrafficUnitsUtil.getUsedTrafficWithPoint(sumWifi));
    }

    private void updateUI(long usage, long usageWifi) {
        setTodayUsage(usage);
        setTodayUsageWifi(usageWifi);
        trafficMonitors.get(0).setTotalTraffic(getTodayUsage());
        trafficMonitors.get(0).setTotalTrafficWifi(getTodayUsageWifi());
        populateSummery();
        adapter.notifyDataSetChanged();
    }

    private void populateTraffics() {
        trafficMonitors.clear();
        trafficMonitors.addAll(DailyTrafficHistories.getMonthlyTraffic());
        //for (int i = 0; i < 29; i++) {
        //    Random r = new Random();
        //    int Low = 10;
        //    int High = 100 * 1024 * 1024;
        //    int HighWifi = 200 * 1024 * 1024;
        //    int sumReceivedSent = r.nextInt(High - Low) + Low;
        //    int sumReceivedSentWifi = r.nextInt(HighWifi - Low) + Low;
        //    TrafficMonitor trafficMonitor = new TrafficMonitor((long) sumReceivedSent, (long) sumReceivedSentWifi, Helper.addDay(i - 29));
        //    trafficMonitors.add(0, trafficMonitor);
        //}

        SharedPreferences preferences;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        } else {
            preferences = getActivity().getSharedPreferences(getActivity().getPackageName() + "_preferences", Context.MODE_MULTI_PROCESS);
        }
        setTodayUsage(preferences.getLong(DataUsageMeter.TODAY_USAGE_BYTES, 0));
        setTodayUsageWifi(preferences.getLong(DataUsageMeter.TODAY_USAGE_BYTES_WIFI, 0));

        trafficMonitors.add(0, new TrafficMonitor(getTodayUsage(), getTodayUsageWifi(), Helper.getCurrentDate()));
    }

    private void showResetDialog() {
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_reset_stats);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(true);
                final CheckBox chkMobile = (CheckBox) dialog.findViewById(R.id.chkMobile);
                final CheckBox chkWifi = (CheckBox) dialog.findViewById(R.id.chkWifi);
                Button positiveButton = (Button) dialog.findViewById(R.id.positiveButton);
                Button negativeButton = (Button) dialog.findViewById(R.id.negativeButton);

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (chkMobile.isChecked() || chkWifi.isChecked()) {

                            DailyTrafficHistories.reset(chkMobile.isChecked(), chkWifi.isChecked());
                            SharedPreferences preferences;
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            } else {
                                preferences = getActivity().getSharedPreferences(getActivity().getPackageName() + "_preferences", Context.MODE_MULTI_PROCESS);
                            }
                            if (chkMobile.isChecked()) {
                                preferences.edit().putLong(DataUsageMeter.TODAY_USAGE_BYTES, 0).apply();
                            }
                            if (chkWifi.isChecked()) {
                                preferences.edit().putLong(DataUsageMeter.TODAY_USAGE_BYTES_WIFI, 0).apply();
                            }

                            populateTraffics();
                            adapter.notifyDataSetChanged();
                        }

                        dialog.dismiss();
                    }
                });

                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
}
