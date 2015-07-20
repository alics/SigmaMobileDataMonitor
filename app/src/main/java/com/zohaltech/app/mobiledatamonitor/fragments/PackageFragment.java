package com.zohaltech.app.mobiledatamonitor.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.adapters.ExpandablePackageAdapter;
import com.zohaltech.app.mobiledatamonitor.classes.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import widgets.AnimatedExpandableListView;

public class PackageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    ExpandablePackageAdapter packageAdapter;
    AnimatedExpandableListView                               lstPeriods;
    List<String>                                             listDataHeader;
    HashMap<String, List<String>>                            listDataChild;

    private int mPage;

    public static PackageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PackageFragment fragment = new PackageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_package, container, false);
        //TextView textView = (TextView) view;
        //textView.setText("Fragment #" + mPage);


        // get the listview
        lstPeriods = (AnimatedExpandableListView) view.findViewById(R.id.lstPeriods);

        // preparing list data
        prepareListData(mPage);

        packageAdapter = new ExpandablePackageAdapter(App.currentActivity, listDataHeader, listDataChild);

        // setting list adapter
        lstPeriods.setAdapter(packageAdapter);

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


    private void prepareListData(int mPage) {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }
}
