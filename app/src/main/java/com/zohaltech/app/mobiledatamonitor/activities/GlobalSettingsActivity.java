package com.zohaltech.app.mobiledatamonitor.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.ConnectionManager;
import com.zohaltech.app.mobiledatamonitor.dal.Settings;
import com.zohaltech.app.mobiledatamonitor.entities.Setting;

public class GlobalSettingsActivity extends EnhancedActivity {

    SwitchCompat switchShowNotification;
    SwitchCompat switchShowNotificationWhenDataIsOn;
    SwitchCompat switchShowDownUpSpeed;
    LinearLayout layoutLockScreen;
    SwitchCompat switchShowNotificationInLockScreen;
    LinearLayout layoutPremium;
    LinearLayout layoutAbout;
    LinearLayout layoutIntroduction;
    TextView     txtAbout;

    @Override
    void onCreated() {
        setContentView(R.layout.activity_global_settings);

        switchShowNotification = (SwitchCompat) findViewById(R.id.switchShowNotification);
        switchShowNotificationWhenDataIsOn = (SwitchCompat) findViewById(R.id.switchShowNotificationWhenDataIsOn);
        switchShowDownUpSpeed = (SwitchCompat) findViewById(R.id.switchShowDownUpSpeed);
        layoutLockScreen = (LinearLayout) findViewById(R.id.layoutLockScreen);
        switchShowNotificationInLockScreen = (SwitchCompat) findViewById(R.id.switchShowNotificationInLockScreen);
        layoutPremium = (LinearLayout) findViewById(R.id.layoutPremium);
        layoutAbout = (LinearLayout) findViewById(R.id.layoutAbout);
        layoutIntroduction = (LinearLayout) findViewById(R.id.layoutIntroduction);
        txtAbout = (TextView) findViewById(R.id.txtAbout);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            layoutLockScreen.setVisibility(View.GONE);
        }

        txtAbout.setText("درباره " + getString(R.string.app_name));

        Setting setting = Settings.getCurrentSettings();
        switchShowNotification.setChecked(setting.getShowNotification());
        switchShowNotificationWhenDataIsOn.setChecked(setting.getShowNotificationWhenDataIsOn());
        switchShowNotificationInLockScreen.setChecked(setting.getShowNotificationInLockScreen());
        switchShowDownUpSpeed.setChecked(setting.getShowUpDownSpeed());

        switchShowNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchShowDownUpSpeed.setEnabled(isChecked);
                switchShowNotificationInLockScreen.setEnabled(isChecked);
                switchShowNotificationWhenDataIsOn.setEnabled(isChecked);

                Setting setting = Settings.getCurrentSettings();
                setting.setShowNotification(isChecked);
                Settings.update(setting);
                //ZtDataService.restart(App.currentActivity);
            }
        });

        switchShowNotificationWhenDataIsOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectionManager.setDataConnectedStatus();
                Setting setting = Settings.getCurrentSettings();
                setting.setShowNotificationWhenDataIsOn(isChecked);
                Settings.update(setting);
                //ZtDataService.restart(App.currentActivity);
            }
        });

        switchShowDownUpSpeed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Setting setting = Settings.getCurrentSettings();
                setting.setShowUpDownSpeed(isChecked);
                Settings.update(setting);
                //ZtDataService.restart(App.currentActivity);
            }
        });

        switchShowNotificationInLockScreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Setting setting = Settings.getCurrentSettings();
                setting.setShowNotificationInLockScreen(isChecked);
                Settings.update(setting);
                //ZtDataService.restart(App.currentActivity);
            }
        });

        layoutPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo : bazaar payment
            }
        });

        layoutAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App.currentActivity, AboutUsActivity.class);
                startActivity(intent);
            }
        });
        layoutIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App.currentActivity, TutorialActivity.class);
                startActivity(intent);
            }
        });
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
        txtToolbarTitle.setText("تنظیمات");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
