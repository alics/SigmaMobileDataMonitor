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
import com.zohaltech.app.sigma.classes.PackageStatus;
import com.zohaltech.app.sigma.classes.RemainingTimeObject;

import widgets.CircleProgress;

public class DaysRemainFragment extends Fragment {

    CircleProgress progressDayRemain;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateUI();
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pager_adapter_day_remain, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int size = (App.screenWidth) / 2;
        progressDayRemain = (CircleProgress) view.findViewById(R.id.progressDayRemain);
        progressDayRemain.setLayoutParams(new LinearLayout.LayoutParams(size, size));
        updateUI();
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

    public void updateUI() {
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                RemainingTimeObject remainingTimeObject = PackageStatus.getLeftDays();
                progressDayRemain.setProgress("" + remainingTimeObject.getRemained(), remainingTimeObject.getTimeDesc());
            }
        });
    }
}
