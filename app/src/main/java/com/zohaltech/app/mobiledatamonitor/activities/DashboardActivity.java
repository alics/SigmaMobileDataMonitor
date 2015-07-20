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
    Button btnStartAnimation;
    TextView txtNightTraffic;

    int dayTraffic;
    int dayTotalTraffic;
    String strDayTraffic;
    int nightTraffic;
    int nightTotalTraffic;
    String strNightTraffic;

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

        dayTraffic = 1243;
        dayTotalTraffic = 3057;
        nightTraffic = 120;
        nightTotalTraffic = 3096;
        strDayTraffic = dayTraffic + "/" + dayTotalTraffic + "MB";
        strNightTraffic = nightTraffic + "/" + nightTotalTraffic + "MB";
        progressDay.setProgress(0, strDayTraffic);
        progressNight.setProgress(0, strNightTraffic);

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
                    int percent = dayTraffic * 100 / dayTotalTraffic;
                    int progress = 0;
                    if (100 - percent >= 2) {
                        while (progress < (percent + 2)) {
                            Thread.sleep(10 - (percent / 10));
                            progress++;
                            progressDay.setProgress(progress, strDayTraffic);
                        }
                        while (progress > percent) {
                            Thread.sleep(100);
                            progress--;
                            progressDay.setProgress(progress, strDayTraffic);
                        }
                    } else {
                        while (progress < percent) {
                            Thread.sleep(10 - (percent / 10));
                            progress++;
                            progressDay.setProgress(progress, strDayTraffic);
                        }
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
                    int percent = nightTraffic * 100 / nightTotalTraffic;
                    int progress = 0;
                    if (100 - percent >= 2) {
                        while (progress < (percent + 2)) {
                            Thread.sleep(10 - (percent / 10));
                            progress++;
                            progressNight.setProgress(percent, strNightTraffic);
                        }
                        while (progress > percent) {
                            Thread.sleep(100);
                            progress--;
                            progressNight.setProgress(percent, strNightTraffic);
                        }
                    } else {
                        while (progress < percent) {
                            Thread.sleep(10 - (percent / 10));
                            progress++;
                            progressNight.setProgress(percent, strNightTraffic);
                        }
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
