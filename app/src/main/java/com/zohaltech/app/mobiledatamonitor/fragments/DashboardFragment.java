package com.zohaltech.app.mobiledatamonitor.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.MainActivity;
import com.zohaltech.app.mobiledatamonitor.activities.PackageManagementActivity;
import com.zohaltech.app.mobiledatamonitor.adapters.UsagePagerAdapter;
import com.zohaltech.app.mobiledatamonitor.classes.App;

import widgets.MyFragment;
import widgets.MyToast;
import widgets.MyViewPagerIndicator;

public class DashboardFragment extends MyFragment {

    //public static final String DASHBOARD_PAGE_INDEX = "DASHBOARD_PAGE_INDEX";

    ViewPager            pagerUsages;
    MyViewPagerIndicator indicator;
    Button               btnPackageManagement;
    Button               btnPurchasePackage;
    Button               btnUsageReport;
    Button               btnPackagesHistory;

    UsagePagerAdapter usagePagerAdapter;

    long startTime;

    public DashboardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        startTime = System.currentTimeMillis() - 5000;

        pagerUsages = (ViewPager) rootView.findViewById(R.id.pagerUsages);
        indicator = (MyViewPagerIndicator) rootView.findViewById(R.id.indicator);
        btnPackageManagement = (Button) rootView.findViewById(R.id.btnPackageManagement);
        btnPurchasePackage = (Button) rootView.findViewById(R.id.btnPurchasePackage);
        btnUsageReport = (Button) rootView.findViewById(R.id.btnUsageReport);
        btnPackagesHistory = (Button) rootView.findViewById(R.id.btnPackagesHistory);

        pagerUsages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicator.setPercent(positionOffset);
                indicator.setCurrentPage(position);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 2) {
                    int pageIndex = pagerUsages.getCurrentItem();
                    if (pageIndex == 0) {
                        usagePagerAdapter.loadTodayUsage();
                    } else if (pageIndex == 1) {
                        usagePagerAdapter.loadTrafficUsage();
                    } else if (pageIndex == 2) {
                        usagePagerAdapter.loadRemainDays();
                    }
                }
            }
        });

        indicator.setIndicatorsCount(3);

        btnPackageManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(App.currentActivity, PackageManagementActivity.class);
                startActivity(myIntent);
            }
        });

        btnPurchasePackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(App.currentActivity, PackagesActivity.class);
                //startActivity(intent);
                MainActivity parent = ((MainActivity) getActivity());
                parent.animType = MainActivity.AnimType.OPEN;
                parent.displayView(MainActivity.EnumFragment.PACKAGES);
            }
        });

        btnUsageReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(App.currentActivity, DailyTrafficMonitorActivity.class);
                //startActivity(intent);
                MainActivity parent = ((MainActivity) getActivity());
                parent.animType = MainActivity.AnimType.OPEN;
                parent.displayView(MainActivity.EnumFragment.REPORT);
            }
        });

        btnPackagesHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(App.currentActivity, PackagesHistoryActivity.class);
                //startActivity(intent);
                MainActivity parent = ((MainActivity) getActivity());
                parent.animType = MainActivity.AnimType.OPEN;
                parent.displayView(MainActivity.EnumFragment.HISTORY);
            }
        });

        usagePagerAdapter = new UsagePagerAdapter();
        pagerUsages.setAdapter(usagePagerAdapter);
        pagerUsages.setCurrentItem(1);

        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dashboard, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            MyToast.show("سلام جان جان", Toast.LENGTH_SHORT, R.drawable.ic_warning);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        //usagePagerAdapter.notifyDataSetChanged();
        //if (pagerUsages.getCurrentItem() == 0) {
        //    usagePagerAdapter.loadTodayUsage();
        //} else if (pagerUsages.getCurrentItem() == 1) {
        //    usagePagerAdapter.loadTrafficUsage();
        //} else {
        //    usagePagerAdapter.loadRemainDays();
        //}
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - startTime) > 2000) {
            startTime = System.currentTimeMillis();
            MyToast.show(getString(R.string.press_back_again_to_exit), Toast.LENGTH_SHORT);
            //Toast.makeText(App.context, getString(R.string.press_back_again_to_exit), Toast.LENGTH_SHORT).show();
        } else {
            getActivity().finish();
        }
    }
}
