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
import android.widget.Toast;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.MainActivity;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.DialogManager;
import com.zohaltech.app.mobiledatamonitor.classes.SettingsHandler;
import com.zohaltech.app.mobiledatamonitor.classes.TrafficUnitsUtil;
import com.zohaltech.app.mobiledatamonitor.classes.Validator;
import com.zohaltech.app.mobiledatamonitor.dal.DataPackages;
import com.zohaltech.app.mobiledatamonitor.dal.MobileOperators;
import com.zohaltech.app.mobiledatamonitor.dal.PackageHistories;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;
import com.zohaltech.app.mobiledatamonitor.entities.MobileOperator;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;

import java.util.ArrayList;

import widgets.MyFragment;
import widgets.MyToast;

public class SettingsFragment extends MyFragment {

    EditText     editTextPackageTitle;
    Spinner      spinnerOperators;
    EditText     editTextPackageValidPeriod;
    EditText     editTextPackagePrice;
    EditText     editTextPrimaryTraffic;
    EditText     editTextSecondaryTraffic;
    Button       btnSecondaryStartTime;
    Button       btnSecondaryEndTime;
    EditText     editTextTrafficAlarm;
    SwitchCompat switchTrafficAlarm;
    EditText     editTextLeftDaysAlarm;
    SwitchCompat switchLeftDaysAlarm;
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

        editTextPackageTitle = (EditText) rootView.findViewById(R.id.editTextPackageTitle);
        spinnerOperators = (Spinner) rootView.findViewById(R.id.spinnerOperators);
        editTextPackageValidPeriod = (EditText) rootView.findViewById(R.id.editTextPackageValidPeriod);
        editTextPackagePrice = (EditText) rootView.findViewById(R.id.editTextPackagePrice);
        editTextPrimaryTraffic = (EditText) rootView.findViewById(R.id.editTextPrimaryTraffic);
        editTextSecondaryTraffic = (EditText) rootView.findViewById(R.id.editTextSecondaryTraffic);
        btnSecondaryStartTime = (Button) rootView.findViewById(R.id.btnSecondaryStartTime);
        btnSecondaryEndTime = (Button) rootView.findViewById(R.id.btnSecondaryEndTime);
        editTextTrafficAlarm = (EditText) rootView.findViewById(R.id.editTextTrafficAlarm);
        switchTrafficAlarm = (SwitchCompat) rootView.findViewById(R.id.switchTrafficAlarm);
        editTextLeftDaysAlarm = (EditText) rootView.findViewById(R.id.editTextLeftDaysAlarm);
        switchLeftDaysAlarm = (SwitchCompat) rootView.findViewById(R.id.switchLeftDaysAlarm);
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
                editTextPackageTitle.setText(dataPackage.getTitle());
                spinnerOperators.setSelection(dataPackage.getOperatorId());
                editTextPackageValidPeriod.setText(String.valueOf(dataPackage.getPeriod()));
                editTextPackagePrice.setText(String.valueOf(dataPackage.getPrice()));
                editTextPrimaryTraffic.setText(String.valueOf(dataPackage.getPrimaryTraffic()));
                editTextSecondaryTraffic.setText(String.valueOf(dataPackage.getSecondaryTraffic()));
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
        Boolean trafficAlarm = switchTrafficAlarm.isChecked();
        Boolean leftDaysAlarm = switchLeftDaysAlarm.isChecked();

