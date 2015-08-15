package com.zohaltech.app.mobiledatamonitor.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.PackageStatus;

import widgets.CircleProgress;

public class DaysRemainFragment extends Fragment {

    CircleProgress progressDayRemain;

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

    public void updateUI() {
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                progressDayRemain.setProgress("" + PackageStatus.getCurrentStatus().getLeftDays(), "روز");
            }
        });
    }
}
