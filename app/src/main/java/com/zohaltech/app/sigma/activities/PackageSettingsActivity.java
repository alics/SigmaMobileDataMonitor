package com.zohaltech.app.sigma.activities;


import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.DialogManager;
import com.zohaltech.app.sigma.classes.Helper;
import com.zohaltech.app.sigma.classes.LicenseManager;
import com.zohaltech.app.sigma.classes.TrafficUnitsUtil;
import com.zohaltech.app.sigma.classes.Validator;
import com.zohaltech.app.sigma.dal.DataPackages;
import com.zohaltech.app.sigma.dal.MobileOperators;
import com.zohaltech.app.sigma.dal.PackageHistories;
import com.zohaltech.app.sigma.dal.Settings;
import com.zohaltech.app.sigma.dal.SystemSettings;
import com.zohaltech.app.sigma.entities.DataPackage;
import com.zohaltech.app.sigma.entities.MobileOperator;
import com.zohaltech.app.sigma.entities.PackageHistory;
import com.zohaltech.app.sigma.entities.Setting;
import com.zohaltech.app.sigma.entities.SystemSetting;

import java.util.ArrayList;

import widgets.MyToast;

public class PackageSettingsActivity extends PaymentActivity {

    public static final String INIT_MODE_KEY         = "INIT_MODE";
    public static final String MODE_INSERT_CUSTOM    = "INSERT_CUSTOM";
    public static final String MODE_SETTING_ACTIVE   = "SETTING_ACTIVE";
    public static final String MODE_SETTING_RESERVED = "SETTING_RESERVED";
    public static final String PACKAGE_ID_KEY        = "PACKAGE_ID";
    public static final String FORM_MODE_KEY         = "FORM_MODE";
    public static final String FORM_MODE_NEW         = "FORM_MODE_NEW";
    public static final String FORM_MODE_EDIT        = "FORM_MODE_EDIT";

    LinearLayout     layoutPremium;
    Button           btnPurchase;
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
    TextView         txtPercentTrafficAlarm;
    EditText         edtLeftDaysAlarm;
    TextView         txtLeftDaysAlarm;
    SwitchCompat     switchLeftDaysAlarm;
    DataPackage      dataPackage;
    DataPackage      customPackage;
    String           initMode;
    Setting          setting;
    String           formMode;

    @Override
    void onCreated() {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_package_settings);

