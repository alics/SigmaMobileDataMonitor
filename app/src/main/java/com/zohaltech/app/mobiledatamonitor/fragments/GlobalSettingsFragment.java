package com.zohaltech.app.mobiledatamonitor.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.MainActivity;
import com.zohaltech.app.mobiledatamonitor.classes.DataUsageService;
import com.zohaltech.app.mobiledatamonitor.dal.Settings;
import com.zohaltech.app.mobiledatamonitor.entities.Setting;

import widgets.MyFragment;

public class GlobalSettingsFragment extends MyFragment {

    SwitchCompat switchShowNotification;
    SwitchCompat switchShowNotificationWhenDataIsOn;
    SwitchCompat switchShowNotificationInLockScreen;
    Button       btnAboutUs;
    Setting      setting;

    public GlobalSettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //addPreferencesFromResource(R.xml.);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.activity_global_settings, container, false);

        setting = Settings.getCurrentSettings();

        btnAboutUs = (Button) rootView.findViewById(R.id.btnAboutUs);
        switchShowNotification = (SwitchCompat) rootView.findViewById(R.id.switchShowNotification);
        switchShowNotificationWhenDataIsOn = (SwitchCompat) rootView.findViewById(R.id.switchShowNotificationWhenDataIsOn);
        switchShowNotificationInLockScreen = (SwitchCompat) rootView.findViewById(R.id.switchShowNotificationInLockScreen);


        switchShowNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting.setShowNotification(isChecked);
                Settings.update(setting);
                restartService();
            }
        });

        switchShowNotificationWhenDataIsOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting.setShowNotificationWhenDataIsOn(isChecked);
                Settings.update(setting);
                restartService();
            }
        });

        switchShowNotificationInLockScreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting.setShowNotificationInLockScreen(isChecked);
                Settings.update(setting);
                restartService();
            }
        });

        btnAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity parent = ((MainActivity) getActivity());
                parent.animType = MainActivity.AnimType.OPEN;
                parent.displayView(MainActivity.EnumFragment.ABOUT_US);
            }
        });

        initControls();

        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        return rootView;
    }

    private void restartService() {
        Intent intent = new Intent(getActivity(), DataUsageService.class);
        getActivity().stopService(intent);
        getActivity().startService(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            close();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        close();
    }

    private void close() {
        MainActivity parent = ((MainActivity) getActivity());
        parent.animType = MainActivity.AnimType.CLOSE;
        parent.displayView(MainActivity.EnumFragment.DASHBOARD);
    }

    private void initControls() {
        switchShowNotification.setChecked(setting.getShowNotification());
        switchShowNotificationWhenDataIsOn.setChecked(setting.getShowNotificationWhenDataIsOn());
        switchShowNotificationInLockScreen.setChecked(setting.getShowNotificationInLockScreen());
    }
}
