package com.zohaltech.app.mobiledatamonitor.adapters;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;

public class TutorialPagerAdapter extends PagerAdapter {

    Context        context;
    String[]       items;
    int[]          flag;
    LayoutInflater inflater;


    public TutorialPagerAdapter(Context context, String[] items, int[] flag) {
        this.context = context;
        this.items = items;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = App.inflater.inflate(R.layout.pager_tutorial_item, container, false);

        // Declare Variables
        TextView txtTutorialDesc = (TextView) itemView.findViewById(R.id.txtTutorialDesc);
        ImageView imageTutorialImage= (ImageView) itemView.findViewById(R.id.imageTutorialImage);

        txtTutorialDesc.setText(items[position]);
        imageTutorialImage.setImageResource(flag[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        container.removeView((RelativeLayout) object);

    }
}
