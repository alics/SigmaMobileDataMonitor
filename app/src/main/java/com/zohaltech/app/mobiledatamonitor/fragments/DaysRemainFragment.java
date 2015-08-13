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
import com.zohaltech.app.mobiledatamonitor.classes.PackageStatus;
import com.zohaltech.app.mobiledatamonitor.classes.TrafficDisplay;

import widgets.CircleProgress;

public class DaysRemainFragment extends Fragment {

    CircleProgress progressDayRemain;
    //private BroadcastReceiver broadcastReceiver;

    //@Override
    //public void onCreate(Bundle savedInstanceState) {
    //    super.onCreate(savedInstanceState);
    //    broadcastReceiver = new BroadcastReceiver() {
    //        @Override
    //        public void onReceive(Context context, Intent intent) {
    //            updateUI(intent.getLongExtra(DataUsageService.DAYS_REMAIN, 0));
    //        }
    //    };
    //}

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

    //@Override
    //public void onStart() {
    //    super.onStart();
    //    LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter(DataUsageService.DAILY_USAGE_ACTION));
    //}
    //
    //@Override
    //public void onStop() {
    //    super.onStop();
    //    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    //}

    public void updateUI() {
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                progressDayRemain.setProgress("" + PackageStatus.getCurrentStatus().getLeftDays(), "روز");
            }
        });
    }
}
