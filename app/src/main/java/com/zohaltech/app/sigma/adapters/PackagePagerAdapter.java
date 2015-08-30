package com.zohaltech.app.sigma.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zohaltech.app.sigma.fragments.PackageFragment;

public class PackagePagerAdapter extends FragmentPagerAdapter {
    final   int    PAGE_COUNT  = 3;
    private String tabTitles[] = new String[]{"رایتل", "ایرانسل", "همراه اول"};

    public PackagePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
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