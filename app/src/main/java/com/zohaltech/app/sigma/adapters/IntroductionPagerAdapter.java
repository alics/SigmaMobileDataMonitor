package com.zohaltech.app.sigma.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.activities.PaymentActivity;
import com.zohaltech.app.sigma.activities.DashboardActivity;
import com.zohaltech.app.sigma.activities.IntroductionActivity;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.LicenseManager;

import widgets.MyToast;
import widgets.Slide;

public class IntroductionPagerAdapter extends PagerAdapter {
    Context  context;
    int      count;
    String[] texts;
    int[]    images;

    public IntroductionPagerAdapter(Context context, int count, String[] texts, int[] images) {
        this.count = count;
        this.context = context;
        this.texts = texts;
        this.images = images;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = App.inflater.inflate(R.layout.pager_adapter_introduction, container, false);
        // Declare Variables
        TextView txtIntro = (TextView) itemView.findViewById(R.id.txtIntro);
        ImageView imgIntro = (ImageView) itemView.findViewById(R.id.imgIntro);
        Slide slide = (Slide) itemView.findViewById(R.id.slide);
        LinearLayout layoutPurchase = (LinearLayout) itemView.findViewById(R.id.layoutPurchase);
        slide.setTextColor(context.getResources().getColor(R.color.secondary_text));
        slide.setBackgroundColor("#ffffff");

        layoutPurchase.setVisibility(View.GONE);
        if (position != 0) {
            slide.setVisibility(View.GONE);
            if (position == 6) {
                txtIntro.setVisibility(View.GONE);
                //imgIntro.setVisibility(View.GONE);
                imgIntro.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layoutPurchase.setVisibility(View.VISIBLE);
                Button btnBuyNow = (Button) itemView.findViewById(R.id.btnBuyNow);
                Button btnGoToApp = (Button) itemView.findViewById(R.id.btnGoToApp);

                btnBuyNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        App.uiPreferences.edit().putBoolean(IntroductionActivity.INTRO_SHOWN, true).apply();
                        if (LicenseManager.getLicenseStatus() == LicenseManager.Status.NOT_REGISTERED) {
                            ((PaymentActivity) App.currentActivity).pay();
                        } else {
                            Intent intent = new Intent(App.currentActivity, DashboardActivity.class);
                            App.currentActivity.startActivity(intent);
                            App.currentActivity.finish();
                            MyToast.show("شما قبلا نسخه کامل را خریداری نموده اید", Toast.LENGTH_LONG);
                        }
                    }
                });

                btnGoToApp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        App.uiPreferences.edit().putBoolean(IntroductionActivity.INTRO_SHOWN, true).apply();
                        Intent intent = new Intent(App.currentActivity, DashboardActivity.class);
                        App.currentActivity.startActivity(intent);
                        App.currentActivity.finish();
                    }
                });
            }
        }

        txtIntro.setText(texts[position]);
        imgIntro.setImageResource(images[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        container.removeView((LinearLayout) object);
    }
}