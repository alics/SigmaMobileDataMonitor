package com.zohaltech.app.sigma.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.DataUsageMeter;
import com.zohaltech.app.sigma.classes.TrafficUnitsUtil;

import widgets.CircleProgress;

public class TodayUsageFragment extends Fragment {

    CircleProgress progressTodayUsageMobile;
    CircleProgress progressTodayUsageWifi;
    private BroadcastReceiver broadcastReceiver;

//    private long todayUsageMobile;
//    private long todayUsageWifi;
//
//    public long getTodayUsageMobile() {
//        return todayUsageMobile;
//    }
//
//    public void setTodayUsageMobile(long todayUsageMobile) {
//        this.todayUsageMobile = todayUsageMobile;
//    }
//
//    public long getTodayUsageWifi() {
//        return todayUsageWifi;
//    }
//
//    public void setTodayUsageWifi(long todayUsageWifi) {
//        this.todayUsageWifi = todayUsageWifi;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long usageMobile = intent.getLongExtra(DataUsageMeter.TODAY_USAGE_BYTES, 0);
                updateUIMobile(usageMobile);
                long usageWifi = intent.getLongExtra(DataUsageMeter.TODAY_USAGE_BYTES_WIFI, 0);
                updateUIWifi(usageWifi);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pager_adapter_today_usage, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int size = (App.screenWidth) / 3;
        progressTodayUsageMobile = (CircleProgress) view.findViewById(R.id.progressTodayUsageMobile);
        progressTodayUsageWifi = (CircleProgress) view.findViewById(R.id.progressTodayUsageWifi);
        progressTodayUsageMobile.setLayoutParams(new LinearLayout.LayoutParams(size, size));
        progressTodayUsageWifi.setLayoutParams(new LinearLayout.LayoutParams(size, size));
        updateUIMobile(App.preferences.getLong(DataUsageMeter.TODAY_USAGE_BYTES, 0));
        updateUIWifi(App.preferences.getLong(DataUsageMeter.TODAY_USAGE_BYTES_WIFI, 0));
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

    public void updateUIMobile(final long bytes) {
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                TrafficUnitsUtil trafficDisplay = TrafficUnitsUtil.getTodayTraffic(bytes);
                progressTodayUsageMobile.setProgress(trafficDisplay.getValue(), trafficDisplay.getPostfix());
            }
        });
    }

    public void updateUIWifi(final long bytes) {
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                TrafficUnitsUtil trafficDisplay = TrafficUnitsUtil.getTodayTraffic(bytes);
                progressTodayUsageWifi.setProgress(trafficDisplay.getValue(), trafficDisplay.getPostfix());
            }
        });
    }
}
