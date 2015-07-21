package com.zohaltech.app.mobiledatamonitor.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.adapters.PackagePagerAdapter;
import com.zohaltech.app.mobiledatamonitor.classes.App;

public class PackagesActivity extends EnhancedActivity {

    ViewPager pagerPackages;
    TabLayout tabOperators;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        pagerPackages = (ViewPager) findViewById(R.id.pagerPackages);
        pagerPackages.setAdapter(new PackagePagerAdapter(getSupportFragmentManager(), PackagesActivity.this));

        // Give the TabLayout the ViewPager
        tabOperators = (TabLayout) findViewById(R.id.tabOperators);

        //((ViewGroup)tabOperators.getChildAt(0)).getChildAt().setTypeface(App.persianFont);
        tabOperators.setupWithViewPager(pagerPackages);

        changeTabsFont();
    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) tabOperators.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(App.persianFont);
                }
            }
        }
    }
}
