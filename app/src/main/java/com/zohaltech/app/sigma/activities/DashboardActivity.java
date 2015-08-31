package com.zohaltech.app.sigma.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.UsagePagerAdapter;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.DialogManager;
import com.zohaltech.app.sigma.classes.LicenseManager;
import com.zohaltech.app.sigma.dal.DataAccess;

import widgets.MyToast;
import widgets.MyViewPagerIndicator;

public class DashboardActivity extends BazaarPaymentActivity {

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

        setContentView(R.layout.activity_dashboard);

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

        super.onCreated();
    }

    @Override
    void onToolbarCreated() {
        txtToolbarTitle.setText(getString(R.string.app_name));

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    @Override
    void updateUiToPremiumVersion() {
        destroyPaymentDialog();
    }

    @Override
    void updateUiToTrialVersion() {
        showPaymentDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LicenseManager.getLicenseStatus() == LicenseManager.Status.EXPIRED) {
            showPaymentDialog();
        }
    }

    private void showPaymentDialog() {
        destroyPaymentDialog();
        paymentDialog = DialogManager.getPopupDialog(App.currentActivity,
                                                     getString(R.string.buy_full_vesion),
                                                     getString(R.string.buy_description),
                                                     getString(R.string.buy_like),
                                                     getString(R.string.buy_sora),
                                                     null,
                                                     new Runnable() {
                                                         @Override
                                                         public void run() {
                                                             pay();
                                                         }
                                                     },
                                                     new Runnable() {
                                                         @Override
                                                         public void run() {
                                                             finish();
                                                         }
                                                     });
        paymentDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        paymentDialog.show();
    }

    private void destroyPaymentDialog() {
        if (paymentDialog != null) {
            if (paymentDialog.isShowing()) {
                paymentDialog.dismiss();
            }
            paymentDialog = null;
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
