package com.zohaltech.app.mobiledatamonitor.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.MainActivity;
import com.zohaltech.app.mobiledatamonitor.classes.SettingsHandler;

import widgets.MyFragment;

public class GlobalSettingsFragment extends MyFragment {

    SwitchCompat switchShowNotification;
    SwitchCompat switchShowNotificationWhenDataIsOn;
    SwitchCompat switchShowNotificationInLockScreen;

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
        View rootView = inflater.inflate(R.layout.fragment_global_settings, container, false);

        switchShowNotification = (SwitchCompat) rootView.findViewById(R.id.switchShowNotification);
        switchShowNotificationWhenDataIsOn = (SwitchCompat) rootView.findViewById(R.id.switchShowNotificationWhenDataIsOn);
        switchShowNotificationInLockScreen = (SwitchCompat) rootView.findViewById(R.id.switchShowNotificationInLockScreen);


        switchShowNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //SettingsHandler.setShowNotification(isChecked);
                SharedPreferences preferences = getActivity().getSharedPreferences("SHOW_NOTIFICATION", Context.MODE_PRIVATE);
                SharedPreferences.Editor edt = preferences.edit();
                edt.putBoolean("SHOW_NOTIFICATION ", false);
                edt.apply();
                edt.commit();
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

        initControls();

        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        return rootView;
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

        Context hostActivity = getActivity();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(hostActivity);
         boolean s=prefs.getBoolean("SHOW_NOTIFICATION",true);
        //switchShowNotification.setChecked(SettingsHandler.isShowNotification());
        SharedPreferences preferences = getActivity().getSharedPreferences("SHOW_NOTIFICATION", Context.MODE_PRIVATE);
        switchShowNotification.setChecked(preferences.getBoolean("SHOW_NOTIFICATION", true));
        switchShowNotificationWhenDataIsOn.setChecked(SettingsHandler.isShowNotificationWhenDataIsOn());
        switchShowNotificationInLockScreen.setChecked(SettingsHandler.isShowNotificationInLockScreen());
    }

    @Override
    public void onResume() {
        super.onResume();
        Context hostActivity = getActivity();
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(hostActivity);
       // PreferenceManager.getDefaultSharedPreferences(hostActivity).registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
      //  getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}
