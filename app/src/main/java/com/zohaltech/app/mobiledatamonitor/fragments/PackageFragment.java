package com.zohaltech.app.mobiledatamonitor.fragments;

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

     AnimatedExpandableListView lstPeriods;
    List<String> periods ;
    HashMap<String, List<DataPackage>> dataPackages;
    ExpandablePackageAdapter packageAdapter;

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
        periods = new ArrayList<>();
        dataPackages = new HashMap<>();
        packageAdapter = new ExpandablePackageAdapter(App.currentActivity, periods, dataPackages);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_package, container, false);

        lstPeriods = (AnimatedExpandableListView) view.findViewById(R.id.lstPeriods);



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

    @Override
    public void onResume() {
        super.onResume();
        int operatorId = getArguments().getInt(POSITION);

        ArrayList<Integer> operatorPeriodList = DataPackages.selectOperatorPeriods(operatorId);
        for (int i = 0; i < operatorPeriodList.size(); i++) {
            int period = operatorPeriodList.get(i);
            periods.add(period + " روزه");
            ArrayList<DataPackage> packageList = DataPackages.selectPackagesByOperatorAndPeriod(operatorId, period);
            dataPackages.put(periods.get(i), packageList);
        }
        lstPeriods.setAdapter(packageAdapter);
        packageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        periods.clear();
        dataPackages.clear();
        packageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
    }


}