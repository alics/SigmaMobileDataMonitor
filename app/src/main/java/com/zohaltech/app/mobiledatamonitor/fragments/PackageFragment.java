package com.zohaltech.app.mobiledatamonitor.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.adapters.ExpandablePackageAdapter;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.dal.DataPackages;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import widgets.AnimatedExpandableListView;

public class PackageFragment extends Fragment {
    public static final String POSITION = "POSITION";

    AnimatedExpandableListView         lstPeriods;
    List<String>                       periods;
    HashMap<String, List<DataPackage>> dataPackages;
    ExpandablePackageAdapter           packageAdapter;

    //todo
    ///////////////////////////////////////////
    public PackageFragment() {
    }
    ///////////////////////////////////////////

    public static PackageFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        PackageFragment fragment = new PackageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_package, container, false);

        lstPeriods = (AnimatedExpandableListView) view.findViewById(R.id.lstPeriods);

        int operatorId = getArguments().getInt(POSITION);

        periods = new ArrayList<>();
        dataPackages = new HashMap<>();
        packageAdapter = new ExpandablePackageAdapter(App.currentActivity, periods, dataPackages);

        ArrayList<Integer> operatorPeriodList = DataPackages.selectOperatorPeriods(operatorId);
        for (int i = 0; i < operatorPeriodList.size(); i++) {
            int period = operatorPeriodList.get(i);
            periods.add(period + " روزه");
            ArrayList<DataPackage> packageList = DataPackages.selectPackagesByOperatorAndPeriod(operatorId, period);
            dataPackages.put(periods.get(i), packageList);
        }
        lstPeriods.setAdapter(packageAdapter);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            lstPeriods.setIndicatorBounds(App.screenWidth - GetPixelFromDpi(35), App.screenWidth - GetPixelFromDpi(5));
        } else {
            lstPeriods.setIndicatorBoundsRelative(App.screenWidth - GetPixelFromDpi(35), App.screenWidth - GetPixelFromDpi(5));
        }

        // In order to show animations, we need to use a custom click handler
        // for our ExpandableListView.
        lstPeriods.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (lstPeriods.isGroupExpanded(groupPosition)) {
                    lstPeriods.collapseGroupWithAnimation(groupPosition);
                } else {
                    lstPeriods.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });

        return view;
    }

    public int GetPixelFromDpi(float dpi) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpi * scale + 0.5f);
    }

    @Override
    public void onResume() {
        super.onResume();

        packageAdapter.notifyDataSetChanged();
    }

    //@Override
    //public void onPause() {
    //    super.onPause();
    //    periods.clear();
    //    dataPackages.clear();
    //    packageAdapter.notifyDataSetChanged();
    //}

    @Override
    public void onDetach() {
        super.onDetach();
        periods.clear();
        dataPackages.clear();
        packageAdapter.notifyDataSetChanged();
    }
}