        layoutPremium = (LinearLayout) findViewById(R.id.layoutPremium);
        btnPurchase = (Button) findViewById(R.id.btnPurchase);
        edtPackageTitle = (EditText) findViewById(R.id.edtPackageTitle);
        txtPackageTitle = (TextView) findViewById(R.id.txtPackageTitle);
        spinnerOperators = (AppCompatSpinner) findViewById(R.id.spinnerOperators);
        txtOperator = (TextView) findViewById(R.id.txtOperator);
        edtPackageValidPeriod = (EditText) findViewById(R.id.edtPackageValidPeriod);
        txtPackageValidPeriod = (TextView) findViewById(R.id.txtPackageValidPeriod);
        edtPrimaryTraffic = (EditText) findViewById(R.id.edtPrimaryTraffic);
        txtPrimaryTraffic = (TextView) findViewById(R.id.txtPrimaryTraffic);
        edtSecondaryTraffic = (EditText) findViewById(R.id.edtSecondaryTraffic);
        txtSecondaryTraffic = (TextView) findViewById(R.id.txtSecondaryTraffic);
        btnSecondaryStartTime = (Button) findViewById(R.id.btnSecondaryStartTime);
        txtSecondaryStartTime = (TextView) findViewById(R.id.txtSecondaryStartTime);
        btnSecondaryEndTime = (Button) findViewById(R.id.btnSecondaryEndTime);
        txtSecondaryEndTime = (TextView) findViewById(R.id.txtSecondaryEndTime);
        edtTrafficAlarm = (EditText) findViewById(R.id.edtTrafficAlarm);
        switchTrafficAlarm = (SwitchCompat) findViewById(R.id.switchTrafficAlarm);
        txtPercentTrafficAlarm = (TextView) findViewById(R.id.txtPercentTrafficAlarm);
        edtLeftDaysAlarm = (EditText) findViewById(R.id.edtLeftDaysAlarm);
        txtLeftDaysAlarm = (TextView) findViewById(R.id.txtLeftDaysAlarm);
        switchLeftDaysAlarm = (SwitchCompat) findViewById(R.id.switchLeftDaysAlarm);

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });

        implementListeners();
        initControls();

        initMode = getIntent().getStringExtra(INIT_MODE_KEY);
        formMode = getIntent().getStringExtra(FORM_MODE_KEY);

        assert initMode != null;
        if (initMode.equals(MODE_INSERT_CUSTOM)) {
            edtPackageTitle.setText(getString(R.string.custom_package));
            int operatorIndex = Helper.getOperator().ordinal();
            if (operatorIndex < 3) {
                spinnerOperators.setSelection(operatorIndex);
            } else {
                spinnerOperators.setSelection(0);
            }
            edtPackageValidPeriod.setText("30");
            edtPrimaryTraffic.setText("1024");
            edtSecondaryTraffic.setText("1024");
            btnSecondaryStartTime.setText("02:00");
            btnSecondaryEndTime.setText("07:00");
            edtTrafficAlarm.setText("85");
            edtLeftDaysAlarm.setText("1");
            switchTrafficAlarm.setChecked(true);
            switchLeftDaysAlarm.setChecked(true);
            setEditMode(true);
        } else {

            int packageId = getIntent().getIntExtra(PACKAGE_ID_KEY, 0);
            dataPackage = DataPackages.selectPackageById(packageId);

            if (dataPackage != null) {
                txtPackageTitle.setText(dataPackage.getTitle());
                txtOperator.setText(MobileOperators.getOperatorById(dataPackage.getOperatorId()).getName());
                txtPackageValidPeriod.setText(String.valueOf(dataPackage.getPeriod()));
                txtPrimaryTraffic.setText("" + TrafficUnitsUtil.ByteToMb(dataPackage.getPrimaryTraffic()));
                txtSecondaryTraffic.setText("" + TrafficUnitsUtil.ByteToMb(dataPackage.getSecondaryTraffic()));
                txtSecondaryStartTime.setText(dataPackage.getSecondaryTrafficStartTime());
                txtSecondaryEndTime.setText(dataPackage.getSecondaryTrafficEndTime());

                setEditMode(false);
                if (initMode.equals(MODE_SETTING_ACTIVE)) {
                    if (formMode.equals(FORM_MODE_NEW)) {
                        loadNewPackageSettings(dataPackage);
                    } else {
                        loadActivePackageSettings();
                    }
                } else if (initMode.equals(MODE_SETTING_RESERVED)) {
                    if (formMode.equals(FORM_MODE_NEW)) {
                        loadNewPackageSettings(dataPackage);
                    } else {
                        loadReservedPackageSettings();
                    }
                }
            }
        }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_package_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_done) {
            if (LicenseManager.getLicenseStatus() == LicenseManager.Status.REGISTERED) {
                confirm();
            } else {
                showPaymentDialog();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    void onToolbarCreated() {
        String title = "";
        switch (getIntent().getStringExtra(INIT_MODE_KEY)) {
            case MODE_SETTING_ACTIVE:
                title = getString(R.string.active_package);
                break;
            case MODE_SETTING_RESERVED:
                title = getString(R.string.reserved_package);
                break;
            case MODE_INSERT_CUSTOM:
                title = getString(R.string.custom_package);
                break;
        }
        txtToolbarTitle.setText(title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void confirm() {
        switch (initMode) {
            case MODE_SETTING_ACTIVE:
                if (saveActivePackageSettings(true))
                    finish();
                break;
            case MODE_SETTING_RESERVED:
                if (saveReservedPackageSettings())
                    finish();
                break;
            case MODE_INSERT_CUSTOM:
                addCustomPackage();
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

    private boolean saveActivePackageSettings(Boolean resetAlarm) {
        Boolean trafficAlarm = switchTrafficAlarm.isChecked();
        Boolean leftDaysAlarm = switchLeftDaysAlarm.isChecked();

        setting = Settings.getCurrentSettings();

        int validPeriod = edtPackageValidPeriod.getVisibility() == View.GONE ?
                          Integer.valueOf(txtPackageValidPeriod.getText().toString()) :
                          Integer.valueOf(edtPackageValidPeriod.getText().toString());

        if (trafficAlarm && leftDaysAlarm) {
            if (!Validator.validateEditText(edtLeftDaysAlarm, getString(R.string.left_days_alarm)))
                return false;
            if (!Validator.validateEditText(edtTrafficAlarm, getString(R.string.traffic_alarm)))
                return false;


            if (validPeriod <= Integer.valueOf(edtLeftDaysAlarm.getText().toString())) {
                MyToast.show("هشدار روز باقیمانده باید کمتر مدت اعتبار بسته باشد", Toast.LENGTH_SHORT, R.drawable.ic_warning_white);
                return false;
            }

            setting.setAlarmType(Setting.AlarmType.BOTH.ordinal());
            setting.setLeftDaysAlarm(Integer.valueOf(edtLeftDaysAlarm.getText().toString()));
            setting.setPercentTrafficAlarm(Integer.valueOf(edtTrafficAlarm.getText().toString()));
        } else if (trafficAlarm) {
            if (!Validator.validateEditText(edtTrafficAlarm, getString(R.string.traffic_alarm)))
                return false;

            setting.setAlarmType(Setting.AlarmType.REMINDED_BYTES.ordinal());
            setting.setPercentTrafficAlarm(Integer.valueOf(edtTrafficAlarm.getText().toString()));
        } else if (leftDaysAlarm) {


            if (validPeriod <= Integer.valueOf(edtLeftDaysAlarm.getText().toString())) {
                MyToast.show("هشدار روز باقیمانده باید کمتر مدت اعتبار بسته باشد", Toast.LENGTH_SHORT, R.drawable.ic_warning_white);
                return false;
            }
            setting.setAlarmType(Setting.AlarmType.LEFT_DAY.ordinal());
            setting.setLeftDaysAlarm(Integer.valueOf(edtLeftDaysAlarm.getText().toString()));
        } else
            setting.setAlarmType(Setting.AlarmType.NONE.ordinal());

        Settings.update(setting);

        if (resetAlarm) {
            SystemSetting systemSetting = SystemSettings.getCurrentSettings();
            systemSetting.setLeftDaysAlarmHasShown(false);
            systemSetting.setTrafficAlarmHasShown(false);
            systemSetting.setPrimaryTrafficFinishHasShown(false);
            systemSetting.setSecondaryTrafficFinishHasShown(false);
            SystemSettings.update(systemSetting);
        }
        return true;
    }

    private boolean saveReservedPackageSettings() {
        Boolean trafficAlarm = switchTrafficAlarm.isChecked();
        Boolean leftDaysAlarm = switchLeftDaysAlarm.isChecked();

        int validPeriod = edtPackageValidPeriod.getVisibility() == View.GONE ?
                          Integer.valueOf(txtPackageValidPeriod.getText().toString()) :
                          Integer.valueOf(edtPackageValidPeriod.getText().toString());

        setting = Settings.getCurrentSettings();

        if (trafficAlarm && leftDaysAlarm) {
            if (!Validator.validateEditText(edtLeftDaysAlarm, getString(R.string.left_days_alarm)))
                return false;
            if (!Validator.validateEditText(edtTrafficAlarm, getString(R.string.traffic_alarm)))
                return false;

            if (validPeriod <= Integer.valueOf(edtLeftDaysAlarm.getText().toString())) {
                MyToast.show("هشدار روز باقیمانده باید کمتر مدت اعتبار بسته باشد", Toast.LENGTH_SHORT, R.drawable.ic_warning_white);
                return false;
            }

            setting.setAlarmTypeRes(Setting.AlarmType.BOTH.ordinal());
            setting.setLeftDaysAlarmRes(Integer.valueOf(edtLeftDaysAlarm.getText().toString()));
            setting.setPercentTrafficAlarmRes(Integer.valueOf(edtTrafficAlarm.getText().toString()));
        } else if (trafficAlarm) {
            if (!Validator.validateEditText(edtTrafficAlarm, getString(R.string.traffic_alarm)))
                return false;

            setting.setAlarmTypeRes(Setting.AlarmType.REMINDED_BYTES.ordinal());
            setting.setPercentTrafficAlarmRes(Integer.valueOf(edtTrafficAlarm.getText().toString()));
        } else if (leftDaysAlarm) {
            if (validPeriod <= Integer.valueOf(edtLeftDaysAlarm.getText().toString())) {
                MyToast.show("هشدار روز باقیمانده باید کمتر مدت اعتبار بسته باشد", Toast.LENGTH_SHORT, R.drawable.ic_warning_white);
                return false;
            }
            setting.setAlarmTypeRes(Setting.AlarmType.LEFT_DAY.ordinal());
            setting.setLeftDaysAlarmRes(Integer.valueOf(edtLeftDaysAlarm.getText().toString()));
        } else
            setting.setAlarmTypeRes(Setting.AlarmType.NONE.ordinal());

        Settings.update(setting);
        return true;
    }

    private void loadActivePackageSettings() {
        setting = Settings.getCurrentSettings();
        int alarmType = setting.getAlarmType();
        if (alarmType == Setting.AlarmType.BOTH.ordinal()) {
            switchLeftDaysAlarm.setChecked(true);
            edtLeftDaysAlarm.setText("" + setting.getLeftDaysAlarm());
            switchTrafficAlarm.setChecked(true);
            edtTrafficAlarm.setText("" + setting.getPercentTrafficAlarm());
        } else if (alarmType == Setting.AlarmType.LEFT_DAY.ordinal()) {
            switchLeftDaysAlarm.setChecked(true);
            edtLeftDaysAlarm.setText("" + setting.getLeftDaysAlarm());
            edtTrafficAlarm.setVisibility(View.INVISIBLE);
            txtPercentTrafficAlarm.setVisibility(View.INVISIBLE);
            switchTrafficAlarm.setChecked(false);
        } else if (alarmType == Setting.AlarmType.REMINDED_BYTES.ordinal()) {
            switchTrafficAlarm.setChecked(true);
            edtTrafficAlarm.setText("" + setting.getPercentTrafficAlarm());
            edtLeftDaysAlarm.setVisibility(View.INVISIBLE);
            txtLeftDaysAlarm.setVisibility(View.INVISIBLE);
            switchLeftDaysAlarm.setChecked(false);
        } else if (alarmType == Setting.AlarmType.NONE.ordinal()) {
            switchTrafficAlarm.setChecked(false);
            edtTrafficAlarm.setVisibility(View.INVISIBLE);
            txtPercentTrafficAlarm.setVisibility(View.INVISIBLE);

            switchLeftDaysAlarm.setChecked(false);
            edtLeftDaysAlarm.setVisibility(View.INVISIBLE);
            txtLeftDaysAlarm.setVisibility(View.INVISIBLE);
        }
    }

    private void loadReservedPackageSettings() {
        setting = Settings.getCurrentSettings();
        int alarmType = setting.getAlarmTypeRes();
        if (alarmType == Setting.AlarmType.BOTH.ordinal()) {
            switchLeftDaysAlarm.setChecked(true);
            edtLeftDaysAlarm.setText("" + setting.getLeftDaysAlarmRes());
            switchTrafficAlarm.setChecked(true);
            edtTrafficAlarm.setText("" + setting.getPercentTrafficAlarmRes());
        } else if (alarmType == Setting.AlarmType.LEFT_DAY.ordinal()) {
            switchLeftDaysAlarm.setChecked(true);
            edtLeftDaysAlarm.setText("" + setting.getLeftDaysAlarmRes());
            edtTrafficAlarm.setVisibility(View.INVISIBLE);
            txtPercentTrafficAlarm.setVisibility(View.INVISIBLE);
            switchTrafficAlarm.setChecked(false);
        } else if (alarmType == Setting.AlarmType.REMINDED_BYTES.ordinal()) {
            switchTrafficAlarm.setChecked(true);
            edtTrafficAlarm.setText("" + setting.getPercentTrafficAlarmRes());
            edtLeftDaysAlarm.setVisibility(View.INVISIBLE);
            txtLeftDaysAlarm.setVisibility(View.INVISIBLE);
            switchLeftDaysAlarm.setChecked(false);
        } else if (alarmType == Setting.AlarmType.NONE.ordinal()) {
            switchTrafficAlarm.setChecked(false);
            edtTrafficAlarm.setVisibility(View.INVISIBLE);
            txtPercentTrafficAlarm.setVisibility(View.INVISIBLE);
            switchLeftDaysAlarm.setChecked(false);
            edtLeftDaysAlarm.setVisibility(View.INVISIBLE);
            txtLeftDaysAlarm.setVisibility(View.INVISIBLE);
        }
    }

    private void loadNewPackageSettings(DataPackage dataPackage) {
        switchLeftDaysAlarm.setChecked(true);
        edtLeftDaysAlarm.setText("1");
        if (dataPackage.getPeriod() == 1) {
            switchLeftDaysAlarm.setChecked(false);
        }
        switchTrafficAlarm.setChecked(true);
        edtTrafficAlarm.setText("85");
    }

    private void addCustomPackage() {
        customPackage = new DataPackage();
        String secondaryTrafficStartTime;
        String secondaryTrafficEndTime;

        if (!Validator.validateEditText(edtPackageTitle, getString(R.string.package_title)))
            return;
        if (!Validator.validateEditText(edtPackageValidPeriod, getString(R.string.validation_period)))
            return;

        if ((edtPrimaryTraffic.getText().toString().equals("0") || edtPrimaryTraffic.getText().length() == 0) &&
            (edtSecondaryTraffic.getText().toString().equals("0") || edtSecondaryTraffic.getText().length() == 0)) {
            MyToast.show("حجم اصلی و شبانه همزمان نمی توانند صفر باشند.", Toast.LENGTH_SHORT, R.drawable.ic_warning_white);
            return;
        }

        if (switchLeftDaysAlarm.isChecked() &&
            Integer.valueOf(edtLeftDaysAlarm.getText().toString()) >=
            Integer.valueOf(edtPackageValidPeriod.getText().toString())) {
            MyToast.show("هشدار روز باقیمانده باید از مدت اعتبار بسته کمتر باشد", Toast.LENGTH_SHORT, R.drawable.ic_warning_white);
            return;
        }

        if (edtSecondaryTraffic.getText().toString().trim().length() > 0 && !edtSecondaryTraffic.getText().toString().equals("0")) {
            if (btnSecondaryStartTime.getText().toString().trim().length() == 0) {
                MyToast.show("لطفا بازه مصرف شبانه را وارد کنید", Toast.LENGTH_SHORT, R.drawable.ic_warning_white);
                return;
            } else {
                secondaryTrafficStartTime = btnSecondaryStartTime.getText().toString();
                customPackage.setSecondaryTrafficStartTime(secondaryTrafficStartTime);
            }
            if (btnSecondaryStartTime.getText().toString().trim().length() == 0) {
                MyToast.show("لطفا بازه مصرف شبانه را وارد کنید", Toast.LENGTH_SHORT, R.drawable.ic_warning_white);
                return;
            } else {
                secondaryTrafficEndTime = btnSecondaryEndTime.getText().toString();
                customPackage.setSecondaryTrafficEndTime(secondaryTrafficEndTime);
            }

            if (secondaryTrafficStartTime.compareTo(secondaryTrafficEndTime) >= 0) {
                MyToast.show("بازه انتهای مصرف شبانه باید بزرگتر از بازه ابتدای آن باشد", Toast.LENGTH_SHORT, R.drawable.ic_warning_white);
                return;
            }
        }

        int operatorId = spinnerOperators.getSelectedItemPosition() + 1;
        customPackage.setOperatorId(operatorId);
        String title = edtPackageTitle.getText().toString();
        customPackage.setTitle(title);
        int period = Integer.valueOf(edtPackageValidPeriod.getText().toString());
        customPackage.setPeriod(period);
        customPackage.setPrice(0);
        Long primaryTraffic = TrafficUnitsUtil.MbToByte(edtPrimaryTraffic.getText().length() > 0 ? Integer.valueOf(edtPrimaryTraffic.getText().toString()) : 0);
        customPackage.setPrimaryTraffic(primaryTraffic);
        Long secondaryTraffic = TrafficUnitsUtil.MbToByte(edtSecondaryTraffic.getText().length() > 0 ? Integer.valueOf(edtSecondaryTraffic.getText().toString()) : 0);
        customPackage.setSecondaryTraffic(secondaryTraffic);
        customPackage.setUssdCode(null);
        customPackage.setCustom(true);

        if (PackageHistories.getActivePackage() != null) {
            DialogManager.showConfirmationDialog(App.currentActivity, "فعالسازی بسته سفارشی", "با تأیید بسته سفارشی اطلاعات مربوط به بسته های فعال و رزرو از بین می رود، آیا انجام شود؟",
                                                 "بله", "خیر", null, new Runnable() {
                        @Override
                        public void run() {
                            activate();
                        }
                    });
        } else {
            activate();
        }
    }

    private void activate() {
        PackageHistories.deletedReservedPackages();
        PackageHistory history = PackageHistories.getActivePackage();
        if (history != null) {
            PackageHistories.finishPackageProcess(history, PackageHistory.StatusEnum.CANCELED);
        }
        long result = DataPackages.insert(customPackage);
        boolean saveRes = saveActivePackageSettings(false);
        if (result != -1 && saveRes) {
            PackageHistories.insert(new PackageHistory(Integer.valueOf("" + result),
                                                       Helper.getCurrentDateTime(),
                                                       null,
                                                       null,
                                                       null,
                                                       null,
                                                       PackageHistory.StatusEnum.ACTIVE.ordinal()));
            finish();
        }
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

    private void implementListeners() {
        btnSecondaryStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSecondaryStartTime.getText().length() > 0) {
                    int hour = Integer.valueOf(btnSecondaryStartTime.getText().toString().substring(0, 2));
                    int minute = Integer.valueOf(btnSecondaryStartTime.getText().toString().substring(3, 5));
                    DialogManager.showTimePickerDialog(App.currentActivity, "انتخاب زمان", hour, minute, new Runnable() {
                        @Override
                        public void run() {
                            btnSecondaryStartTime.setText(DialogManager.timeResult);
                        }
                    });
                } else {
                    DialogManager.showTimePickerDialog(App.currentActivity, "انتخاب زمان", 2, 0, new Runnable() {
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
                    DialogManager.showTimePickerDialog(App.currentActivity, "انتخاب زمان", hour, minute, new Runnable() {
                        @Override
                        public void run() {
                            btnSecondaryEndTime.setText(DialogManager.timeResult);
                        }
                    });
                } else {
                    DialogManager.showTimePickerDialog(App.currentActivity, "انتخاب زمان", 7, 0, new Runnable() {
                        @Override
                        public void run() {
                            btnSecondaryEndTime.setText(DialogManager.timeResult);
                        }
                    });
                }
            }
        });

        edtSecondaryTraffic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 || s.toString().equals("0")) {
                    btnSecondaryStartTime.setText(null);
                    btnSecondaryStartTime.setEnabled(false);

                    btnSecondaryEndTime.setText(null);
                    btnSecondaryEndTime.setEnabled(false);
                } else {
                    btnSecondaryStartTime.setText("02:00");
                    btnSecondaryStartTime.setEnabled(true);

                    btnSecondaryEndTime.setText("07:00");
                    btnSecondaryEndTime.setEnabled(true);
                }
            }
        });

        switchTrafficAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtTrafficAlarm.setVisibility(View.VISIBLE);
                    txtPercentTrafficAlarm.setVisibility(View.VISIBLE);
                } else {
                    edtTrafficAlarm.setVisibility(View.INVISIBLE);
                    txtPercentTrafficAlarm.setVisibility(View.INVISIBLE);
                }
            }
        });

        switchLeftDaysAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edtLeftDaysAlarm.setVisibility(View.VISIBLE);
                    txtLeftDaysAlarm.setVisibility(View.VISIBLE);
                } else {
                    edtLeftDaysAlarm.setVisibility(View.INVISIBLE);
                    txtLeftDaysAlarm.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    void updateUiToPremiumVersion() {
        layoutPremium.setVisibility(View.GONE);
    }

    @Override
    void updateUiToTrialVersion() {
        layoutPremium.setVisibility(View.VISIBLE);
    }
}