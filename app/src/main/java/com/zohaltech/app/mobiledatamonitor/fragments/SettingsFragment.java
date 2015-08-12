package com.zohaltech.app.mobiledatamonitor.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.MainActivity;
import com.zohaltech.app.mobiledatamonitor.dal.DataPackages;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;

import widgets.MyEditText;
import widgets.MyFragment;


public class SettingsFragment extends MyFragment {

    public static final String INIT_MODE_KEY         = "INIT_MODE";
    public static final String MODE_INSERT_CUSTOM    = "INSERT_CUSTOM";
    public static final String MODE_SETTING_ACTIVE   = "SETTING_ACTIVE";
    public static final String MODE_SETTING_RESERVED = "SETTING_RESERVED";
    public static final String PACKAGE_ID_KEY        = "PackageId";


    public SettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

//    private void setTableView(int packageId){
//        if (packageId==0){
//
//
//        }
//
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);


//        final MyEditText txtPackageTitle= (MyEditText) rootView.findViewById(R.id.txtPackageTitle);
//        final MyEditText txtOperators= (MyEditText) rootView.findViewById(R.id.txtOperators);
//        final MyEditText txtPackageValidPeriod= (MyEditText) rootView.findViewById(R.id.txtPackageValidPeriod);
//        final MyEditText txtPackagePrice= (MyEditText) rootView.findViewById(R.id.txtPackagePrice);
//        final MyEditText txtPrimaryTraffic= (MyEditText) rootView.findViewById(R.id.txtPrimaryTraffic);
//        final MyEditText txtSecondaryTraffic= (MyEditText) rootView.findViewById(R.id.txtSecondaryTraffic);
//        final Spinner spinnerTrafficUnit= (Spinner) rootView.findViewById(R.id.spinnerTrafficUnit);
//        final MyEditText txtSecondaryTrafficPeriod= (MyEditText) rootView.findViewById(R.id.txtSecondaryTrafficPeriod);
//        final MyEditText txtAlarmTriggerVolume= (MyEditText) rootView.findViewById(R.id.txtAlarmTriggerVolume);
//        final SwitchCompat switchEnableVolumeAlarm= (SwitchCompat) rootView.findViewById(R.id.switchEnableVolumeAlarm);
//        final MyEditText txtAlarmDaysToExpDate= (MyEditText) rootView.findViewById(R.id.txtAlarmDaysToExpDate);
//        final SwitchCompat switchEnableAlarmDaysToExpDate= (SwitchCompat) rootView.findViewById(R.id.switchEnableAlarmDaysToExpDate);
//        final SwitchCompat switchAutoMobileDataOff= (SwitchCompat) rootView.findViewById(R.id.switchAutoMobileDataOff);
//        final FloatingActionButton fabCancel= (FloatingActionButton) rootView.findViewById(R.id.fabCancel);
//        final FloatingActionButton fabDone= (FloatingActionButton) rootView.findViewById(R.id.fabDone);

//        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        final String initMode = getArguments().getString(INIT_MODE_KEY);
        String packageId = getArguments().getString(PACKAGE_ID_KEY);
        DataPackage dataPackage = DataPackages.selectPackageById(Integer.valueOf(packageId));



//        txtPackageTitle.setEnabled(false);
//        txtOperators.setEnabled(false);
//        txtPackageValidPeriod.setEnabled(false);
//        txtPackagePrice.setEnabled(false);
//        txtPrimaryTraffic.setEnabled(false);
//        switchAutoMobileDataOff.setEnabled(false);

        if (INIT_MODE_KEY == MODE_SETTING_ACTIVE) {



        } else if (INIT_MODE_KEY == MODE_SETTING_RESERVED) {

            //TODO  visible cancel

        } else if (INIT_MODE_KEY == MODE_INSERT_CUSTOM) {

            //TODO  visible cancel

        }
        //  String initModeKey = getArguments().getString(MODE_SETTING_ACTIVE);
        //  String initModeKey = getArguments().getString(MODE_SETTING_RESERVED);


        //        lstTraffics = (ListView) rootView.findViewById(R.id.lstTraffics);
        //
        //        trafficMonitors = DailyTrafficHistories.getMonthlyTraffic();
        //        long bytes = App.preferences.getLong(DataUsageService.DAILY_USAGE_BYTES, 0);
        //        trafficMonitors.add(0, new TrafficMonitor(bytes, Helper.getCurrentDate()));
        //        adapter = new ReportAdapter(trafficMonitors);
        //        lstTraffics.setAdapter(adapter);

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
        parent.displayView(MainActivity.EnumFragment.MANAGEMENT);
    }
}
