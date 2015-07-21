package com.zohaltech.app.mobiledatamonitor.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zohaltech.app.mobiledatamonitor.fragments.PackageFragment;

public class PackagePagerAdapter extends FragmentPagerAdapter {
    final   int    PAGE_COUNT  = 3;
    private String tabTitles[] = new String[]{"رایتل", "ایرانسل", "همراه اول"};
    private Context context;

    public PackagePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return PackageFragment.newInstance(3);
        else if (position == 1)
            return PackageFragment.newInstance(2);
        else
            return PackageFragment.newInstance(1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}