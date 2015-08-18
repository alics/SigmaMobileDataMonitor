package com.zohaltech.app.mobiledatamonitor.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.MainActivity;
import com.zohaltech.app.mobiledatamonitor.adapters.PackagePagerAdapter;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.Helper;

import widgets.MyFragment;

public class PackagesFragment extends MyFragment {
    PagerSlidingTabStrip tabOperators;
    ViewPager            pagerPackages;
    PackagePagerAdapter  packagePagerAdapter;

    public PackagesFragment() {
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_packages, container, false);

        // Initialize the ViewPager and set an adapter
        pagerPackages = (ViewPager) rootView.findViewById(R.id.pagerPackages);
        packagePagerAdapter = new PackagePagerAdapter(getFragmentManager());
        pagerPackages.setAdapter(packagePagerAdapter);

        // Bind the tabOperators to the ViewPager
        tabOperators = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabOperators);
        tabOperators.setViewPager(pagerPackages);

        pagerPackages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((TextView) ((ViewGroup) tabOperators.getChildAt(0)).getChildAt(0)).setTextColor(getResources().getColor(R.color.gray_lighter));
                ((TextView) ((ViewGroup) tabOperators.getChildAt(0)).getChildAt(1)).setTextColor(getResources().getColor(R.color.gray_lighter));
                ((TextView) ((ViewGroup) tabOperators.getChildAt(0)).getChildAt(2)).setTextColor(getResources().getColor(R.color.gray_lighter));
                ((TextView) ((ViewGroup) tabOperators.getChildAt(0)).getChildAt(position)).setTextColor(Color.WHITE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        changeTabsFont();

        selectTabByOperator();

        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            packagePagerAdapter = null;
            pagerPackages.setAdapter(null);
        } catch (Exception e) {
            // nothing
        }
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

    private void selectTabByOperator() {
        pagerPackages.setCurrentItem(2);
        Helper.Operator operator = Helper.getOperator();
        if (operator == Helper.Operator.IRANCELL) {
            pagerPackages.setCurrentItem(1);
        } else if (operator == Helper.Operator.RIGHTELL) {
            pagerPackages.setCurrentItem(0);
        }
    }

    private void changeTabsFont() {
        ViewGroup vg = (ViewGroup) tabOperators.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            TextView textView = (TextView) vg.getChildAt(j);
            //int tabChildCount = vgTab.getChildCount();
            //for (int i = 0; i < tabChildCount; i++) {
            //    View tabViewChild = vgTab.getChildAt(i);
            //    if (tabViewChild instanceof TextView) {
            //        TextView textView = (TextView) tabViewChild;
            textView.setWidth(App.screenWidth / 3);
            textView.setTypeface(App.persianFont);
            textView.setTextColor(getResources().getColor(R.color.gray_lighter));
            textView.setTextSize(14);
        }
    }
}