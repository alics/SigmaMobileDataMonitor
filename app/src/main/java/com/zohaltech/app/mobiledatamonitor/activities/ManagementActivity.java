package com.zohaltech.app.mobiledatamonitor.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.DialogManager;
import com.zohaltech.app.mobiledatamonitor.dal.DataPackages;
import com.zohaltech.app.mobiledatamonitor.dal.PackageHistories;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;
import com.zohaltech.app.mobiledatamonitor.fragments.PackageSettingsFragment;

public class ManagementActivity extends EnhancedActivity {
    TextView             txtActivePackageDescription;
    FloatingActionButton fabActivePackageSettings;
    TextView             txtReservedPackageDescription;
    FloatingActionButton fabReservedPackageSettings;
    FloatingActionButton fabCancelReservedPackage;
    FloatingActionButton fabAddPackage;

    DataPackage activePackage;
    DataPackage reservedPackage;


    @Override
     void onCreated() {
        setContentView(R.layout.activity_management);

        PackageHistory activePackageHistory = PackageHistories.getActivePackage();
        PackageHistory reservedPackageHistory = PackageHistories.getReservedPackage();

        txtActivePackageDescription = (TextView) findViewById(R.id.txtActivePackageDescription);
        fabActivePackageSettings = (FloatingActionButton) findViewById(R.id.fabActivePackageSettings);
        txtReservedPackageDescription = (TextView) findViewById(R.id.txtReservedPackageDescription);
        fabCancelReservedPackage = (FloatingActionButton) findViewById(R.id.fabCancelReservedPackage);
        fabReservedPackageSettings = (FloatingActionButton) findViewById(R.id.fabReservedPackageSettings);
        fabAddPackage = (FloatingActionButton) findViewById(R.id.fabAddPackage);

        fabAddPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                intent.putExtra(PackageSettingsFragment.INIT_MODE_KEY, PackageSettingsFragment.MODE_INSERT_CUSTOM);
                startActivity(intent);
            }
        });

        fabActivePackageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                intent.putExtra(PackageSettingsFragment.INIT_MODE_KEY, PackageSettingsFragment.MODE_SETTING_ACTIVE);
                intent.putExtra(PackageSettingsFragment.PACKAGE_ID_KEY, activePackage.getId());
                startActivity(intent);
            }
        });

        fabCancelReservedPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager.showConfirmationDialog(App.currentActivity, "حذف بسته رزرو", "بسته رزرو حذف شود؟", "بله", "خیر", null, new Runnable() {
                    @Override
                    public void run() {
                        PackageHistories.deletedReservedPackages();
                        disableReservePackage();
                    }
                });
            }
        });

        fabReservedPackageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                intent.putExtra(PackageSettingsFragment.INIT_MODE_KEY, PackageSettingsFragment.MODE_SETTING_RESERVED);
                intent.putExtra(PackageSettingsFragment.PACKAGE_ID_KEY, reservedPackage.getId());
                startActivity(intent);
            }
        });

        if (activePackageHistory == null) {
            txtActivePackageDescription.setText("بسته فعالی ثبت نشده است.");
            fabActivePackageSettings.setEnabled(false);
            setDisable(fabActivePackageSettings);

        } else {
            activePackage = DataPackages.selectPackageById(activePackageHistory.getDataPackageId());
            if (activePackage != null)
                txtActivePackageDescription.setText(activePackage.getDescription());
        }

        if (reservedPackageHistory == null) {
            disableReservePackage();

        } else {
            reservedPackage = DataPackages.selectPackageById(reservedPackageHistory.getDataPackageId());
            if (reservedPackage != null)
                txtReservedPackageDescription.setText(reservedPackage.getDescription());
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
        txtToolbarTitle.setText("مدیریت بسته");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void disableReservePackage() {
        txtReservedPackageDescription.setText("بسته رزروی ثبت نشده است.");
        fabCancelReservedPackage.setEnabled(false);
        setDisable(fabCancelReservedPackage);
        fabReservedPackageSettings.setEnabled(false);
        setDisable(fabReservedPackageSettings);
    }

    private void setDisable(View view) {
        AlphaAnimation alpha = new AlphaAnimation(0.3F, 0.3F);
        alpha.setDuration(0);
        alpha.setFillAfter(true);
        view.startAnimation(alpha);
    }
}
