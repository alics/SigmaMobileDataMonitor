package com.zohaltech.app.mobiledatamonitor.activities;


import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.dal.DataPackages;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;

import widgets.MyEditText;

public class PackageSettingsActivity extends EnhancedActivity {

    public static final String INIT_MODE_KEY         = "INIT_MODE";
    public static final String MODE_INSERT_CUSTOM    = "INSERT_CUSTOM";
    public static final String MODE_SETTING_ACTIVE   = "SETTING_ACTIVE";
    public static final String MODE_SETTING_RESERVED = "SETTING_RESERVED";
    public static final String PACKAGE_ID_KEY        = "PackageId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_settings);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final MyEditText txtPackageTitle= (MyEditText) findViewById(R.id.txtPackageTitle);
        final MyEditText txtOperators= (MyEditText) findViewById(R.id.txtOperators);
        final MyEditText txtPackageValidPeriod= (MyEditText) findViewById(R.id.txtPackageValidPeriod);
        final MyEditText txtPackagePrice= (MyEditText) findViewById(R.id.txtPackagePrice);
        final MyEditText txtPrimaryTraffic= (MyEditText) findViewById(R.id.txtPrimaryTraffic);
        final MyEditText txtSecondaryTraffic= (MyEditText) findViewById(R.id.txtSecondaryTraffic);
        final Spinner spinnerTrafficUnit= (Spinner) findViewById(R.id.spinnerTrafficUnit);
        final MyEditText txtSecondaryTrafficPeriod= (MyEditText) findViewById(R.id.txtSecondaryTrafficPeriod);
        final MyEditText txtAlarmTriggerVolume= (MyEditText) findViewById(R.id.txtAlarmTriggerVolume);
        final SwitchCompat switchEnableVolumeAlarm= (SwitchCompat) findViewById(R.id.switchEnableVolumeAlarm);
        final MyEditText txtAlarmDaysToExpDate= (MyEditText) findViewById(R.id.txtAlarmDaysToExpDate);
        final SwitchCompat switchEnableAlarmDaysToExpDate= (SwitchCompat) findViewById(R.id.switchEnableAlarmDaysToExpDate);
        final SwitchCompat switchAutoMobileDataOff= (SwitchCompat) findViewById(R.id.switchAutoMobileDataOff);
        final ImageButton imgBtnCancel= (ImageButton) findViewById(R.id.imgBtnCancel);
        final ImageButton imgBtnSave= (ImageButton) findViewById(R.id.imgBtnSave);

    }

    private void initialize() {
        final String initMode = getIntent().getStringExtra(INIT_MODE_KEY);
        final int packageId = getIntent().getIntExtra(PACKAGE_ID_KEY, 0);

        DataPackage dataPackage = DataPackages.selectPackageById(packageId);

        if(INIT_MODE_KEY==MODE_SETTING_ACTIVE){
            //TODO
        }
        else if(INIT_MODE_KEY==MODE_SETTING_RESERVED){

            //TODO  visible cancel

        }

        else if(INIT_MODE_KEY==MODE_INSERT_CUSTOM){

            //TODO  visible cancel

        }


    }
}

