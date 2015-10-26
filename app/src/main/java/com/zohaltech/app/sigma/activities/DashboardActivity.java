package com.zohaltech.app.sigma.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zohaltech.app.sigma.BuildConfig;
import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.UsagePagerAdapter;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.DialogManager;
import com.zohaltech.app.sigma.classes.Helper;
import com.zohaltech.app.sigma.classes.LicenseManager;
import com.zohaltech.app.sigma.classes.LicenseStatus;
import com.zohaltech.app.sigma.classes.WebApiClient;
import com.zohaltech.app.sigma.dal.DataAccess;

import widgets.MyToast;
import widgets.MyViewPagerIndicator;

public class DashboardActivity extends EnhancedActivity {

    ViewPager            pagerUsages;
    MyViewPagerIndicator indicator;
    Button               btnPackageManagement;
    Button               btnPurchasePackage;
    Button               btnUsageReport;
    Button               btnPackagesHistory;
    Dialog               paymentDialog;

    UsagePagerAdapter usagePagerAdapter;

    long startTime;

    @Override
    void onCreated() {
        DataAccess da = new DataAccess();
        da.getReadableDB();
        da.close();

        //todo : is this line necessary
        //AppDataUsageMeter.takeSnapshot();

        setContentView(R.layout.activity_dashboard);

        if (App.uiPreferences.getBoolean(IntroductionActivity.INTRO_SHOWN, false) == false) {
            Intent intent = new Intent(this, IntroductionActivity.class);
            intent.putExtra(IntroductionActivity.CALL_FROM, IntroductionActivity.FROM_DASHBOARD);
            startActivity(intent);
            finish();
        }

        startTime = System.currentTimeMillis() - 5000;

        pagerUsages = (ViewPager) findViewById(R.id.pagerUsages);
        indicator = (MyViewPagerIndicator) findViewById(R.id.indicator);
        btnPackageManagement = (Button) findViewById(R.id.btnPackageManagement);
        btnPurchasePackage = (Button) findViewById(R.id.btnPurchasePackage);
        btnUsageReport = (Button) findViewById(R.id.btnUsageReport);
        btnPackagesHistory = (Button) findViewById(R.id.btnPackagesHistory);

        pagerUsages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicator.setPercent(positionOffset);
                indicator.setCurrentPage(position);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        indicator.setIndicatorsCount(3);

        btnPackageManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(App.currentActivity, ManagementActivity.class);
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
                Intent intent = new Intent(App.currentActivity, ReportActivity.class);
                startActivity(intent);
            }
        });

        btnPackagesHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.currentActivity, HistoryActivity.class);
                startActivity(intent);
            }
        });

        usagePagerAdapter = new UsagePagerAdapter(getSupportFragmentManager());
        pagerUsages.setAdapter(usagePagerAdapter);
        pagerUsages.setCurrentItem(1);

        WebApiClient.sendUserData();
        WebApiClient.checkForUpdate();

        if (App.uiPreferences.getBoolean("RATED", false) == false) {
            App.uiPreferences.edit().putInt("APP_RUN_COUNT", App.uiPreferences.getInt("APP_RUN_COUNT", 0) + 1).apply();
        }
    }

    @Override
    void onToolbarCreated() {
        txtToolbarTitle.setText(getString(R.string.app_name));

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LicenseStatus status = LicenseManager.getExistingLicense();
        if (status == null) {
            return;
        }
        if (status.getAppVersion().equals("" + BuildConfig.VERSION_CODE) == false) {
            String changeLog = Helper.inputStreamToString(getResources().openRawResource(R.raw.change_log));
            DialogManager.showNotificationDialog(this, "لیست تغییرات", changeLog, "خُب");
            status.setAppVersion("" + BuildConfig.VERSION_CODE);
            LicenseManager.updateLicense(status);
            return;
        }

        int runCount = App.uiPreferences.getInt("APP_RUN_COUNT", 0);
        boolean rated = App.uiPreferences.getBoolean("RATED", false);
        if (runCount != 0 && runCount % 6 == 0 && rated == false) {
            App.uiPreferences.edit().putInt("APP_RUN_COUNT", App.uiPreferences.getInt("APP_RUN_COUNT", 0) + 1).apply();
            Dialog dialog = DialogManager.getPopupDialog(this, "امتیازدهی به سیگما", "اگر سیگما برای شما مفید بوده است، آیا مایلید برای حمایت از تیم توسعه سیگما، به برنامه نظر و امتیاز بدهید؟", "بله، امتیاز میدم", "فعلا نه!", null, new Runnable() {
                @Override
                public void run() {
                    Helper.rateApp(DashboardActivity.this);
                }
            }, new Runnable() {
                @Override
                public void run() {
                    //do nothing
                }
            });
            dialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(App.currentActivity, GlobalSettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - startTime) > 2000) {
            startTime = System.currentTimeMillis();
            MyToast.show(this, getString(R.string.press_back_again_to_exit), Toast.LENGTH_SHORT);
            //Toast.makeText(App.context, getString(R.string.press_back_again_to_exit), Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
