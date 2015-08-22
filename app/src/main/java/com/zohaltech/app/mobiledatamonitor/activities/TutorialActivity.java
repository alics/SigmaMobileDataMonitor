package com.zohaltech.app.mobiledatamonitor.activities;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;
import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.adapters.TutorialPagerAdapter;

public class TutorialActivity extends EnhancedActivity {
    ViewPager    viewPager;
    PagerAdapter adapter;
    String[]     items=new String[]{"چه دافی", "چه کوونی"};
    int[]        flag =new int[]{R.drawable.ic_one, R.drawable.ic_two};

    @Override
    void onCreated() {
        setContentView(R.layout.activity_tutorial);

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pagerTutorial);
        // Pass results to ViewPagerAdapter Class
        viewPager.setPageTransformer(true, new ParallaxPagerTransformer(R.id.txtTutorialDesc));
        adapter = new TutorialPagerAdapter(TutorialActivity.this, items, flag);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);


    }

    @Override
    void onToolbarCreated() {
        txtToolbarTitle.setText("راهنمای برنامه");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
