package com.zohaltech.app.mobiledatamonitor.activities;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;
import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.adapters.TutorialPagerAdapter;

import widgets.Slide;

public class TutorialActivity extends EnhancedActivity {
    ViewPager    viewPager;
    PagerAdapter adapter;
    String[] texts  = new String[]{"به موبایل دیتا مونیتور خوش آمدید",
                                   "با این برنامه به راحتی بسته های اینترنتی را خریداری و فعال کنید و کنترل مصرف دیتا را به دست بگیرید"};
    int[]    images = new int[]{R.drawable.ic_one, R.drawable.ic_two};

    @Override
    void onCreated() {
        setContentView(R.layout.activity_tutorial);

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pagerTutorial);
        viewPager.setPageMargin(0);
        // Pass results to ViewPagerAdapter Class
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        ParallaxPagerTransformer transformer = new ParallaxPagerTransformer(R.id.imgTutorial);
        transformer.setSpeed(-0.8F);
        viewPager.setPageTransformer(true, transformer);
        //}
        adapter = new TutorialPagerAdapter(TutorialActivity.this, texts, images);
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
