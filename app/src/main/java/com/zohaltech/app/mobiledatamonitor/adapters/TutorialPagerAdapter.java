package com.zohaltech.app.mobiledatamonitor.adapters;


import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;

import widgets.Slide;

public class TutorialPagerAdapter extends PagerAdapter {

    Context        context;
    String[]       texts;
    int[]          images;
    LayoutInflater inflater;


    public TutorialPagerAdapter(Context context, String[] texts, int[] images) {
        this.context = context;
        this.texts = texts;
        this.images = images;
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
        TextView txtTutorial = (TextView) itemView.findViewById(R.id.txtTutorial);
        ImageView imgTutorial = (ImageView) itemView.findViewById(R.id.imgTutorial);
        Slide slide = (Slide) itemView.findViewById(R.id.slide);
        slide.setTextColor(context.getResources().getColor(R.color.secondary_text));
        slide.setBackgroundColor("#ffffff");

        if (position == 0) {
            slide.setVisibility(View.VISIBLE);
        } else {
            slide.setVisibility(View.GONE);
        }

        txtTutorial.setText(texts[position]);
        imgTutorial.setImageResource(images[position]);


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        container.removeView((RelativeLayout) object);

    }
}
