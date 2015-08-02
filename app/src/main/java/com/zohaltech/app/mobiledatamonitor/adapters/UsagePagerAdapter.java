package com.zohaltech.app.mobiledatamonitor.adapters;

import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;

import widgets.ArcProgress;
import widgets.CircleProgress;

public class UsagePagerAdapter extends PagerAdapter {

    CircleProgress progressTodayUsage;
    ArcProgress    progressDay;
    ArcProgress    progressNight;
    int            dayTraffic;
    int            dayTotalTraffic;
    String         strDayTraffic;
    int            nightTraffic;
    int            nightTotalTraffic;
    String         strNightTraffic;

    //private Context context;
    //
    //public UsagePagerAdapter(Context context) {
    //    this.context = context;
    //}

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int size1 = (App.currentActivity.getWindowManager().getDefaultDisplay().getWidth()) / 2;
        int size2 = (App.currentActivity.getWindowManager().getDefaultDisplay().getWidth()) / 4;
        //int size1 = (container.getWidth()) / 2;
        //int size2 = (container.getWidth()) / 4;

        View view;
        if (position == 0) {
            view = App.inflater.inflate(R.layout.pager_adapter_today_usage, null);
            progressTodayUsage = (CircleProgress) view.findViewById(R.id.progressTodayUsage);
            progressTodayUsage.setProgress(25, "daily usage");
            progressTodayUsage.setLayoutParams(new LinearLayout.LayoutParams(size1, size1));
        } else if (position == 1) {
            view = App.inflater.inflate(R.layout.pager_adapter_traffic_usage, null);
            progressDay = (ArcProgress) view.findViewById(R.id.progressDay);
            progressNight = (ArcProgress) view.findViewById(R.id.progressNight);
            TextView txtNightTraffic = (TextView) view.findViewById(R.id.txtNightTraffic);


            progressDay.setLayoutParams(new LinearLayout.LayoutParams(size1, size1));
            progressNight.setLayoutParams(new LinearLayout.LayoutParams(size2, size2));

            txtNightTraffic.setLayoutParams(new LinearLayout.LayoutParams(size2, ViewGroup.LayoutParams.WRAP_CONTENT));

            dayTraffic = 96;
            dayTotalTraffic = 100;
            nightTraffic = 25;
            nightTotalTraffic = 100;
            strDayTraffic = dayTraffic + "/" + dayTotalTraffic + "MB";
            strNightTraffic = nightTraffic + "/" + nightTotalTraffic + "MB";
            progressDay.setProgress(0, strDayTraffic);
            progressNight.setProgress(0, strNightTraffic);

            startAnimation1();

        } else {
            view = App.inflater.inflate(R.layout.pager_adapter_day_remain, null);
        }

        container.addView(view);
        return view;
    }

    public void startAnimation1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new ProgressDayTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new ProgressNightTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new ProgressDayTask().execute();
            new ProgressNightTask().execute();
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private class ProgressDayTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                int percent = dayTraffic * 100 / dayTotalTraffic;
                int progress = 0;
                if (100 - percent >= 4) {
                    while (progress < (percent + 4)) {
                        Thread.sleep(15 - (percent / 10));
                        progress++;
                        publishProgress(progress);
                    }
                    while (progress > percent) {
                        Thread.sleep(100);
                        progress--;
                        publishProgress(progress);
                    }
                } else {
                    while (progress < percent) {
                        Thread.sleep(15 - (percent / 10));
                        progress++;
                        publishProgress(progress);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDay.setProgress(values[0], strDayTraffic);
        }
    }

    private class ProgressNightTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                int percent = nightTraffic * 100 / nightTotalTraffic;
                int progress = 0;
                if (100 - percent >= 4) {
                    while (progress < (percent + 4)) {
                        Thread.sleep(15 - (percent / 10));
                        progress++;
                        publishProgress(progress);
                    }
                    while (progress > percent) {
                        Thread.sleep(100);
                        progress--;
                        publishProgress(progress);
                    }
                } else {
                    while (progress < percent) {
                        Thread.sleep(15 - (percent / 10));
                        progress++;
                        publishProgress(progress);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressNight.setProgress(values[0], strNightTraffic);
        }
    }
}
