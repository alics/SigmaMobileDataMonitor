package com.zohaltech.app.sigma.activities;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.DialogManager;
import com.zohaltech.app.sigma.dal.DataPackages;
import com.zohaltech.app.sigma.dal.PackageHistories;
import com.zohaltech.app.sigma.entities.DataPackage;
import com.zohaltech.app.sigma.entities.PackageHistory;

public class ManagementActivity extends EnhancedActivity {
    TextView             txtActivePackageDescription;
    FloatingActionButton fabActivePackageSettings;
    TextView             txtReservedPackageDescription;
    FloatingActionButton fabReservedPackageSettings;
    //FloatingActionButton fabCancelReservedPackage;
    FloatingActionButton fabActivateReservedPackage;
    FloatingActionButton fabAddPackage;

    DataPackage activePackage;
    DataPackage reservedPackage;

    @Override
    void onCreated() {
        setContentView(R.layout.activity_management);

        txtActivePackageDescription = (TextView) findViewById(R.id.txtActivePackageDescription);
        fabActivePackageSettings = (FloatingActionButton) findViewById(R.id.fabActivePackageSettings);
        txtReservedPackageDescription = (TextView) findViewById(R.id.txtReservedPackageDescription);
        fabReservedPackageSettings = (FloatingActionButton) findViewById(R.id.fabReservedPackageSettings);
        //fabCancelReservedPackage = (FloatingActionButton) findViewById(R.id.fabCancelReservedPackage);
        fabActivateReservedPackage = (FloatingActionButton) findViewById(R.id.fabActivateReservedPackage);
        fabAddPackage = (FloatingActionButton) findViewById(R.id.fabAddPackage);

        fabActivePackageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                intent.putExtra(PackageSettingsActivity.INIT_MODE_KEY, PackageSettingsActivity.MODE_SETTING_ACTIVE);
                intent.putExtra(PackageSettingsActivity.PACKAGE_ID_KEY, activePackage.getId());
                intent.putExtra(PackageSettingsActivity.FORM_MODE_KEY, PackageSettingsActivity.FORM_MODE_EDIT);
                startActivity(intent);
            }
        });

        //fabCancelReservedPackage.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        DialogManager.showConfirmationDialog(App.currentActivity, "حذف بسته رزرو", "بسته رزرو حذف شود؟", "بله", "خیر", null, new Runnable() {
        //            @Override
        //            public void run() {
        //                PackageHistories.deletedReservedPackages();
        //                setReservePackageStatus(false);
        //            }
        //        });
        //    }
        //});

        fabReservedPackageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                intent.putExtra(PackageSettingsActivity.INIT_MODE_KEY, PackageSettingsActivity.MODE_SETTING_RESERVED);
                intent.putExtra(PackageSettingsActivity.PACKAGE_ID_KEY, reservedPackage.getId());
                intent.putExtra(PackageSettingsActivity.FORM_MODE_KEY, PackageSettingsActivity.FORM_MODE_EDIT);
                startActivity(intent);
            }
        });

        fabActivateReservedPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogManager.showConfirmationDialog(App.currentActivity,
                                                     "فعالسازی بسته رزرو", "آیا مایل به لغو بسته فعال و فعالسازی بسته رزرو هستید؟",
                                                     "بله",
                                                     "خیر",
                                                     null,
                                                     new Runnable() {
                                                         @Override
                                                         public void run() {
                                                             PackageHistory activePackage = PackageHistories.getActivePackage();
                                                             PackageHistories.finishPackageProcess(activePackage, PackageHistory.StatusEnum.CANCELED);
                                                             finish();
                                                         }
                                                     });
            }
        });

        fabAddPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                intent.putExtra(PackageSettingsActivity.INIT_MODE_KEY, PackageSettingsActivity.MODE_INSERT_CUSTOM);
                intent.putExtra(PackageSettingsActivity.FORM_MODE_KEY, PackageSettingsActivity.FORM_MODE_NEW);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        PackageHistory activePackageHistory = PackageHistories.getActivePackage();
        if (activePackageHistory == null) {
            txtActivePackageDescription.setText(getString(R.string.not_register_active_package));
            setActivePackageStatus(false);
        } else {
            activePackage = DataPackages.selectPackageById(activePackageHistory.getDataPackageId());
            txtActivePackageDescription.setText(activePackage.getTitle());
            setActivePackageStatus(true);
        }

        PackageHistory reservedPackageHistory = PackageHistories.getReservedPackage();
        if (reservedPackageHistory == null) {
            txtReservedPackageDescription.setText(getString(R.string.not_register_reserve_package));
            setReservePackageStatus(false);

        } else {
            reservedPackage = DataPackages.selectPackageById(reservedPackageHistory.getDataPackageId());
            txtReservedPackageDescription.setText(reservedPackage.getTitle());
            setReservePackageStatus(true);
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

    @Override
    void onToolbarCreated() {
        txtToolbarTitle.setText(getString(R.string.package_management));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setReservePackageStatus(boolean enable) {
        //fabCancelReservedPackage.setEnabled(enable);
        //setEnability(fabCancelReservedPackage, enable);
        fabReservedPackageSettings.setEnabled(enable);
        setEnability(fabReservedPackageSettings, enable);
    }

    private void setActivePackageStatus(boolean enable) {
        fabActivePackageSettings.setEnabled(enable);
        setEnability(fabActivePackageSettings, enable);
    }

    private void setEnability(View view, boolean enable) {
        AlphaAnimation alpha;
        if (enable) {
            alpha = new AlphaAnimation(1F, 1F);
        } else {
            alpha = new AlphaAnimation(0.3F, 0.3F);
        }
        alpha.setFillAfter(true);
        view.startAnimation(alpha);
    }
}
