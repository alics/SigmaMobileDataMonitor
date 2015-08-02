package com.zohaltech.app.mobiledatamonitor.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.dal.DataPackages;
import com.zohaltech.app.mobiledatamonitor.dal.PackageHistories;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;

public class PackageManagementActivity extends EnhancedActivity {


    TextView    txtActivePackageDescription;
    TextView    txtReservedPackageDescription;
    ImageButton imgBtnReservedPackageSetting;
    ImageButton imgBtnActivePackageSetting;
    ImageButton imgBtnAddPackage;

    DataPackage activePackage;
    DataPackage reservedPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_management);

        imgBtnAddPackage = (ImageButton) findViewById(R.id.imgBtnAddPackage);
        imgBtnActivePackageSetting = (ImageButton) findViewById(R.id.imgBtnActivePackageSetting);
        imgBtnReservedPackageSetting = (ImageButton) findViewById(R.id.imgBtnReservedPackageSetting);

        imgBtnAddPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                startActivity(myIntent);
            }
        });

        imgBtnActivePackageSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                myIntent.putExtra("activePackageId",activePackage.getId());
                startActivity(myIntent);
            }
        });

        imgBtnReservedPackageSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                myIntent.putExtra("reservedPackageId",reservedPackage.getId());
                startActivity(myIntent);
            }
        });
        initialize();
    }

    private void initialize() {
        txtActivePackageDescription = (TextView) findViewById(R.id.txtActivePackageDescription);
        txtReservedPackageDescription = (TextView) findViewById(R.id.txtReservedPackageDescription);

        PackageHistory activePackageHistory = PackageHistories.getActivePackage();
        PackageHistory reservedPackageHistory = PackageHistories.getReservedPackage();
        if (activePackageHistory == null) {
            txtActivePackageDescription.setText("بسته فعالی برای نمایش وجود ندارد.");
            imgBtnActivePackageSetting.setEnabled(false);

        } else {
            activePackage = DataPackages.selectPackageById(activePackageHistory.getId());
            if (activePackage != null)
                txtActivePackageDescription.setText(activePackage.getDescription());
        }

        if (reservedPackageHistory == null) {
            txtReservedPackageDescription.setText("بسته رزرو شده ای برای نمایش وجود ندارد.");
            imgBtnReservedPackageSetting.setEnabled(false);

        } else {
            reservedPackage = DataPackages.selectPackageById(reservedPackageHistory.getId());
            if (reservedPackage != null)
                txtReservedPackageDescription.setText(reservedPackage.getDescription());
        }
    }
}
