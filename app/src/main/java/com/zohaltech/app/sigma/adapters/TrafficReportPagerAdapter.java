package com.zohaltech.app.sigma.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zohaltech.app.sigma.fragments.AppsTrafficReportFragment;
import com.zohaltech.app.sigma.fragments.TotalTrafficReportFragment;

public class TrafficReportPagerAdapter extends FragmentPagerAdapter {

    private String[] tabTitles = new String[]{"مصرف کلی", "مصرف برنامه ها"};
    private int vocabId;

    public TrafficReportPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return TotalTrafficReportFragment.newInstance();
        else
            return AppsTrafficReportFragment.newInstance();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
