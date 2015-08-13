package com.zohaltech.app.mobiledatamonitor.fragments;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.MainActivity;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.DialogManager;
import com.zohaltech.app.mobiledatamonitor.dal.DataPackages;
import com.zohaltech.app.mobiledatamonitor.dal.MobileOperators;
import com.zohaltech.app.mobiledatamonitor.dal.PackageHistories;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;
import com.zohaltech.app.mobiledatamonitor.entities.MobileOperator;

import java.util.ArrayList;

import widgets.MyFragment;

public class SettingsFragment extends MyFragment {

    EditText     txtPackageTitle;
    Spinner      spinnerOperators;
    EditText     txtPackageValidPeriod;
    EditText     txtPackagePrice;
    EditText     txtPrimaryTraffic;
    EditText     txtSecondaryTraffic;
    Spinner      spinnerTrafficUnit;
    EditText     txtSecondaryStartTime;
    EditText     txtSecondaryEndTime;
    EditText     txtAlarmTriggerVolume;
    SwitchCompat switchEnableVolumeAlarm;
    EditText     txtAlarmDaysToExpDate;
    SwitchCompat switchEnableAlarmDaysToExpDate;
    SwitchCompat switchAutoMobileDataOff;
    DataPackage  dataPackage;
    String       initMode;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        txtPackageTitle = (EditText) rootView.findViewById(R.id.txtPackageTitle);
        spinnerOperators = (Spinner) rootView.findViewById(R.id.spinnerOperators);
        txtPackageValidPeriod = (EditText) rootView.findViewById(R.id.txtPackageValidPeriod);
        txtPackagePrice = (EditText) rootView.findViewById(R.id.txtPackagePrice);
        txtPrimaryTraffic = (EditText) rootView.findViewById(R.id.txtPrimaryTraffic);
        txtSecondaryTraffic = (EditText) rootView.findViewById(R.id.txtSecondaryTraffic);
        spinnerTrafficUnit = (Spinner) rootView.findViewById(R.id.spinnerTrafficUnit);
        // txtSecondaryStartTime = (EditText) rootView.findViewById(R.id.txtSecondaryStartTime);
        //txtSecondaryEndTime = (EditText) rootView.findViewById(R.id.txtSecondaryEndTime);
        txtAlarmTriggerVolume = (EditText) rootView.findViewById(R.id.txtAlarmTriggerVolume);
        switchEnableVolumeAlarm = (SwitchCompat) rootView.findViewById(R.id.switchEnableVolumeAlarm);
        txtAlarmDaysToExpDate = (EditText) rootView.findViewById(R.id.txtAlarmDaysToExpDate);
        switchEnableAlarmDaysToExpDate = (SwitchCompat) rootView.findViewById(R.id.switchEnableAlarmDaysToExpDate);
        switchAutoMobileDataOff = (SwitchCompat) rootView.findViewById(R.id.switchAutoMobileDataOff);

        initControls();

        initMode = getArguments().getString(INIT_MODE_KEY);
        String packageId = getArguments().getString(PACKAGE_ID_KEY);

        if (packageId != null) {
            dataPackage = DataPackages.selectPackageById(Integer.valueOf(packageId));

            if (dataPackage != null) {
                txtPackageTitle.setText(dataPackage.getTitle());
                //txtOperators.setText(MobileOperators.getOperatorById(dataPackage.getOperatorId()).getName());
                txtPackageValidPeriod.setText(String.valueOf(dataPackage.getPeriod()));
                txtPackagePrice.setText(String.valueOf(dataPackage.getPrice()));
                txtPrimaryTraffic.setText(String.valueOf(dataPackage.getPrimaryTraffic()));
                txtSecondaryTraffic.setText(String.valueOf(dataPackage.getSecondaryTraffic()));
                //txtSecondaryStartTime.setText(dataPackage.getSecondaryTrafficStartTime());
                // txtSecondaryEndTime.setText(dataPackage.getSecondaryTrafficEndTime());

                if (initMode.equals(MODE_SETTING_ACTIVE)) {
                    freezePackageInformation();
                    loadActivePackageSettings();

                } else if (initMode.equals(MODE_SETTING_RESERVED)) {
                    freezePackageInformation();
                    loadReservedPackageSettings();
                }
            }
        }

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

    private void confirmButtonProcess() {
        switch (initMode) {
            case MODE_SETTING_ACTIVE:
                saveActivePackageSettings();
                break;
            case MODE_SETTING_RESERVED:
                saveReservedPackageSettings();
                break;
            case MODE_INSERT_CUSTOM:
                DialogManager.showConfirmationDialog(App.currentActivity, "فعالسازی بسته سفارشی", "با تأیید بسته سفارشی اطلاعات مربوط به بسته های فعال و رزرو شده از بین می رود، آیا انجام شود/؟",
                                                     "بله", "خیر", null, new Runnable() {
                            @Override
                            public void run() {
                                addCustomPackage();
                                saveReservedPackageSettings();
                                PackageHistories.terminateAll();
                                PackageHistories.deletedReservedPackages();
                            }
                        });

                break;
        }
    }

    private void initControls() {
        ArrayAdapter<String> operatorsAdapter;
        ArrayList<MobileOperator> operators = MobileOperators.select();
        ArrayList<String> operatorList = new ArrayList<>();

        for (int i = 0; i < operators.size(); i++) {
            operatorList.add(operators.get(i).getName());
        }

        operatorsAdapter = new ArrayAdapter<String>(App.context,
                                                    android.R.layout.simple_spinner_item, operatorList);
        operatorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOperators.setAdapter(operatorsAdapter);

        ArrayAdapter<String> unitsAdapter;
        ArrayList<String> trafficUnitList = new ArrayList<String>();
        trafficUnitList.add("Mb");
        trafficUnitList.add("Gb");

        unitsAdapter = new ArrayAdapter<String>(App.context,
                                                android.R.layout.simple_spinner_item, trafficUnitList);
        unitsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTrafficUnit.setAdapter(unitsAdapter);
    }

    private void saveActivePackageSettings() {

    }

    private void saveReservedPackageSettings() {

    }

    private void loadActivePackageSettings() {

    }

    private void loadReservedPackageSettings() {

    }

    private void addCustomPackage() {

    }

    private void freezePackageInformation() {
        txtPackageTitle.setEnabled(false);
        spinnerOperators.setEnabled(false);
        spinnerTrafficUnit.setEnabled(false);
        txtPackageValidPeriod.setEnabled(false);
        txtPackagePrice.setEnabled(false);
        txtPrimaryTraffic.setEnabled(false);
        txtSecondaryTraffic.setEnabled(false);
        // txtSecondaryStartTime.setEnabled(false);
        //txtSecondaryEndTime.setEnabled(false);
    }
}
