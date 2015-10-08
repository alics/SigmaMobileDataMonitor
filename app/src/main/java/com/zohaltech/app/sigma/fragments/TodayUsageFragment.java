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
import com.zohaltech.app.sigma.classes.SigmaDataService;
import com.zohaltech.app.sigma.classes.TrafficUnitsUtil;

import widgets.CircleProgress;

public class TodayUsageFragment extends Fragment {

    CircleProgress progressTodayUsage;
    private BroadcastReceiver broadcastReceiver;

    private long todayUsage;

    public long getTodayUsage() {
        return todayUsage;
    }

    public void setTodayUsage(long todayUsage) {
        this.todayUsage = todayUsage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long usage = intent.getLongExtra(DataUsageMeter.TODAY_USAGE_BYTES, 0);
                updateUI(usage);
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
        updateUI(App.preferences.getLong(DataUsageMeter.TODAY_USAGE_BYTES, 0));
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

    public void updateUI(final long bytes) {
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                setTodayUsage(bytes);
                TrafficUnitsUtil trafficDisplay = TrafficUnitsUtil.getTodayTraffic(getTodayUsage());
                progressTodayUsage.setProgress(trafficDisplay.getValue(), trafficDisplay.getPostfix());
            }
        });
    }
}
