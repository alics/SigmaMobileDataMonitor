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
import android.widget.Button;
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
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;

import java.util.ArrayList;

import widgets.MyFragment;

public class SettingsFragment extends MyFragment {

    EditText     txtPackageTitle;
    Spinner      spinnerOperators;
    EditText     txtPackageValidPeriod;
    EditText     txtPackagePrice;
    EditText     txtPrimaryTraffic;
    EditText     txtSecondaryTraffic;
    Button       btnSecondaryStartTime;
    Button       btnSecondaryEndTime;
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
        btnSecondaryStartTime = (Button) rootView.findViewById(R.id.btnSecondaryStartTime);
        btnSecondaryEndTime = (Button) rootView.findViewById(R.id.btnSecondaryEndTime);
        txtAlarmTriggerVolume = (EditText) rootView.findViewById(R.id.txtAlarmTriggerVolume);
        switchEnableVolumeAlarm = (SwitchCompat) rootView.findViewById(R.id.switchEnableVolumeAlarm);
        txtAlarmDaysToExpDate = (EditText) rootView.findViewById(R.id.txtAlarmDaysToExpDate);
        switchEnableAlarmDaysToExpDate = (SwitchCompat) rootView.findViewById(R.id.switchEnableAlarmDaysToExpDate);
        switchAutoMobileDataOff = (SwitchCompat) rootView.findViewById(R.id.switchAutoMobileDataOff);


        btnSecondaryStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSecondaryStartTime.getText().length() > 0) {
                    int hour = Integer.valueOf(btnSecondaryStartTime.getText().toString().substring(0, 2));
                    int minute = Integer.valueOf(btnSecondaryStartTime.getText().toString().substring(3, 5));
                    DialogManager.showTimePickerDialog(getActivity(), "انتخاب زمان", hour, minute, new Runnable() {
                        @Override
                        public void run() {
                            btnSecondaryStartTime.setText(DialogManager.timeResult);
                        }
                    });
                } else {
                    DialogManager.showTimePickerDialog(getActivity(), "انتخاب زمان", 2, 0, new Runnable() {
                        @Override
                        public void run() {
                            btnSecondaryStartTime.setText(DialogManager.timeResult);
                        }
                    });
                }
            }
        });

        btnSecondaryEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSecondaryEndTime.getText().length() > 0) {
                    int hour = Integer.valueOf(btnSecondaryEndTime.getText().toString().substring(0, 2));
                    int minute = Integer.valueOf(btnSecondaryEndTime.getText().toString().substring(3, 5));
                    DialogManager.showTimePickerDialog(getActivity(), "انتخاب زمان", hour, minute, new Runnable() {
                        @Override
                        public void run() {
                            btnSecondaryEndTime.setText(DialogManager.timeResult);
                        }
                    });
                } else {
                    DialogManager.showTimePickerDialog(getActivity(), "انتخاب زمان", 2, 0, new Runnable() {
                        @Override
                        public void run() {
                            btnSecondaryEndTime.setText(DialogManager.timeResult);
                        }
                    });
                }
            }
        });

        initControls();

        initMode = getArguments().getString(INIT_MODE_KEY);
        String packageId = getArguments().getString(PACKAGE_ID_KEY);

        if (packageId != null) {
            dataPackage = DataPackages.selectPackageById(Integer.valueOf(packageId));

            if (dataPackage != null) {
                txtPackageTitle.setText(dataPackage.getTitle());
                spinnerOperators.setSelection(dataPackage.getOperatorId());
                txtPackageValidPeriod.setText(String.valueOf(dataPackage.getPeriod()));
                txtPackagePrice.setText(String.valueOf(dataPackage.getPrice()));
                txtPrimaryTraffic.setText(String.valueOf(dataPackage.getPrimaryTraffic()));
                txtSecondaryTraffic.setText(String.valueOf(dataPackage.getSecondaryTraffic()));
                btnSecondaryStartTime.setText(dataPackage.getSecondaryTrafficStartTime());
                btnSecondaryEndTime.setText(dataPackage.getSecondaryTrafficEndTime());

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
                                PackageHistories.terminateAll(PackageHistory.StatusEnum.CANCELED);
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
        int operatorId = spinnerOperators.getSelectedItemPosition() + 1;
        String title = txtPackageTitle.getText().toString();


    }

    private void freezePackageInformation() {
        txtPackageTitle.setEnabled(false);
        spinnerOperators.setEnabled(false);
        txtPackageValidPeriod.setEnabled(false);
        txtPackagePrice.setEnabled(false);
        txtPrimaryTraffic.setEnabled(false);
        txtSecondaryTraffic.setEnabled(false);
        btnSecondaryStartTime.setEnabled(false);
        btnSecondaryEndTime.setEnabled(false);
    }


//    private DataPackage getDataPackageInstanceFromControllers(){
//        int operatorId = spinnerOperators.getSelectedItemPosition() + 1;
//        String title = txtPackageTitle.getText().toString();
//        int period=Integer.valueOf(txtPackageValidPeriod.getText().toString());
//        int price=0;
//      //  long primaryTraffic=
//
//
//
//      //  DataPackage dataPackage=new DataPackage
//
//    }

}