        if (trafficAlarm && leftDaysAlarm) {
            if (Validator.validateEditText(editTextLeftDaysAlarm, "اخطار روز باقیمانده"))
                return;
            if (Validator.validateEditText(editTextTrafficAlarm, "اخطار حجمی"))
                return;

            SettingsHandler.setAlarmType(SettingsHandler.AlarmType.Both.ordinal());
            SettingsHandler.setLeftDaysAlarm(Integer.valueOf(editTextLeftDaysAlarm.getText().toString()));
            SettingsHandler.setRemindedByteAlarm(
                    TrafficUnitsUtil.MbToByte(Integer.valueOf(editTextTrafficAlarm.getText().toString())));
        } else if (trafficAlarm) {
            if (Validator.validateEditText(editTextTrafficAlarm, "اخطار حجمی"))
                return;
            SettingsHandler.setAlarmType(SettingsHandler.AlarmType.RemindedBytes.ordinal());
            SettingsHandler.setRemindedByteAlarm(
                    TrafficUnitsUtil.MbToByte(Integer.valueOf(editTextTrafficAlarm.getText().toString())));
        } else if (leftDaysAlarm) {
            SettingsHandler.setAlarmType(SettingsHandler.AlarmType.LeftDay.ordinal());
            SettingsHandler.setLeftDaysAlarm(Integer.valueOf(editTextLeftDaysAlarm.getText().toString()));
        }
    }

    private void saveReservedPackageSettings() {
        Boolean trafficAlarm = switchTrafficAlarm.isChecked();
        Boolean leftDaysAlarm = switchLeftDaysAlarm.isChecked();

        if (trafficAlarm && leftDaysAlarm) {
            if (Validator.validateEditText(editTextLeftDaysAlarm, "اخطار روز باقیمانده"))
                return;
            if (Validator.validateEditText(editTextTrafficAlarm, "اخطار حجمی"))
                return;

            SettingsHandler.setAlarmTypeRes(SettingsHandler.AlarmType.Both.ordinal());
            SettingsHandler.setLeftDaysAlarmRes(Integer.valueOf(editTextLeftDaysAlarm.getText().toString()));
            SettingsHandler.setRemindedByteAlarmRes(
                    TrafficUnitsUtil.MbToByte(Integer.valueOf(editTextTrafficAlarm.getText().toString())));
        } else if (trafficAlarm) {
            if (Validator.validateEditText(editTextTrafficAlarm, "اخطار حجمی"))
                return;
            SettingsHandler.setAlarmTypeRes(SettingsHandler.AlarmType.RemindedBytes.ordinal());
            SettingsHandler.setRemindedByteAlarmRes(
                    TrafficUnitsUtil.MbToByte(Integer.valueOf(editTextTrafficAlarm.getText().toString())));
        } else if (leftDaysAlarm) {
            SettingsHandler.setAlarmTypeRes(SettingsHandler.AlarmType.LeftDay.ordinal());
            SettingsHandler.setLeftDaysAlarmRes(Integer.valueOf(editTextLeftDaysAlarm.getText().toString()));
        }
    }

    private void loadActivePackageSettings() {
        int alarmType = SettingsHandler.getAlarmType();
        if (alarmType == SettingsHandler.AlarmType.Both.ordinal()) {
            switchLeftDaysAlarm.setChecked(true);
            editTextLeftDaysAlarm.setText(SettingsHandler.getLeftDaysAlarm() + "");
            switchTrafficAlarm.setChecked(true);
            editTextTrafficAlarm.setText(TrafficUnitsUtil.ByteToMb(SettingsHandler.getRemindedByteAlarm()) + "");
        } else if (alarmType == SettingsHandler.AlarmType.LeftDay.ordinal()) {
            switchLeftDaysAlarm.setChecked(true);
            editTextLeftDaysAlarm.setText(SettingsHandler.getLeftDaysAlarm() + "");
        } else if (alarmType == SettingsHandler.AlarmType.RemindedBytes.ordinal()) {
            switchTrafficAlarm.setChecked(true);
            editTextTrafficAlarm.setText(TrafficUnitsUtil.ByteToMb(SettingsHandler.getRemindedByteAlarm()) + "");
        }
    }

    private void loadReservedPackageSettings() {
        int alarmType = SettingsHandler.getAlarmTypeRes();
        if (alarmType == SettingsHandler.AlarmType.Both.ordinal()) {
            switchLeftDaysAlarm.setChecked(true);
            editTextLeftDaysAlarm.setText(SettingsHandler.getLeftDaysAlarmRes() + "");
            switchTrafficAlarm.setChecked(true);
            editTextTrafficAlarm.setText(TrafficUnitsUtil.ByteToMb(SettingsHandler.getRemindedByteAlarmRes()) + "");
        } else if (alarmType == SettingsHandler.AlarmType.LeftDay.ordinal()) {
            switchLeftDaysAlarm.setChecked(true);
            editTextLeftDaysAlarm.setText(SettingsHandler.getLeftDaysAlarmRes() + "");
        } else if (alarmType == SettingsHandler.AlarmType.RemindedBytes.ordinal()) {
            switchTrafficAlarm.setChecked(true);
            editTextTrafficAlarm.setText(TrafficUnitsUtil.ByteToMb(SettingsHandler.getRemindedByteAlarmRes()) + "");
        }
    }

    private void addCustomPackage() {
        long secondaryTraffic = 0;
        String secondaryTrafficStartTime = null;
        String secondaryTrafficEndTime = null;

        if (Validator.validateEditText(editTextPackageTitle, "عنوان بسته"))
            return;
        if (Validator.validateEditText(editTextPrimaryTraffic, "حجم ترافیک اولیه"))
            return;
        if (Validator.validateEditText(editTextPackageValidPeriod, "مدت اعتبار"))
            return;
        if (Validator.validateEditText(editTextPackageTitle, "عنوان بسته"))
            return;

        if (editTextSecondaryTraffic.getText().toString().trim().length() > 0) {
            secondaryTraffic = TrafficUnitsUtil.MbToByte(Integer.valueOf(editTextSecondaryTraffic.getText().toString()));
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
        String title = editTextPackageTitle.getText().toString();
        int period = Integer.valueOf(editTextPackageValidPeriod.getText().toString());
        int price = 0;
        long primaryTraffic = TrafficUnitsUtil.MbToByte(Integer.valueOf(editTextPrimaryTraffic.getText().toString()));

        long result = DataPackages.insert(new DataPackage(operatorId,
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

    private void freezePackageInformation() {
        editTextPackageTitle.setEnabled(false);
        spinnerOperators.setEnabled(false);
        editTextPackageValidPeriod.setEnabled(false);
        editTextPackagePrice.setEnabled(false);
        editTextPrimaryTraffic.setEnabled(false);
        editTextSecondaryTraffic.setEnabled(false);
        btnSecondaryStartTime.setEnabled(false);
        btnSecondaryEndTime.setEnabled(false);
    }
}
