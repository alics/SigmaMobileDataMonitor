package com.zohaltech.app.mobiledatamonitor.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.MainActivity;
import com.zohaltech.app.mobiledatamonitor.dal.DataPackages;
import com.zohaltech.app.mobiledatamonitor.dal.PackageHistories;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;

import widgets.MyFragment;

public class ManagementFragment extends MyFragment {

    TextView             txtActivePackageDescription;
    FloatingActionButton fabActivePackageSettings;
    TextView             txtReservedPackageDescription;
    FloatingActionButton fabReservedPackageSettings;
    FloatingActionButton fabAddPackage;

    DataPackage activePackage;
    DataPackage reservedPackage;

    public ManagementFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_management, container, false);

        PackageHistory activePackageHistory = PackageHistories.getActivePackage();
        PackageHistory reservedPackageHistory = PackageHistories.getReservedPackage();

        txtActivePackageDescription = (TextView) rootView.findViewById(R.id.txtActivePackageDescription);
        fabActivePackageSettings = (FloatingActionButton) rootView.findViewById(R.id.fabActivePackageSettings);
        txtReservedPackageDescription = (TextView) rootView.findViewById(R.id.txtReservedPackageDescription);
        fabReservedPackageSettings = (FloatingActionButton) rootView.findViewById(R.id.fabReservedPackageSettings);
        fabAddPackage = (FloatingActionButton) rootView.findViewById(R.id.fabAddPackage);

        fabAddPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity parent = ((MainActivity) getActivity());
                parent.animType = MainActivity.AnimType.OPEN;
                Bundle bundle = new Bundle();
                bundle.putString(SettingsFragment.INIT_MODE_KEY, SettingsFragment.MODE_INSERT_CUSTOM);
                parent.displayView(MainActivity.EnumFragment.SETTINGS, bundle);
            }
        });

        fabActivePackageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity parent = ((MainActivity) getActivity());
                parent.animType = MainActivity.AnimType.OPEN;
                Bundle bundle = new Bundle();
                bundle.putString(SettingsFragment.INIT_MODE_KEY, SettingsFragment.MODE_SETTING_ACTIVE);
                bundle.putString(SettingsFragment.PACKAGE_ID_KEY, activePackage.getId() + "");
                parent.displayView(MainActivity.EnumFragment.SETTINGS, bundle);
            }
        });

        fabReservedPackageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity parent = ((MainActivity) getActivity());
                parent.animType = MainActivity.AnimType.OPEN;
                Bundle bundle = new Bundle();
                bundle.putString(SettingsFragment.INIT_MODE_KEY, SettingsFragment.MODE_SETTING_RESERVED);
                bundle.putString(SettingsFragment.PACKAGE_ID_KEY, reservedPackage.getId() + "");
                parent.displayView(MainActivity.EnumFragment.SETTINGS, bundle);
            }
        });

        if (activePackageHistory == null) {
            txtActivePackageDescription.setText("بسته فعالی برای نمایش وجود ندارد.");
            fabActivePackageSettings.setEnabled(false);

        } else {
            activePackage = DataPackages.selectPackageById(activePackageHistory.getDataPackageId());
            if (activePackage != null)
                txtActivePackageDescription.setText(activePackage.getDescription());
        }

        if (reservedPackageHistory == null) {
            txtReservedPackageDescription.setText("بسته رزرو شده ای برای نمایش وجود ندارد.");
            fabReservedPackageSettings.setEnabled(false);

        } else {
            reservedPackage = DataPackages.selectPackageById(reservedPackageHistory.getDataPackageId());
            if (reservedPackage != null)
                txtReservedPackageDescription.setText(reservedPackage.getDescription());
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
        parent.displayView(MainActivity.EnumFragment.DASHBOARD);
    }
}
