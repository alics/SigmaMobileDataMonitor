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
import com.zohaltech.app.mobiledatamonitor.classes.Helper;

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

        selectTabByOperator();

        changeTabsFont();
    }

    private void selectTabByOperator() {
        pagerPackages.setCurrentItem(2);
        Helper.Operator operator = Helper.getOperator();
        if (operator == Helper.Operator.IRANCELL){
            pagerPackages.setCurrentItem(1);
        } else if(operator == Helper.Operator.RIGHTELL){
            pagerPackages.setCurrentItem(0);
        }
    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) tabOperators.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    TextView textView = (TextView) tabViewChild;
                    textView.setWidth(App.screenWidth / 3);
                    textView.setTypeface(App.persianFont);
                }
            }
        }
    }
}
