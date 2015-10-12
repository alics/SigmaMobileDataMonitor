package com.zohaltech.app.sigma.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.ConnectionManager;
import com.zohaltech.app.sigma.classes.LicenseManager;
import com.zohaltech.app.sigma.dal.Settings;
import com.zohaltech.app.sigma.entities.Setting;

public class GlobalSettingsActivity extends PaymentActivity {

    SwitchCompat switchShowNotification;
    SwitchCompat switchShowNotificationWhenDataOrWifiIsOn;
    SwitchCompat switchShowWifiUsage;
    SwitchCompat switchShowDownUpSpeed;
    LinearLayout layoutLockScreen;
    SwitchCompat switchShowNotificationInLockScreen;
    SwitchCompat switchVibrateInAlarms;
    SwitchCompat switchSoundInAlarms;
    LinearLayout layoutPremium;
    LinearLayout layoutPremiumSplitter;
    LinearLayout layoutAbout;
    LinearLayout layoutIntroduction;

    @Override
    void onCreated() {

        super.onCreated();

        setContentView(R.layout.activity_global_settings);

        switchShowNotification = (SwitchCompat) findViewById(R.id.switchShowNotification);
        switchShowNotificationWhenDataOrWifiIsOn = (SwitchCompat) findViewById(R.id.switchShowNotificationWhenDataOrWifiIsOn);
        switchShowWifiUsage = (SwitchCompat) findViewById(R.id.switchShowWifiUsage);
        switchShowDownUpSpeed = (SwitchCompat) findViewById(R.id.switchShowDownUpSpeed);
        switchVibrateInAlarms = (SwitchCompat) findViewById(R.id.switchVibrateInAlarms);
        switchSoundInAlarms = (SwitchCompat) findViewById(R.id.switchSoundInAlarms);
        layoutLockScreen = (LinearLayout) findViewById(R.id.layoutLockScreen);
        switchShowNotificationInLockScreen = (SwitchCompat) findViewById(R.id.switchShowNotificationInLockScreen);
        layoutPremium = (LinearLayout) findViewById(R.id.layoutPremium);
        layoutPremiumSplitter = (LinearLayout) findViewById(R.id.layoutPremiumSplitter);
        layoutAbout = (LinearLayout) findViewById(R.id.layoutAbout);
        layoutIntroduction = (LinearLayout) findViewById(R.id.layoutIntroduction);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            layoutLockScreen.setVisibility(View.GONE);
        }

        Setting setting = Settings.getCurrentSettings();
        switchShowNotification.setChecked(setting.getShowNotification());
        switchShowNotificationWhenDataOrWifiIsOn.setChecked(setting.getShowNotificationWhenDataOrWifiIsOn());
        switchShowWifiUsage.setChecked(setting.getShowWifiUsage());
        switchShowNotificationInLockScreen.setChecked(setting.getShowNotificationInLockScreen());
        switchShowDownUpSpeed.setChecked(setting.getShowUpDownSpeed());
        switchVibrateInAlarms.setChecked(setting.getVibrateInAlarms());
        switchSoundInAlarms.setChecked(setting.getSoundInAlarms());

        switchShowNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchShowDownUpSpeed.setEnabled(isChecked);
                switchShowNotificationInLockScreen.setEnabled(isChecked);
                switchShowNotificationWhenDataOrWifiIsOn.setEnabled(isChecked);
                switchShowWifiUsage.setEnabled(isChecked);

                Setting setting = Settings.getCurrentSettings();
                setting.setShowNotification(isChecked);
                Settings.update(setting);
            }
        });

        switchShowNotificationWhenDataOrWifiIsOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectionManager.setDataOrWifiConnectedStatus();
                Setting setting = Settings.getCurrentSettings();
                setting.setShowNotificationWhenDataOrWifiIsOn(isChecked);
                Settings.update(setting);
            }
        });

        switchShowWifiUsage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Setting setting = Settings.getCurrentSettings();
                setting.setShowWifiUsage(isChecked);
                Settings.update(setting);
            }
        });

        switchShowDownUpSpeed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Setting setting = Settings.getCurrentSettings();
                setting.setShowUpDownSpeed(isChecked);
                Settings.update(setting);
            }
        });

        switchSoundInAlarms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Setting setting = Settings.getCurrentSettings();
                setting.setSoundInAlarms(isChecked);
                Settings.update(setting);
            }
        });

        switchVibrateInAlarms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Setting setting = Settings.getCurrentSettings();
                setting.setVibrateInAlarms(isChecked);
                Settings.update(setting);
            }
        });

        switchShowNotificationInLockScreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Setting setting = Settings.getCurrentSettings();
                setting.setShowNotificationInLockScreen(isChecked);
                Settings.update(setting);
            }
        });

        layoutPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });

        layoutAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(App.currentActivity, AboutActivity.class);
                //startActivity(intent);
                Intent intent = new Intent(App.currentActivity, AppsTrafficReportActivity.class);
                startActivity(intent);

            }
        });

        layoutIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App.currentActivity, IntroductionActivity.class);
                intent.putExtra(IntroductionActivity.CALL_FROM, IntroductionActivity.FROM_GLOBAL_SETTINGS);
                startActivity(intent);
                //MyToast.show("بعدا بهت میگم " + getString(R.string.app_name) + " چیه", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LicenseManager.getLicenseStatus() == LicenseManager.Status.REGISTERED){
            updateUiToPremiumVersion();
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
        txtToolbarTitle.setText(getString(R.string.settings));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    void updateUiToPremiumVersion() {
        layoutPremium.setVisibility(View.GONE);
        layoutPremiumSplitter.setVisibility(View.GONE);
    }

    @Override
    void updateUiToTrialVersion() {
        //layoutPremium.setVisibility(View.VISIBLE);
        //layoutPremiumSplitter.setVisibility(View.VISIBLE);
    }
}
