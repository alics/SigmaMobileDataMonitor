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
import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.classes.PackageStatus;

import widgets.ArcProgress;
import widgets.CircleProgress;

public class UsagePagerAdapter extends PagerAdapter {

    CircleProgress progressTodayUsage;
    ArcProgress    progressPrimaryUsage;
    ArcProgress    progressSecondaryUsage;

    long   usedPrimaryTraffic;
    long   totalPrimaryTraffic;
    String strPrimaryTraffic;

    long   usedSecondaryTraffic;
    long   totalSecondaryTraffic;
    String strSecondaryTraffic;

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
            progressPrimaryUsage = (ArcProgress) view.findViewById(R.id.progressPrimaryUsage);
            progressSecondaryUsage = (ArcProgress) view.findViewById(R.id.progressSecondaryUsage);
            TextView txtNightTraffic = (TextView) view.findViewById(R.id.txtNightTraffic);


            progressPrimaryUsage.setLayoutParams(new LinearLayout.LayoutParams(size1, size1));
            progressSecondaryUsage.setLayoutParams(new LinearLayout.LayoutParams(size2, size2));

            txtNightTraffic.setLayoutParams(new LinearLayout.LayoutParams(size2, ViewGroup.LayoutParams.WRAP_CONTENT));

            PackageStatus status = PackageStatus.getCurrentStatus();

            if (status.getHasActivePackage()) {
                usedPrimaryTraffic = status.getUsedPrimaryTraffic();
                totalPrimaryTraffic = status.getPrimaryTraffic();
                usedSecondaryTraffic = status.getUsedSecondaryTraffic();
                totalSecondaryTraffic = status.getSecondaryTraffic();
                strPrimaryTraffic = Helper.getArcTraffic(usedPrimaryTraffic, totalPrimaryTraffic);
                strSecondaryTraffic = Helper.getArcTraffic(usedSecondaryTraffic, totalSecondaryTraffic);
                progressPrimaryUsage.setProgress(0, strPrimaryTraffic);
                progressSecondaryUsage.setProgress(0, strSecondaryTraffic);
            }

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
                if (totalPrimaryTraffic != 0) {
                    int percent = (int) (usedPrimaryTraffic * 100 / totalPrimaryTraffic);
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
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressPrimaryUsage.setProgress(values[0], strPrimaryTraffic);
        }
    }

    private class ProgressNightTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                if (totalSecondaryTraffic != 0) {
                    int percent = (int) (usedSecondaryTraffic * 100 / totalSecondaryTraffic);
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
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressSecondaryUsage.setProgress(values[0], strSecondaryTraffic);
        }
    }
}
