package com.zohaltech.app.sigma.activities;


import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;
import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.IntroductionPagerAdapter;

public class IntroductionActivity extends EnhancedActivity {
    ViewPager    viewPager;
    PagerAdapter adapter;
    String[]     texts;
    int[]        images;

    @Override
    void onCreated() {
        setContentView(R.layout.activity_introduction);

        texts = new String[]{"به " + getString(R.string.app_name) + " خوش آمدید\nبرنامه ای برای مدیریت و کنترل مصرف بسته های اینترنتی و گزارشات مصرف روزانه",
                             "با چند کلیک ساده بسته های اینترنتی همراه اول، ایرانسل و رایتل را خریداری و فعال کنید.",
                             "مشخصات بسته را مشاهده کنید و هشدارهای مربوط به بسته را تنظیم کنید. ",
                             "سوابق بسته های خریداری و فعال شده تا کنون را مشاهده کنید.",
                             "سرعت اینترنت موبایل و مصرف امروز و همچنین هشدارهای سیگما را در نوار اعلان(Notification) مشاهده کنید",
                             "گزارش مصرف اینترنت 30 روز گذشته به تفکیک هر روز و مجموع ترافیک مصرفی را مشاهده کنید.",
        };
        images = new int[]{R.drawable.ic_one, R.drawable.ic_two, R.drawable.ic_three, R.drawable.ic_four, R.drawable.ic_five, R.drawable.ic_six};

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pagerTutorial);
        viewPager.setPageMargin(0);
        // Pass results to ViewPagerAdapter Class
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        ParallaxPagerTransformer transformer = new ParallaxPagerTransformer(R.id.imgTutorial);
        transformer.setSpeed(-0.8F);
        viewPager.setPageTransformer(true, transformer);
        //}
        adapter = new IntroductionPagerAdapter(IntroductionActivity.this, texts.length, texts, images);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);
    }

    @Override
    void onToolbarCreated() {
        txtToolbarTitle.setText("سیگما چیست؟");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
