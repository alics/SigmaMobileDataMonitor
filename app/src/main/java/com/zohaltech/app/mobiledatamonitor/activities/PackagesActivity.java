package com.zohaltech.app.mobiledatamonitor.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ExpandableListView;
import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.adapters.ExpandablePackageAdapter;
import com.zohaltech.app.mobiledatamonitor.adapters.PackagePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PackagesActivity extends EnhancedActivity{



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager pagerPackages = (ViewPager) findViewById(R.id.pagerPackages);
        pagerPackages.setAdapter(new PackagePagerAdapter(getSupportFragmentManager(), PackagesActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabOperators = (TabLayout) findViewById(R.id.tabOperators);
        tabOperators.setupWithViewPager(pagerPackages);


    }



}
