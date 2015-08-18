package com.zohaltech.app.mobiledatamonitor.fragments;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
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
import android.widget.TextView;
import android.widget.Toast;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.MainActivity;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.DialogManager;
import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.classes.SolarCalendar;
import com.zohaltech.app.mobiledatamonitor.classes.TrafficUnitsUtil;
import com.zohaltech.app.mobiledatamonitor.classes.Validator;
import com.zohaltech.app.mobiledatamonitor.dal.DataPackages;
import com.zohaltech.app.mobiledatamonitor.dal.MobileOperators;
import com.zohaltech.app.mobiledatamonitor.dal.PackageHistories;
import com.zohaltech.app.mobiledatamonitor.dal.Settings;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;
import com.zohaltech.app.mobiledatamonitor.entities.MobileOperator;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;
import com.zohaltech.app.mobiledatamonitor.entities.Setting;

import java.util.ArrayList;
import java.util.Date;

import widgets.MyFragment;
import widgets.MyToast;

public class PackageSettingsFragment extends MyFragment {

    public static final String INIT_MODE_KEY         = "INIT_MODE";
    public static final String MODE_INSERT_CUSTOM    = "INSERT_CUSTOM";
    public static final String MODE_SETTING_ACTIVE   = "SETTING_ACTIVE";
    public static final String MODE_SETTING_RESERVED = "SETTING_RESERVED";
    public static final String PACKAGE_ID_KEY        = "PACKAGE_ID";

    EditText         edtPackageTitle;
    TextView         txtPackageTitle;
    AppCompatSpinner spinnerOperators;
    TextView         txtOperator;
    EditText         edtPackageValidPeriod;
    TextView         txtPackageValidPeriod;
    EditText         edtPrimaryTraffic;
    TextView         txtPrimaryTraffic;
    EditText         edtSecondaryTraffic;
    TextView         txtSecondaryTraffic;
    Button           btnSecondaryStartTime;
    TextView         txtSecondaryStartTime;
    Button           btnSecondaryEndTime;
    TextView         txtSecondaryEndTime;
    EditText         edtTrafficAlarm;
    SwitchCompat     switchTrafficAlarm;
    EditText         edtLeftDaysAlarm;
    SwitchCompat     switchLeftDaysAlarm;
    SwitchCompat     switchFinishPackageAlarm;
    SwitchCompat     switchAutoMobileDataOff;
    DataPackage      dataPackage;
    String           initMode;
    Setting          setting;

