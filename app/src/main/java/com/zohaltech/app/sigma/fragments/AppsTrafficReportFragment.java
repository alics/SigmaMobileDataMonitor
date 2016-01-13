package com.zohaltech.app.sigma.fragments;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.AppsTrafficReportAdapter;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.AppDataUsageMeter;
import com.zohaltech.app.sigma.classes.DialogManager;
import com.zohaltech.app.sigma.classes.Helper;
import com.zohaltech.app.sigma.classes.SolarCalendar;
import com.zohaltech.app.sigma.dal.AppsUsageLogs;
import com.zohaltech.app.sigma.entities.AppsTrafficMonitor;

import java.util.ArrayList;

public class AppsTrafficReportFragment extends Fragment {

    ListView     lstAppsTraffic;
    LinearLayout layoutProgress;
    LinearLayout layoutNothingFound;
    CheckBox     chkData;
    CheckBox     chkWifi;
    Button       btnPickDate;
    ArrayList<AppsTrafficMonitor> appsTrafficMonitors = new ArrayList<>();
    AppsTrafficReportAdapter adapter;
    AppCompatSpinner         spinnerFrom;

    String selectedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apps_traffic, container, false);

        AppDataUsageMeter.takeSnapshot();

        lstAppsTraffic = (ListView) view.findViewById(R.id.lstAppsTraffic);
        layoutProgress = (LinearLayout) view.findViewById(R.id.layoutProgress);
        layoutNothingFound = (LinearLayout) view.findViewById(R.id.layoutNothingFound);
        chkData = (CheckBox) view.findViewById(R.id.chkData);
        chkWifi = (CheckBox) view.findViewById(R.id.chkWifi);
        btnPickDate = (Button) view.findViewById(R.id.btnPickDate);
        spinnerFrom = (AppCompatSpinner) view.findViewById(R.id.spinnerFrom);

        layoutProgress.setVisibility(View.GONE);
        layoutNothingFound.setVisibility(View.GONE);

        initSpinner();

        selectedDate = Helper.getCurrentDate();
        btnPickDate.setText(SolarCalendar.getCurrentShamsiDateTime().substring(0, 10));
        populateTraffics();

        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (chkData.isChecked() == false && chkWifi.isChecked() == false) {
                    buttonView.setChecked(true);
                } else {
                    populateTraffics();
                }
            }
        };

        chkData.setOnCheckedChangeListener(onCheckedChangeListener);

        chkWifi.setOnCheckedChangeListener(onCheckedChangeListener);

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentSolarDate = SolarCalendar.getCurrentShamsiDateTime().substring(0, 10);
                String dareParts[] = currentSolarDate.split("/");
                DialogManager.showDatePickerDialog(App.currentActivity, Integer.parseInt(dareParts[0]), Integer.parseInt(dareParts[1]), Integer.parseInt(dareParts[2]), new Runnable() {
                    @Override
                    public void run() {
                        selectedDate = DialogManager.dateResult;
                        btnPickDate.setText(SolarCalendar.getShamsiDate(Helper.getDate(selectedDate)));
                        populateTraffics();
                    }
                });
            }
        });

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                populateTraffics();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                populateTraffics();
            }
        });

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_report, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().onBackPressed();
        } else if (id == R.id.action_reset) {
            showResetDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initSpinner() {
        ArrayAdapter<String> selectTypesAdapter;
        ArrayList<String> typeList = new ArrayList<>();

        typeList.add("در تاریخ");
        typeList.add("از تاریخ");

        selectTypesAdapter = new ArrayAdapter<>(App.context, R.layout.spinner_current_item, typeList);
        selectTypesAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinnerFrom.setAdapter(selectTypesAdapter);
    }

    private void populateTraffics() {
        //ReportType      reportType;
        //if (chkData.isChecked() && chkWifi.isChecked()) {
        //    reportType = ReportType.BOTH;
        //} else if (chkData.isChecked()) {
        //    reportType = ReportType.DATA;
        //} else {
        //    reportType = ReportType.WIFI;
        //}
        //appsTrafficMonitors.clear();
        //appsTrafficMonitors.addAll(AppsUsageLogs.getAppsTrafficReport(reportType, selectedDate, spinnerFrom.getSelectedItemPosition() == 0 ? RestrictionType.ON : RestrictionType.FROM));
        //adapter = new AppsTrafficReportAdapter(appsTrafficMonitors, reportType);
        //lstAppsTraffic.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

        new TrafficLoaderTask().execute();
    }

    private void showResetDialog() {
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_reset_stats);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(true);
                TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
                final com.rey.material.widget.CheckBox chkMobile = (com.rey.material.widget.CheckBox) dialog.findViewById(R.id.chkMobile);
                final com.rey.material.widget.CheckBox chkWifi = (com.rey.material.widget.CheckBox) dialog.findViewById(R.id.chkWifi);
                Button positiveButton = (Button) dialog.findViewById(R.id.positiveButton);
                Button negativeButton = (Button) dialog.findViewById(R.id.negativeButton);

                txtMessage.setText("آیا از بازنشانی آمار گزارش مصرف برنامه ها به مقادیر اولیه اطمینان دارید؟");

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (chkMobile.isChecked() || chkWifi.isChecked()) {

                            AppsUsageLogs.reset(chkMobile.isChecked(), chkWifi.isChecked());

                            populateTraffics();
                        }

                        dialog.dismiss();
                    }
                });

                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public enum ReportType {
        WIFI,
        DATA,
        BOTH
    }

    public enum RestrictionType {
        ON,
        FROM
    }

    private class TrafficLoaderTask extends AsyncTask<Void, Void, Void> {

        ReportType      reportType;
        RestrictionType restrictionType;

        @Override
        protected void onPreExecute() {
            if (chkData.isChecked() && chkWifi.isChecked()) {
                reportType = ReportType.BOTH;
            } else if (chkData.isChecked()) {
                reportType = ReportType.DATA;
            } else {
                reportType = ReportType.WIFI;
            }
            restrictionType = spinnerFrom.getSelectedItemPosition() == 0 ? RestrictionType.ON : RestrictionType.FROM;
            //adapter.clear();
            layoutProgress.setVisibility(View.VISIBLE);
            layoutNothingFound.setVisibility(View.GONE);
            lstAppsTraffic.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... params) {
            appsTrafficMonitors.clear();
            appsTrafficMonitors.addAll(AppsUsageLogs.getAppsTrafficReport(reportType, selectedDate, restrictionType));
            adapter = new AppsTrafficReportAdapter(appsTrafficMonitors, reportType);
            //lstAppsTraffic.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            layoutProgress.setVisibility(View.GONE);
            if (adapter.getCount() > 0) {
                lstAppsTraffic.setVisibility(View.VISIBLE);
            } else {
                layoutNothingFound.setVisibility(View.VISIBLE);
            }
            lstAppsTraffic.setAdapter(adapter);
        }
    }
}
