package com.zohaltech.app.mobiledatamonitor.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.MainActivity;
import com.zohaltech.app.mobiledatamonitor.adapters.PackagePagerAdapter;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.Helper;

import widgets.MyFragment;

public class PackagesFragment extends MyFragment {

    TabLayout tabOperators;
    ViewPager pagerPackages;
    PackagePagerAdapter packagePagerAdapter;

    public PackagesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_packages, container, false);

        // Give the TabLayout the ViewPager
        tabOperators = (TabLayout) rootView.findViewById(R.id.tabOperators);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        pagerPackages = (ViewPager) rootView.findViewById(R.id.pagerPackages);
        packagePagerAdapter = new PackagePagerAdapter(getActivity().getSupportFragmentManager(), App.context);
        pagerPackages.setAdapter(packagePagerAdapter);
        packagePagerAdapter.notifyDataSetChanged();

        //((ViewGroup)tabOperators.getChildAt(0)).getChildAt().setTypeface(App.persianFont);
        tabOperators.setupWithViewPager(pagerPackages);

        selectTabByOperator();

        changeTabsFont();

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

    @Override
    public void onDetach() {
        super.onDetach();
        packagePagerAdapter = null;
        pagerPackages.setAdapter(null);
    }

    private void close() {
        MainActivity parent = ((MainActivity) getActivity());
        parent.animType = MainActivity.AnimType.CLOSE;
        parent.displayView(MainActivity.EnumFragment.DASHBOARD);
    }

    private void selectTabByOperator() {
        pagerPackages.setCurrentItem(2);
        Helper.Operator operator = Helper.getOperator();
        if (operator == Helper.Operator.IRANCELL){
            pagerPackages.setCurrentItem(1);
        } else if(operator == Helper.Operator.RIGHTELL){
            pagerPackages.setCurrentItem(0);
        }
    }

    private void changeTabsFont() {
        ViewGroup vg = (ViewGroup) tabOperators.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    TextView textView = (TextView) tabViewChild;
                    textView.setWidth(App.screenWidth / 3);
                    textView.setTypeface(App.persianFont);
                }
            }
        }
    }


}
