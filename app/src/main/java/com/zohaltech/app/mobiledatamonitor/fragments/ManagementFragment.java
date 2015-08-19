package com.zohaltech.app.mobiledatamonitor.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.MainActivity;
import com.zohaltech.app.mobiledatamonitor.classes.DialogManager;
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
    FloatingActionButton fabCancelReservedPackage;
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
        fabCancelReservedPackage = (FloatingActionButton) rootView.findViewById(R.id.fabCancelReservedPackage);
        fabReservedPackageSettings = (FloatingActionButton) rootView.findViewById(R.id.fabReservedPackageSettings);
        fabAddPackage = (FloatingActionButton) rootView.findViewById(R.id.fabAddPackage);

        fabAddPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity parent = ((MainActivity) getActivity());
                parent.animType = MainActivity.AnimType.OPEN;
                Bundle bundle = new Bundle();
                bundle.putString(PackageSettingsFragment.INIT_MODE_KEY, PackageSettingsFragment.MODE_INSERT_CUSTOM);
                parent.displayView(MainActivity.EnumFragment.PACKAGE_SETTINGS, bundle);
            }
        });

        fabActivePackageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity parent = ((MainActivity) getActivity());
                parent.animType = MainActivity.AnimType.OPEN;
                Bundle bundle = new Bundle();
                bundle.putString(PackageSettingsFragment.INIT_MODE_KEY, PackageSettingsFragment.MODE_SETTING_ACTIVE);
                bundle.putString(PackageSettingsFragment.PACKAGE_ID_KEY, activePackage.getId() + "");
                parent.displayView(MainActivity.EnumFragment.PACKAGE_SETTINGS, bundle);
            }
        });

        fabCancelReservedPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager.showConfirmationDialog(getActivity(), "حذف بسته رزرو", "بسته رزرو حذف شود؟", "بله", "خیر", null, new Runnable() {
                    @Override
                    public void run() {
                        PackageHistories.deletedReservedPackages();
                        disableReservePackage();
                    }
                });
            }
        });

        fabReservedPackageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity parent = ((MainActivity) getActivity());
                parent.animType = MainActivity.AnimType.OPEN;
                Bundle bundle = new Bundle();
                bundle.putString(PackageSettingsFragment.INIT_MODE_KEY, PackageSettingsFragment.MODE_SETTING_RESERVED);
                bundle.putString(PackageSettingsFragment.PACKAGE_ID_KEY, reservedPackage.getId() + "");
                parent.displayView(MainActivity.EnumFragment.PACKAGE_SETTINGS, bundle);
            }
        });

        if (activePackageHistory == null) {
            txtActivePackageDescription.setText("بسته فعالی ثبت نشده است.");
            fabActivePackageSettings.setEnabled(false);
            setDisable(fabActivePackageSettings);

        } else {
            activePackage = DataPackages.selectPackageById(activePackageHistory.getDataPackageId());
            if (activePackage != null)
                txtActivePackageDescription.setText(activePackage.getDescription());
        }

        if (reservedPackageHistory == null) {
            disableReservePackage();

        } else {
            reservedPackage = DataPackages.selectPackageById(reservedPackageHistory.getDataPackageId());
            if (reservedPackage != null)
                txtReservedPackageDescription.setText(reservedPackage.getDescription());
        }

        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        return rootView;
    }

    private void disableReservePackage() {
        txtReservedPackageDescription.setText("بسته رزروی ثبت نشده است.");
        fabCancelReservedPackage.setEnabled(false);
        setDisable(fabCancelReservedPackage);
        fabReservedPackageSettings.setEnabled(false);
        setDisable(fabReservedPackageSettings);
    }

    private void setDisable(View view) {
        AlphaAnimation alpha = new AlphaAnimation(0.3F, 0.3F);
        alpha.setDuration(0);
        alpha.setFillAfter(true);
        view.startAnimation(alpha);
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
