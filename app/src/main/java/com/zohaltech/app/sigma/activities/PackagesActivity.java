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
import com.zohaltech.app.sigma.dal.PackageHistories;
import com.zohaltech.app.sigma.entities.DataPackage;
import com.zohaltech.app.sigma.entities.PackageHistory;

public class PackagesActivity extends EnhancedActivity {

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
        txtToolbarTitle.setText(getString(R.string.purchase_package));

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
        if (requestCode != 0) {
            App.handler.postDelayed(new Runnable() {
                public void run() {
                    final DataPackage dataPackage = DataPackages.selectPackageById(requestCode);
                    DialogManager.showConfirmationDialog(App.currentActivity, "فعالسازی بسته", "آیا مایل به فعالسازی بسته " + dataPackage.getTitle() + " هستید؟",
                                                         "بله", "خیر", null, new Runnable() {
                                @Override
                                public void run() {
                                    final PackageHistory history = PackageHistories.getActivePackage();
                                    if (history == null) {
                                        PackageHistories.insert(new PackageHistory(dataPackage.getId(), Helper.getCurrentDateTime(), null, null, null, null, PackageHistory.StatusEnum.ACTIVE.ordinal()));
                                        Intent intent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                                        intent.putExtra(PackageSettingsActivity.INIT_MODE_KEY, PackageSettingsActivity.MODE_SETTING_ACTIVE);
                                        intent.putExtra(PackageSettingsActivity.PACKAGE_ID_KEY, dataPackage.getId());
                                        intent.putExtra(PackageSettingsActivity.FORM_MODE_KEY, PackageSettingsActivity.FORM_MODE_NEW);
                                        App.currentActivity.startActivity(intent);
                                        finish();

                                    } else {
                                        DataPackage activePackage = DataPackages.selectPackageById(history.getDataPackageId());
                                        DialogManager.showChoiceDialog(App.currentActivity, "رزرو بسته", "هم اکنون یک بسته فعال " + activePackage.getTitle() + " وجود دارد، آیا بسته " + dataPackage.getTitle() + " به عنوان بسته رزرو در نظر گرفته شود؟",
                                                                       "رزرو شود", "فعال شود", null, new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        PackageHistories.deletedReservedPackages();
                                                        PackageHistories.insert(new PackageHistory(dataPackage.getId(), null, null, null, null, null, PackageHistory.StatusEnum.RESERVED.ordinal()));
                                                        Intent intent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                                                        intent.putExtra(PackageSettingsActivity.INIT_MODE_KEY, PackageSettingsActivity.MODE_SETTING_RESERVED);
                                                        intent.putExtra(PackageSettingsActivity.PACKAGE_ID_KEY, dataPackage.getId());
                                                        intent.putExtra(PackageSettingsActivity.FORM_MODE_KEY, PackageSettingsActivity.FORM_MODE_NEW);
                                                        App.currentActivity.startActivity(intent);
                                                        finish();
                                                    }

                                                }, new Runnable() {
                                                    public void run() {
                                                        PackageHistories.deletedReservedPackages();
                                                        //PackageHistories.terminateAll(PackageHistory.StatusEnum.CANCELED);
                                                        PackageHistories.finishPackageProcess(history, PackageHistory.StatusEnum.CANCELED);
                                                        PackageHistories.insert(new PackageHistory(dataPackage.getId(), Helper.getCurrentDateTime(), null, null, null, null, PackageHistory.StatusEnum.ACTIVE.ordinal()));
                                                        Intent intent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                                                        intent.putExtra(PackageSettingsActivity.INIT_MODE_KEY, PackageSettingsActivity.MODE_SETTING_ACTIVE);
                                                        intent.putExtra(PackageSettingsActivity.PACKAGE_ID_KEY, dataPackage.getId());
                                                        intent.putExtra(PackageSettingsActivity.FORM_MODE_KEY, PackageSettingsActivity.FORM_MODE_NEW);
                                                        App.currentActivity.startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }, 1000);
        }
    }
}
