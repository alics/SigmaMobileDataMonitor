package com.zohaltech.app.sigma.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.PackagePagerAdapter;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.DialogManager;
import com.zohaltech.app.sigma.classes.Helper;
import com.zohaltech.app.sigma.dal.DataPackages;

public class PackagesActivity extends PaymentActivity {

    PagerSlidingTabStrip tabOperators;
    ViewPager            pagerPackages;
    PackagePagerAdapter  packagePagerAdapter;

    @Override
    void onCreated() {

        setContentView(R.layout.activity_packages);

        // Initialize the ViewPager and set an adapter
        pagerPackages = (ViewPager) findViewById(R.id.pagerPackages);
        packagePagerAdapter = new PackagePagerAdapter(getSupportFragmentManager());
        pagerPackages.setAdapter(packagePagerAdapter);

        // Bind the tabOperators to the ViewPager
        tabOperators = (PagerSlidingTabStrip) findViewById(R.id.tabOperators);
        tabOperators.setViewPager(pagerPackages);

        pagerPackages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((TextView) ((ViewGroup) tabOperators.getChildAt(0)).getChildAt(0)).setTextColor(getResources().getColor(R.color.primary_light));
                ((TextView) ((ViewGroup) tabOperators.getChildAt(0)).getChildAt(1)).setTextColor(getResources().getColor(R.color.primary_light));
                ((TextView) ((ViewGroup) tabOperators.getChildAt(0)).getChildAt(2)).setTextColor(getResources().getColor(R.color.primary_light));
                ((TextView) ((ViewGroup) tabOperators.getChildAt(0)).getChildAt(position)).setTextColor(getResources().getColor(R.color.white));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        changeTabsFont();
        selectTabByOperator();

        super.onCreated();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    void onToolbarCreated() {
        txtToolbarTitle.setText("بسته ها");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void selectTabByOperator() {
        pagerPackages.setCurrentItem(2);
        Helper.Operator operator = Helper.getOperator();
        if (operator == Helper.Operator.IRANCELL) {
            pagerPackages.setCurrentItem(1);
        } else if (operator == Helper.Operator.RIGHTELL) {
            pagerPackages.setCurrentItem(0);
        }
    }

    private void changeTabsFont() {
        ViewGroup vg = (ViewGroup) tabOperators.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            TextView textView = (TextView) vg.getChildAt(j);
            //int tabChildCount = vgTab.getChildCount();
            //for (int i = 0; i < tabChildCount; i++) {
            //    View tabViewChild = vgTab.getChildAt(i);
            //    if (tabViewChild instanceof TextView) {
            //        TextView textView = (TextView) tabViewChild;
            textView.setWidth(App.screenWidth / 3);
            textView.setTypeface(App.persianFont);
            textView.setTextColor(getResources().getColor(R.color.primary_light));
            textView.setTextSize(14);
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 0 && requestCode != RC_REQUEST) {
            App.handler.postDelayed(new Runnable() {
                public void run() {
                    DialogManager.showPackageActivationDialog(DataPackages.selectPackageById(requestCode));
                }
            }, 1000);
        }
    }

    @Override
    void updateUiToPremiumVersion() {
        //packagePagerAdapter = null;
        //packagePagerAdapter = new PackagePagerAdapter(getSupportFragmentManager());
        packagePagerAdapter.notifyDataSetChanged();
    }

    @Override
    void updateUiToTrialVersion() {
        //do nothing
    }
}
