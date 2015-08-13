package com.zohaltech.app.mobiledatamonitor.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zohaltech.app.mobiledatamonitor.fragments.DaysRemainFragment;
import com.zohaltech.app.mobiledatamonitor.fragments.TodayUsageFragment;
import com.zohaltech.app.mobiledatamonitor.fragments.TrafficUsageFragment;

public class UsagePagerAdapter extends FragmentPagerAdapter {

    public UsagePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new TodayUsageFragment();
        } else if (i == 1) {
            return new TrafficUsageFragment();
        } else {
            return new DaysRemainFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}