package com.zohaltech.app.sigma.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.ConnectionManager;
import com.zohaltech.app.sigma.classes.LicenseManager;
import com.zohaltech.app.sigma.classes.TrafficUnitsUtil;
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
    SwitchCompat switchDailyLimitation;
    TextView     txtDailyLimitationAlarm;
    EditText     edtDailyLimitationAlarm;
    Setting      setting;


    @Override
    void onCreated() {

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
        switchDailyLimitation = (SwitchCompat) findViewById(R.id.switchDailyLimitation);
        edtDailyLimitationAlarm = (EditText) findViewById(R.id.edtDailyLimitationAlarm);
        txtDailyLimitationAlarm = (TextView) findViewById(R.id.txtDailyLimitationAlarm);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            layoutLockScreen.setVisibility(View.GONE);
        }

        setting = Settings.getCurrentSettings();

        switchShowNotification.setChecked(setting.getShowNotification());
        switchShowNotificationWhenDataOrWifiIsOn.setChecked(setting.getShowNotificationWhenDataOrWifiIsOn());
        switchShowWifiUsage.setChecked(setting.getShowWifiUsage());
        switchShowNotificationInLockScreen.setChecked(setting.getShowNotificationInLockScreen());
        switchShowDownUpSpeed.setChecked(setting.getShowUpDownSpeed());
        switchVibrateInAlarms.setChecked(setting.getVibrateInAlarms());
        switchSoundInAlarms.setChecked(setting.getSoundInAlarms());
        switchDailyLimitation.setChecked(setting.getDailyTrafficLimitationAlarm());

        if (setting.getDailyTrafficLimitationAlarm()) {
            edtDailyLimitationAlarm.setVisibility(View.VISIBLE);
            txtDailyLimitationAlarm.setVisibility(View.VISIBLE);
            edtDailyLimitationAlarm.setText("" + TrafficUnitsUtil.ByteToMb(setting.getDailyTrafficLimitation()));
        } else {
            edtDailyLimitationAlarm.setVisibility(View.INVISIBLE);
            txtDailyLimitationAlarm.setVisibility(View.INVISIBLE);
        }


        edtDailyLimitationAlarm.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                //if (edtDailyLimitationAlarm.getText().length()== 0) {
                //    MyToast.show("لطفا محدودیت حجم را وارد نمایید!", Toast.LENGTH_SHORT, R.drawable.ic_warning_white);
                //}else {
                setting.setDailyTrafficLimitationAlarm(true);
                Long value = TrafficUnitsUtil.MbToByte(Integer.valueOf(edtDailyLimitationAlarm.getText().toString()));
                setting.setDailyTrafficLimitation(value);
                Settings.update(setting);
                //  }
            }
        });


        switchDailyLimitation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtDailyLimitationAlarm.setVisibility(View.VISIBLE);
                    txtDailyLimitationAlarm.setVisibility(View.VISIBLE);
                } else {
                    edtDailyLimitationAlarm.setVisibility(View.INVISIBLE);
                    txtDailyLimitationAlarm.setVisibility(View.INVISIBLE);

                    setting.setDailyTrafficLimitationAlarm(false);
                    setting.setDailyTrafficLimitation(0L);
                    Settings.update(setting);
                }
            }
        });

        switchShowNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchShowDownUpSpeed.setEnabled(isChecked);
                switchShowNotificationInLockScreen.setEnabled(isChecked);
                switchShowNotificationWhenDataOrWifiIsOn.setEnabled(isChecked);
                switchShowWifiUsage.setEnabled(isChecked);

                //Setting setting = Settings.getCurrentSettings();
                setting.setShowNotification(isChecked);
                Settings.update(setting);
            }
        });

        switchShowNotificationWhenDataOrWifiIsOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ConnectionManager.setDataOrWifiConnectedStatus();
                // Setting setting = Settings.getCurrentSettings();
                setting.setShowNotificationWhenDataOrWifiIsOn(isChecked);
                Settings.update(setting);
            }
        });

        switchShowWifiUsage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //  Setting setting = Settings.getCurrentSettings();
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
                // Setting setting = Settings.getCurrentSettings();
                setting.setSoundInAlarms(isChecked);
                Settings.update(setting);
            }
        });

        switchVibrateInAlarms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //  Setting setting = Settings.getCurrentSettings();
                setting.setVibrateInAlarms(isChecked);
                Settings.update(setting);
            }
        });

        switchShowNotificationInLockScreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Setting setting = Settings.getCurrentSettings();
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
                Intent intent = new Intent(App.currentActivity, AboutActivity.class);
                startActivity(intent);
            }
        });

        layoutIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App.currentActivity, IntroductionActivity.class);
                intent.putExtra(IntroductionActivity.CALL_FROM, IntroductionActivity.FROM_GLOBAL_SETTINGS);
                startActivity(intent);
            }
        });

        super.onCreated();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LicenseManager.getLicenseStatus() == LicenseManager.Status.REGISTERED) {
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
        layoutPremium.setVisibility(View.VISIBLE);
        layoutPremiumSplitter.setVisibility(View.VISIBLE);
    }
}
