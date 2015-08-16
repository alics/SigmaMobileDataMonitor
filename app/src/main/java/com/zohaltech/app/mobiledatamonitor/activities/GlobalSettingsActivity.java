package com.zohaltech.app.mobiledatamonitor.activities;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.SettingsHandler;

public class GlobalSettingsActivity extends EnhancedActivity {

    SwitchCompat switchShowNotification;
    SwitchCompat switchShowNotificationWhenDataIsOn;
    SwitchCompat switchShowNotificationInLockScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_settings);

        switchShowNotification = (SwitchCompat) findViewById(R.id.switchShowNotification);
        switchShowNotificationWhenDataIsOn = (SwitchCompat) findViewById(R.id.switchShowNotificationWhenDataIsOn);
        switchShowNotificationInLockScreen = (SwitchCompat) findViewById(R.id.switchShowNotificationInLockScreen);

        switchShowNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsHandler.setShowNotification(isChecked);
            }
        });

        switchShowNotificationWhenDataIsOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsHandler.setShowNotificationWhenDataIsOn(isChecked);
            }
        });

        switchShowNotificationInLockScreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SettingsHandler.setShowNotificationInLockScreen(isChecked);
            }
        });

        initialize();
    }

    private void initialize() {
        switchShowNotification.setChecked(SettingsHandler.isShowNotification());
        switchShowNotificationWhenDataIsOn.setChecked(SettingsHandler.isShowNotificationWhenDataIsOn());
        switchShowNotificationInLockScreen.setChecked(SettingsHandler.isShowNotificationInLockScreen());
    }
}
