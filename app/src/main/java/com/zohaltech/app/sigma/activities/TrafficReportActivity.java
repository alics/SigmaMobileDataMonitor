package com.zohaltech.app.sigma.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.TrafficReportPagerAdapter;
import com.zohaltech.app.sigma.classes.App;

public class TrafficReportActivity extends EnhancedActivity {
    PagerSlidingTabStrip      tabTrafficReports;
    ViewPager                 pagerTrafficReports;
    TrafficReportPagerAdapter trafficReportPagerAdapter;

    @Override
    protected void onCreated() {
        setContentView(R.layout.activity_traffic_report);

        pagerTrafficReports = (ViewPager) findViewById(R.id.pagerTrafficReports);
        trafficReportPagerAdapter = new TrafficReportPagerAdapter(getSupportFragmentManager());
        pagerTrafficReports.setAdapter(trafficReportPagerAdapter);

        tabTrafficReports = (PagerSlidingTabStrip) findViewById(R.id.tabTrafficReports);
        tabTrafficReports.setViewPager(pagerTrafficReports);

        pagerTrafficReports.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                changeTabTitleColors(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        changeTabsFont();
        changeTabTitleColors(0);
    }

    private void changeTabTitleColors(int position) {
        ((TextView) ((ViewGroup) tabTrafficReports.getChildAt(0)).getChildAt(0)).setTextColor(getResources().getColor(R.color.primary_light));
        ((TextView) ((ViewGroup) tabTrafficReports.getChildAt(0)).getChildAt(1)).setTextColor(getResources().getColor(R.color.primary_light));
        ((TextView) ((ViewGroup) tabTrafficReports.getChildAt(0)).getChildAt(position)).setTextColor(getResources().getColor(R.color.white));
    }

    private void changeTabsFont() {
        ViewGroup vg = (ViewGroup) tabTrafficReports.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            TextView textView = (TextView) vg.getChildAt(j);
            textView.setWidth(App.screenWidth / 2);
            textView.setTypeface(App.persianFont);
            textView.setTextColor(getResources().getColor(R.color.primary_light));
            textView.setTextSize(14);
        }
    }

    @Override
    protected void onToolbarCreated() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            txtToolbarTitle.setText("گزارش مصرف");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }
}
