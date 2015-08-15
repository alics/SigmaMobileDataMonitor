package com.zohaltech.app.mobiledatamonitor.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.DataUsageService;
import com.zohaltech.app.mobiledatamonitor.classes.TrafficUnitsUtil;

import widgets.CircleProgress;

public class TodayUsageFragment extends Fragment {

    CircleProgress progressTodayUsage;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long todayUsage = intent.getLongExtra(DataUsageService.DAILY_USAGE_BYTES, 0);
                updateUI(todayUsage);
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
        int size = (App.screenWidth) / 2;
        progressTodayUsage = (CircleProgress) view.findViewById(R.id.progressTodayUsage);
        progressTodayUsage.setLayoutParams(new LinearLayout.LayoutParams(size, size));
        updateUI(App.preferences.getLong(DataUsageService.DAILY_USAGE_BYTES, 0));
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter(DataUsageService.DAILY_USAGE_ACTION));
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }

    public void updateUI(final long bytes) {
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                //TrafficDisplay trafficDisplay = TrafficDisplay.getTodayTraffic(App.preferences.getLong(DataUsageService.DAILY_USAGE_BYTES, 0));
                TrafficUnitsUtil trafficDisplay = TrafficUnitsUtil.getTodayTraffic(bytes);
                progressTodayUsage.setProgress(trafficDisplay.getValue(), trafficDisplay.getPostfix());
            }
        });
    }
}
