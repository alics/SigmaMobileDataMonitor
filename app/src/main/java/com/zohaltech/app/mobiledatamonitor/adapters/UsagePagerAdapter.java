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
import com.zohaltech.app.mobiledatamonitor.classes.DataUsageService;
import com.zohaltech.app.mobiledatamonitor.classes.PackageStatus;
import com.zohaltech.app.mobiledatamonitor.classes.TrafficDisplay;

import widgets.ArcProgress;
import widgets.CircleProgress;

public class UsagePagerAdapter extends PagerAdapter {

    final int PAGE_COUNT = 3;

    CircleProgress progressTodayUsage;
    ArcProgress    progressPrimaryUsage;
    ArcProgress    progressSecondaryUsage;
    CircleProgress progressDayRemain;

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
        return PAGE_COUNT;
    }

    //@Override
    //public int getItemPosition(Object object) {
    //    return POSITION_NONE;
    //}

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //int size1 = (App.currentActivity.getWindowManager().getDefaultDisplay().getWidth()) / 2;
        //int size2 = (App.currentActivity.getWindowManager().getDefaultDisplay().getWidth()) / 4;
        //int size1 = (App.context.getResources().getDisplayMetrics().widthPixels) / 2;
        //int size2 = (App.context.getResources().getDisplayMetrics().widthPixels) / 4;
        int size1 = (App.screenWidth) / 2;
        int size2 = (App.screenWidth) / 4;

        View view;
        if (position == 0) {

            view = App.inflater.inflate(R.layout.pager_adapter_today_usage, null);
            progressTodayUsage = (CircleProgress) view.findViewById(R.id.progressTodayUsage);
            progressTodayUsage.setLayoutParams(new LinearLayout.LayoutParams(size1, size1));

            TrafficDisplay trafficDisplay = TrafficDisplay.getTodayTraffic(App.preferences.getLong(DataUsageService.DAILY_USAGE_BYTES, 0));
            progressTodayUsage.setProgress(trafficDisplay.getValue(), trafficDisplay.getPostfix());

        } else if (position == 1) {

            view = App.inflater.inflate(R.layout.pager_adapter_traffic_usage, null);
            progressPrimaryUsage = (ArcProgress) view.findViewById(R.id.progressPrimaryUsage);
            progressSecondaryUsage = (ArcProgress) view.findViewById(R.id.progressSecondaryUsage);
            TextView txtSecondaryCaption = (TextView) view.findViewById(R.id.txtSecondaryCaption);


            progressPrimaryUsage.setLayoutParams(new LinearLayout.LayoutParams(size1, size1));
            progressSecondaryUsage.setLayoutParams(new LinearLayout.LayoutParams(size2, size2));

            txtSecondaryCaption.setLayoutParams(new LinearLayout.LayoutParams(size2, ViewGroup.LayoutParams.WRAP_CONTENT));

            PackageStatus status = PackageStatus.getCurrentStatus();

            if (status.getHasActivePackage()) {
                usedPrimaryTraffic = status.getUsedPrimaryTraffic();
                totalPrimaryTraffic = status.getPrimaryTraffic();
                usedSecondaryTraffic = status.getUsedSecondaryTraffic();
                totalSecondaryTraffic = status.getSecondaryTraffic();
                strPrimaryTraffic = TrafficDisplay.getArcTraffic(usedPrimaryTraffic, totalPrimaryTraffic);
                strSecondaryTraffic = TrafficDisplay.getArcTraffic(usedSecondaryTraffic, totalSecondaryTraffic);
                progressPrimaryUsage.setProgress(0, strPrimaryTraffic);
                progressSecondaryUsage.setProgress(0, strSecondaryTraffic);
            }

            startAnimation1();

        } else {
            view = App.inflater.inflate(R.layout.pager_adapter_day_remain, null);
            progressDayRemain = (CircleProgress) view.findViewById(R.id.progressDayRemain);
            progressDayRemain.setLayoutParams(new LinearLayout.LayoutParams(size1, size1));

            progressDayRemain.setProgress("" + PackageStatus.getCurrentStatus().getLeftDays(), "روز");
        }

        container.addView(view);
        return view;
    }

    //public void startAnimation0() {
    //    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
    //        new TodayTrafficTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    //    } else {
    //        new TodayTrafficTask().execute();
    //    }
    //}

    public void startAnimation1() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new PrimaryProgressTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new SecondaryTrafficTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new PrimaryProgressTask().execute();
            new SecondaryTrafficTask().execute();
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

    private class PrimaryProgressTask extends AsyncTask<Void, Integer, Void> {

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

    private class SecondaryTrafficTask extends AsyncTask<Void, Integer, Void> {

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

    //private class TodayTrafficTask extends AsyncTask<Void, Integer, Void> {
    //
    //    @Override
    //    protected Void doInBackground(Void... voids) {
    //        try {
    //            int progress = 0;
    //            while (progress < 100) {
    //                Thread.sleep(5 + (progress / 10));
    //                progress++;
    //                publishProgress(progress);
    //            }
    //
    //        } catch (InterruptedException e) {
    //            e.printStackTrace();
    //        }
    //        return null;
    //    }
    //
    //    @Override
    //    protected void onProgressUpdate(Integer... values) {
    //        super.onProgressUpdate(values);
    //        progressTodayUsage.setProgress(values[0], App.preferences.getLong(DataUsageService.DAILY_USAGE_BYTES, 0));
    //    }
    //}
}
