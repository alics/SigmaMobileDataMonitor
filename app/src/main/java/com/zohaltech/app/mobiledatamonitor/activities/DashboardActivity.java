package com.zohaltech.app.mobiledatamonitor.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;

import widgets.ArcProgress;

public class DashboardActivity extends EnhancedActivity {


    ArcProgress progressDay;
    ArcProgress progressNight;
    Button      btnStartAnimation;
    TextView txtNightTraffic;

    String dayTraffic;
    String nightTraffic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        progressDay = (ArcProgress) findViewById(R.id.progressDay);
        progressNight = (ArcProgress) findViewById(R.id.progressNight);
        btnStartAnimation = (Button) findViewById(R.id.btnStartAnimation);
        txtNightTraffic = (TextView) findViewById(R.id.txtNightTraffic);

        int size1 = (getWindowManager().getDefaultDisplay().getWidth()) / 2;
        int size2 = (getWindowManager().getDefaultDisplay().getWidth()) / 4;
        progressDay.setLayoutParams(new LinearLayout.LayoutParams(size1, size1));
        progressNight.setLayoutParams(new LinearLayout.LayoutParams(size2, size2));

        txtNightTraffic.setLayoutParams(new LinearLayout.LayoutParams(size2, ViewGroup.LayoutParams.WRAP_CONTENT));

        dayTraffic = "1243/3057 MB";
        nightTraffic = "3/3057 MB";
        progressDay.setProgress(0, dayTraffic);
        progressNight.setProgress(0, dayTraffic);

        btnStartAnimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAnimation();
            }
        }, 1000);
    }

    private void startAnimation() {
        final Thread dayThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    int percent= 0;
                    while (percent < 75) {
                        Thread.sleep(10 - (percent / 10));
                        percent++;
                        progressDay.setProgress(percent, dayTraffic);
                    }
                    //Thread.sleep(50);
                    while (percent > 73) {
                        Thread.sleep(50);
                        percent--;
                        progressDay.setProgress(percent, dayTraffic);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        final Thread nightThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    int percent= 0;
                    while (percent < 25) {
                        Thread.sleep(10 - (percent / 10));
                        percent++;
                        progressNight.setProgress(percent, nightTraffic);
                    }
                    //Thread.sleep(50);
                    while (percent > 23) {
                        Thread.sleep(50);
                        percent--;
                        progressNight.setProgress(percent, nightTraffic);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        dayThread.start();
        nightThread.start();
    }
}
