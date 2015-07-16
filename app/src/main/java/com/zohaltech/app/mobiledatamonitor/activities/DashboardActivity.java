package com.zohaltech.app.mobiledatamonitor.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.zohaltech.app.mobiledatamonitor.R;

import widgets.ArcProgress;

public class DashboardActivity extends EnhancedActivity {

    ArcProgress    progressDay;
    ArcProgress progressNight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        progressDay = (ArcProgress) findViewById(R.id.progressDay);
        progressNight = (ArcProgress) findViewById(R.id.progressNight);

        int size1 = (getWindowManager().getDefaultDisplay().getWidth()) / 2;
        int size2 = (getWindowManager().getDefaultDisplay().getWidth()) / 4;
        progressDay.setLayoutParams(new LinearLayout.LayoutParams(size1, size1));
        progressNight.setLayoutParams(new LinearLayout.LayoutParams(size2, size2));
        progressDay.setProperties(22, 136);
        progressNight.setProperties(11, 46);
    }

    public void startAnimation(View view){
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                int percent = 0;
                while (percent < 100) {
                    try {
                        Thread.sleep(50);
                        percent++;
                        progressDay.setProgress(percent);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();    }
}
