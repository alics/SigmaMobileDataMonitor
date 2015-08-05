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

    ExpandablePackageAdapter           packageAdapter;
    AnimatedExpandableListView         lstPeriods;
    List<String>                       periods;
    HashMap<String, List<DataPackage>> dataPackages;

    private int position;

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
        position = getArguments().getInt(POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_package, container, false);

        // get the listview
        lstPeriods = (AnimatedExpandableListView) view.findViewById(R.id.lstPeriods);

        // preparing list data
        prepareListData(position);

        packageAdapter = new ExpandablePackageAdapter(App.currentActivity, periods, dataPackages);

        // setting list adapter
        lstPeriods.setAdapter(packageAdapter);
        packageAdapter.notifyDataSetChanged();

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
    public void onDetach() {
        super.onDetach();
    }

    private void prepareListData(int operatorId) {
        periods = new ArrayList<String>();
        dataPackages = new HashMap<String, List<DataPackage>>();

        ArrayList<Integer> operatorPeriodList = DataPackages.selectOperatorPeriods(operatorId);

        for (int i = 0; i < operatorPeriodList.size(); i++) {
            int period = operatorPeriodList.get(i);
            periods.add(period + " روزه");

            //List<DataPackage> operatorDataPackages = new ArrayList<>();
            ArrayList<DataPackage> packageList = DataPackages.selectPackagesByOperatorAndPeriod(operatorId, period);
            //for (int j = 0; j < packageList.size(); j++) {
            //    operatorDataPackages.add(packageList.get(j));
            //    dataPackages.put(periods.get(i), operatorDataPackages);
                dataPackages.put(periods.get(i), packageList);
            //}
        }


        // Adding child data
        //        periods.add("Top 250");
        //        periods.add("Now Showing");
        //        periods.add("Coming Soon..");
        //
        //        // Adding child data
        //        List<String> top250 = new ArrayList<String>();
        //        top250.add("The Shawshank Redemption");
        //        top250.add("The Godfather");
        //        top250.add("The Godfather: Part II");
        //        top250.add("Pulp Fiction");
        //        top250.add("The Good, the Bad and the Ugly");
        //        top250.add("The Dark Knight");
        //        top250.add("12 Angry Men");
        //        //
        //        List<String> nowShowing = new ArrayList<String>();
        //        nowShowing.add("The Conjuring");
        //        nowShowing.add("Despicable Me 2");
        //        nowShowing.add("Turbo");
        //        nowShowing.add("Grown Ups 2");
        //        nowShowing.add("Red 2");
        //        nowShowing.add("The Wolverine");
        //        //
        //        List<String> comingSoon = new ArrayList<String>();
        //        comingSoon.add("2 Guns");
        //        comingSoon.add("The Smurfs 2");
        //        comingSoon.add("The Spectacular Now");
        //        comingSoon.add("The Canyons");
        //        comingSoon.add("Europa Report");
        //
        //        dataPackages.put(periods.get(0), top250); // Header, Child data
        //        dataPackages.put(periods.get(1), nowShowing);
        //        dataPackages.put(periods.get(2), comingSoon);
    }
}
