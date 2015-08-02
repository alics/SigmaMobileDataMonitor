package com.zohaltech.app.mobiledatamonitor.activities;


import android.os.Bundle;

import com.zohaltech.app.mobiledatamonitor.R;

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
    }

    private void initialize() {
        final String initMode = getIntent().getStringExtra(INIT_MODE_KEY);
        final int packageId = getIntent().getIntExtra(PACKAGE_ID_KEY, 0);

    }
}