    public PackageSettingsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_package_settings, container, false);

        edtPackageTitle = (EditText) rootView.findViewById(R.id.edtPackageTitle);
        txtPackageTitle = (TextView) rootView.findViewById(R.id.txtPackageTitle);
        spinnerOperators = (AppCompatSpinner) rootView.findViewById(R.id.spinnerOperators);
        txtOperator = (TextView) rootView.findViewById(R.id.txtOperator);
        edtPackageValidPeriod = (EditText) rootView.findViewById(R.id.edtPackageValidPeriod);
        txtPackageValidPeriod = (TextView) rootView.findViewById(R.id.txtPackageValidPeriod);
        edtPrimaryTraffic = (EditText) rootView.findViewById(R.id.edtPrimaryTraffic);
        txtPrimaryTraffic = (TextView) rootView.findViewById(R.id.txtPrimaryTraffic);
        edtSecondaryTraffic = (EditText) rootView.findViewById(R.id.edtSecondaryTraffic);
        txtSecondaryTraffic = (TextView) rootView.findViewById(R.id.txtSecondaryTraffic);
        btnSecondaryStartTime = (Button) rootView.findViewById(R.id.btnSecondaryStartTime);
        txtSecondaryStartTime = (TextView) rootView.findViewById(R.id.txtSecondaryStartTime);
        btnSecondaryEndTime = (Button) rootView.findViewById(R.id.btnSecondaryEndTime);
        txtSecondaryEndTime = (TextView) rootView.findViewById(R.id.txtSecondaryEndTime);
        edtTrafficAlarm = (EditText) rootView.findViewById(R.id.edtTrafficAlarm);
        switchTrafficAlarm = (SwitchCompat) rootView.findViewById(R.id.switchTrafficAlarm);
        edtLeftDaysAlarm = (EditText) rootView.findViewById(R.id.edtLeftDaysAlarm);
        switchLeftDaysAlarm = (SwitchCompat) rootView.findViewById(R.id.switchLeftDaysAlarm);
        switchFinishPackageAlarm = (SwitchCompat) rootView.findViewById(R.id.switchFinishPackageAlarm);
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
                    DialogManager.showTimePickerDialog(getActivity(), "انتخاب زمان", 7, 0, new Runnable() {
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

        assert initMode != null;
        if (initMode.equals(MODE_INSERT_CUSTOM)) {
            edtPackageTitle.setText("بسته سفارشی");
            int operatorIndex = Helper.getOperator().ordinal();
            if (operatorIndex < 3) {
                spinnerOperators.setSelection(operatorIndex);
            } else {
                spinnerOperators.setSelection(0);
            }
            edtPackageValidPeriod.setText("10");
            edtPrimaryTraffic.setText("1024");
            edtSecondaryTraffic.setText("1024");
            btnSecondaryStartTime.setText("02:00");
            btnSecondaryEndTime.setText("07:00");
            edtTrafficAlarm.setText("900");
            edtLeftDaysAlarm.setText("2");
            setEditMode(true);
        } else {
            String packageId = getArguments().getString(PACKAGE_ID_KEY);
            dataPackage = DataPackages.selectPackageById(Integer.valueOf(packageId));

            if (dataPackage != null) {
                txtPackageTitle.setText(dataPackage.getTitle());
                txtOperator.setText(MobileOperators.getOperatorById(dataPackage.getOperatorId()).getName());
                txtPackageValidPeriod.setText(String.valueOf(dataPackage.getPeriod()));
                txtPrimaryTraffic.setText(TrafficUnitsUtil.ByteToMb(dataPackage.getPrimaryTraffic()) + "");
                txtSecondaryTraffic.setText(TrafficUnitsUtil.ByteToMb(dataPackage.getSecondaryTraffic()) + "");
                txtSecondaryStartTime.setText(dataPackage.getSecondaryTrafficStartTime());
                txtSecondaryEndTime.setText(dataPackage.getSecondaryTrafficEndTime());

                setEditMode(false);
                if (initMode.equals(MODE_SETTING_ACTIVE)) {
                    loadActivePackageSettings();

                } else if (initMode.equals(MODE_SETTING_RESERVED)) {
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
        inflater.inflate(R.menu.menu_package_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            close();
        } else if (id == R.id.action_done) {
            confirm();
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

    private void confirm() {
        switch (initMode) {
            case MODE_SETTING_ACTIVE:
                saveActivePackageSettings();
                break;
            case MODE_SETTING_RESERVED:
                saveReservedPackageSettings();
                break;
            case MODE_INSERT_CUSTOM:
                DialogManager.showConfirmationDialog(getActivity(), "فعالسازی بسته سفارشی", "با تأیید بسته سفارشی اطلاعات مربوط به بسته های فعال و رزرو شده از بین می رود، آیا انجام شود/؟",
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
        operatorsAdapter = new ArrayAdapter<>(App.context, R.layout.spinner_current_item, operatorList);
        operatorsAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerOperators.setAdapter(operatorsAdapter);
    }

    private void saveActivePackageSettings() {
        Boolean trafficAlarm = switchTrafficAlarm.isChecked();
        Boolean leftDaysAlarm = switchLeftDaysAlarm.isChecked();

        setting = Settings.getCurrentSettings();

        if (trafficAlarm && leftDaysAlarm) {
            if (Validator.validateEditText(edtLeftDaysAlarm, "اخطار روز باقیمانده"))
                return;
            if (Validator.validateEditText(edtTrafficAlarm, "اخطار حجمی"))
                return;
            setting.setAlarmType(Setting.AlarmType.BOTH.ordinal());
            setting.setLeftDaysAlarm(Integer.valueOf(edtLeftDaysAlarm.getText().toString()));
            setting.setRemindedByteAlarm(TrafficUnitsUtil.MbToByte(Integer.valueOf(edtTrafficAlarm.getText().toString())));
        } else if (trafficAlarm) {
            if (Validator.validateEditText(edtTrafficAlarm, "اخطار حجمی"))
                return;
            setting.setAlarmType(Setting.AlarmType.REMINDED_BYTES.ordinal());
            setting.setRemindedByteAlarm(TrafficUnitsUtil.MbToByte(Integer.valueOf(edtTrafficAlarm.getText().toString())));
        } else if (leftDaysAlarm) {
            setting.setAlarmType(Setting.AlarmType.LEFT_DAY.ordinal());
            setting.setLeftDaysAlarm(Integer.valueOf(edtLeftDaysAlarm.getText().toString()));
        }
        Settings.update(setting);
    }

    private void saveReservedPackageSettings() {
        Boolean trafficAlarm = switchTrafficAlarm.isChecked();
        Boolean leftDaysAlarm = switchLeftDaysAlarm.isChecked();

        setting = Settings.getCurrentSettings();

        if (trafficAlarm && leftDaysAlarm) {
            if (Validator.validateEditText(edtLeftDaysAlarm, "اخطار روز باقیمانده"))
                return;
            if (Validator.validateEditText(edtTrafficAlarm, "اخطار حجمی"))
                return;
            setting.setAlarmTypeRes(Setting.AlarmType.BOTH.ordinal());
            setting.setLeftDaysAlarmRes(Integer.valueOf(edtLeftDaysAlarm.getText().toString()));
            setting.setRemindedByteAlarmRes(TrafficUnitsUtil.MbToByte(Integer.valueOf(edtTrafficAlarm.getText().toString())));
        } else if (trafficAlarm) {
            if (Validator.validateEditText(edtTrafficAlarm, "اخطار حجمی"))
                return;
            setting.setAlarmType(Setting.AlarmType.REMINDED_BYTES.ordinal());
            setting.setRemindedByteAlarm(TrafficUnitsUtil.MbToByte(Integer.valueOf(edtTrafficAlarm.getText().toString())));
        } else if (leftDaysAlarm) {
            setting.setAlarmTypeRes(Setting.AlarmType.LEFT_DAY.ordinal());
            setting.setLeftDaysAlarmRes(Integer.valueOf(edtLeftDaysAlarm.getText().toString()));
        }
    }

    private void loadActivePackageSettings() {
        setting = Settings.getCurrentSettings();
        int alarmType = setting.getAlarmType();
        if (alarmType == Setting.AlarmType.BOTH.ordinal()) {
            switchLeftDaysAlarm.setChecked(true);
            edtLeftDaysAlarm.setText(setting.getLeftDaysAlarm() + "");
            switchTrafficAlarm.setChecked(true);
            edtTrafficAlarm.setText(TrafficUnitsUtil.ByteToMb(setting.getRemindedByteAlarm()) + "");
        } else if (alarmType == Setting.AlarmType.LEFT_DAY.ordinal()) {
            switchLeftDaysAlarm.setChecked(true);
            edtLeftDaysAlarm.setText(setting.getLeftDaysAlarm() + "");
        } else if (alarmType == Setting.AlarmType.REMINDED_BYTES.ordinal()) {
            switchTrafficAlarm.setChecked(true);
            edtTrafficAlarm.setText(TrafficUnitsUtil.ByteToMb(setting.getRemindedByteAlarm()) + "");
        }
    }

    private void loadReservedPackageSettings() {
        setting = Settings.getCurrentSettings();
        int alarmType = setting.getAlarmTypeRes();
        if (alarmType == Setting.AlarmType.BOTH.ordinal()) {
            switchLeftDaysAlarm.setChecked(true);
            edtLeftDaysAlarm.setText(setting.getLeftDaysAlarmRes() + "");
            switchTrafficAlarm.setChecked(true);
            edtTrafficAlarm.setText(TrafficUnitsUtil.ByteToMb(setting.getRemindedByteAlarmRes()) + "");
        } else if (alarmType == Setting.AlarmType.LEFT_DAY.ordinal()) {
            switchLeftDaysAlarm.setChecked(true);
            edtLeftDaysAlarm.setText(setting.getLeftDaysAlarmRes() + "");
        } else if (alarmType == Setting.AlarmType.REMINDED_BYTES.ordinal()) {
            switchTrafficAlarm.setChecked(true);
            edtTrafficAlarm.setText(TrafficUnitsUtil.ByteToMb(setting.getRemindedByteAlarmRes()) + "");
        }
    }

    private void addCustomPackage() {
        long secondaryTraffic = 0;
        String secondaryTrafficStartTime = null;
        String secondaryTrafficEndTime = null;

        if (Validator.validateEditText(edtPackageTitle, "عنوان بسته"))
            return;
        if (Validator.validateEditText(edtPrimaryTraffic, "حجم شبانه روزی"))
            return;
        if (Validator.validateEditText(edtPackageValidPeriod, "مدت اعتبار"))
            return;
        if (Validator.validateEditText(edtPackageTitle, "عنوان بسته"))
            return;

        if (edtSecondaryTraffic.getText().toString().trim().length() > 0) {
            secondaryTraffic = TrafficUnitsUtil.MbToByte(Integer.valueOf(edtSecondaryTraffic.getText().toString()));
            if (btnSecondaryStartTime.getText().toString().trim().length() == 0) {
                MyToast.show("لطفا " + "بازه مصرف شبانه" + " را وارد کنید", Toast.LENGTH_SHORT, R.drawable.ic_warning_white);
                return;
            } else
                secondaryTrafficStartTime = btnSecondaryStartTime.getText().toString();
            if (btnSecondaryStartTime.getText().toString().trim().length() == 0) {
                MyToast.show("لطفا " + "بازه مصرف شبانه" + " را وارد کنید", Toast.LENGTH_SHORT, R.drawable.ic_warning_white);
                return;
            } else
                secondaryTrafficEndTime = btnSecondaryEndTime.getText().toString();
        }

        int operatorId = spinnerOperators.getSelectedItemPosition() + 1;
        String title = edtPackageTitle.getText().toString();
        int period = Integer.valueOf(edtPackageValidPeriod.getText().toString());
        int price = 0;
        long primaryTraffic = TrafficUnitsUtil.MbToByte(Integer.valueOf(edtPrimaryTraffic.getText().toString()));

        DataPackages.insert(new DataPackage(operatorId,
                                            title,
                                            period,
                                            price,
                                            primaryTraffic,
                                            secondaryTraffic,
                                            secondaryTrafficStartTime,
                                            secondaryTrafficEndTime,
                                            null,
                                            true));
    }

    private void setEditMode(boolean isEditMode) {
        if (isEditMode) {
            edtPackageTitle.setVisibility(View.VISIBLE);
            spinnerOperators.setVisibility(View.VISIBLE);
            edtPackageValidPeriod.setVisibility(View.VISIBLE);
            edtPrimaryTraffic.setVisibility(View.VISIBLE);
            edtSecondaryTraffic.setVisibility(View.VISIBLE);
            btnSecondaryStartTime.setVisibility(View.VISIBLE);
            btnSecondaryEndTime.setVisibility(View.VISIBLE);
            txtPackageTitle.setVisibility(View.GONE);
            txtOperator.setVisibility(View.GONE);
            txtPackageValidPeriod.setVisibility(View.GONE);
            txtPrimaryTraffic.setVisibility(View.GONE);
            txtSecondaryTraffic.setVisibility(View.GONE);
            txtSecondaryStartTime.setVisibility(View.GONE);
            txtSecondaryEndTime.setVisibility(View.GONE);
        } else {
            edtPackageTitle.setVisibility(View.GONE);
            spinnerOperators.setVisibility(View.GONE);
            edtPackageValidPeriod.setVisibility(View.GONE);
            edtPrimaryTraffic.setVisibility(View.GONE);
            edtSecondaryTraffic.setVisibility(View.GONE);
            btnSecondaryStartTime.setVisibility(View.GONE);
            btnSecondaryEndTime.setVisibility(View.GONE);
            txtPackageTitle.setVisibility(View.VISIBLE);
            txtOperator.setVisibility(View.VISIBLE);
            txtPackageValidPeriod.setVisibility(View.VISIBLE);
            txtPrimaryTraffic.setVisibility(View.VISIBLE);
            txtSecondaryTraffic.setVisibility(View.VISIBLE);
            txtSecondaryStartTime.setVisibility(View.VISIBLE);
            txtSecondaryEndTime.setVisibility(View.VISIBLE);
        }
    }
}
