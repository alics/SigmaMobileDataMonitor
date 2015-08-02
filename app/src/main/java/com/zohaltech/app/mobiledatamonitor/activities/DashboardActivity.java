package com.zohaltech.app.mobiledatamonitor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.adapters.UsagePagerAdapter;
import com.zohaltech.app.mobiledatamonitor.classes.App;

public class DashboardActivity extends EnhancedActivity {

    ViewPager pagerUsages;
    Button    btnPackageManagement;
    Button    btnPurchasePackage;
    Button    btnUsageReport;

    UsagePagerAdapter usagePagerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //AlarmHandler.start(App.context);

        pagerUsages = (ViewPager) findViewById(R.id.pagerUsages);
        btnPackageManagement = (Button) findViewById(R.id.btnPackageManagement);
        btnPurchasePackage = (Button) findViewById(R.id.btnPurchasePackage);
        btnUsageReport = (Button) findViewById(R.id.btnUsageReport);

        pagerUsages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 2) {
                    if (pagerUsages.getCurrentItem() == 0) {
                        //usagePagerAdapter.startAnimation0();
                    } else if (pagerUsages.getCurrentItem() == 1) {
                        usagePagerAdapter.startAnimation1();
                    } else if (pagerUsages.getCurrentItem() == 2) {
                        //usagePagerAdapter.startAnimation2();
                    }
                }
            }
        });

        btnPackageManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(App.currentActivity, PackageManagementActivity.class);
                startActivity(myIntent);
            }
        });

        btnPurchasePackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.currentActivity, PackagesActivity.class);
                startActivity(intent);
            }
        });

        btnUsageReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.currentActivity, DailyTrafficMonitorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        usagePagerAdapter = new UsagePagerAdapter();
        pagerUsages.setAdapter(usagePagerAdapter);
        pagerUsages.setCurrentItem(1);
    }
}
