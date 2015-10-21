package com.zohaltech.app.sigma.activities;


import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.xgc1986.parallaxPagerTransformer.ParallaxPagerTransformer;
import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.IntroductionPagerAdapter;
import com.zohaltech.app.sigma.classes.App;

public class IntroductionActivity extends PaymentActivity {
    public static final String INTRO_SHOWN          = "INTRO_SHOWN";
    public static final String CALL_FROM            = "CALL_FROM";
    public static final String FROM_DASHBOARD       = "FROM_DASHBOARD";
    public static final String FROM_GLOBAL_SETTINGS = "FROM_GLOBAL_SETTINGS";
    ViewPager    viewPager;
    PagerAdapter adapter;
    String[]     texts;
    int[]        images;

    @Override
    void onCreated() {
        super.onCreated();

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_introduction);

        texts = new String[]{"به " + getString(R.string.app_name) + " خوش آمدید\nمدیریت و کنترل مصرف بسته های اینترنتی و اینترنت وای فای و ارائه گزارشات مصرف روزانه",
                             "با چند کلیک ساده بسته های اینترنتی همراه اول، ایرانسل و رایتل را خریداری و فعال کنید.",
                             "مشخصات بسته را مشاهده کنید و هشدارهای مربوط به بسته را تنظیم کنید. ",
                             "سوابق بسته های خریداری و فعال شده تا کنون را مشاهده کنید.",
                             "سرعت اینترنت موبایل و وای فای و مصرف روز جاری و همچنین هشدارهای سیگما را در نوار اعلان(Notification) مشاهده کنید.",
                             "گزارش مصرف اینترنت 30 روز گذشته به تفکیک موبایل و وای فای را ببینید و مجموع ترافیک مصرفی را مشاهده کنید.",
                             "استفاده از سیگما رایگان است و فقط بخش های خرید بسته و مدیریت بسته غیرفعال هستند و برای استفاده از آنها میبایست از طریق پرداخت درون برنامه ای، نسخه کامل و بدون محدودیت را خریداری نمایید.",
        };
        images = new int[]{R.drawable.ic_one, R.drawable.ic_two, R.drawable.ic_three, R.drawable.ic_four, R.drawable.ic_five, R.drawable.ic_six, R.drawable.ic_sigma};

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pagerTutorial);
        viewPager.setPageMargin(0);
        // Pass results to ViewPagerAdapter Class
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        ParallaxPagerTransformer transformer = new ParallaxPagerTransformer(R.id.imgIntro);
        transformer.setSpeed(-0.8F);
        viewPager.setPageTransformer(true, transformer);
        //}
        int pageCount = texts.length;
        if (getIntent().getStringExtra(CALL_FROM).equals(FROM_GLOBAL_SETTINGS)) {
            pageCount--;
        }

        adapter = new IntroductionPagerAdapter(IntroductionActivity.this, pageCount, texts, images);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);
    }

    @Override
    void onToolbarCreated() {
        if (getIntent().getStringExtra(CALL_FROM).equals(FROM_GLOBAL_SETTINGS)) {
            txtToolbarTitle.setText("سیگما چیست؟");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } else {
            toolbar.setVisibility(View.GONE);
        }

    }

    @Override
    void updateUiToPremiumVersion() {
        Intent intent = new Intent(App.currentActivity, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    void updateUiToTrialVersion() {

    }

    @Override
    public void onBackPressed() {
        if (getIntent().getStringExtra(CALL_FROM).equals(FROM_GLOBAL_SETTINGS)) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
