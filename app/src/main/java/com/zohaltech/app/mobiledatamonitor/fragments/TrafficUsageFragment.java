package com.zohaltech.app.mobiledatamonitor.fragments;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.PackageStatus;
import com.zohaltech.app.mobiledatamonitor.classes.TrafficDisplay;

import widgets.ArcProgress;

public class TrafficUsageFragment extends Fragment {

    ArcProgress progressPrimaryUsage;
    ArcProgress progressSecondaryUsage;
    //private BroadcastReceiver broadcastReceiver;

    long   usedPrimaryTraffic;
    long   totalPrimaryTraffic;
    String strPrimaryTraffic;

    long   usedSecondaryTraffic;
    long   totalSecondaryTraffic;
    String strSecondaryTraffic;

    //@Override
    //public void onCreate(Bundle savedInstanceState) {
    //    super.onCreate(savedInstanceState);
    //    broadcastReceiver = new BroadcastReceiver() {
    //        @Override
    //        public void onReceive(Context context, Intent intent) {
    //            updateUI(intent.getLongExtra(DataUsageService.DAILY_USAGE_BYTES, 0));
    //        }
    //    };
    //}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pager_adapter_traffic_usage, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int size1 = (App.screenWidth) / 2;
        int size2 = (App.screenWidth) / 4;
        progressPrimaryUsage = (ArcProgress) view.findViewById(R.id.progressPrimaryUsage);
        progressSecondaryUsage = (ArcProgress) view.findViewById(R.id.progressSecondaryUsage);
        TextView txtSecondaryCaption = (TextView) view.findViewById(R.id.txtSecondaryCaption);

        progressPrimaryUsage.setLayoutParams(new LinearLayout.LayoutParams(size1, size1));
        progressSecondaryUsage.setLayoutParams(new LinearLayout.LayoutParams(size2, size2));

        txtSecondaryCaption.setLayoutParams(new LinearLayout.LayoutParams(size2, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    //@Override
    //public void onStart() {
    //    super.onStart();
    //    LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, new IntentFilter(DataUsageService.DAILY_USAGE_ACTION));
    //}

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    //@Override
    //public void onStop() {
    //    super.onStop();
    //    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    //}

    public void updateUI() {
        PackageStatus status = PackageStatus.getCurrentStatus();

        if (status.getHasActivePackage()) {
            usedPrimaryTraffic = 100;//status.getUsedPrimaryTraffic();
            totalPrimaryTraffic = 200;//status.getPrimaryTraffic();
            usedSecondaryTraffic = 75;//status.getUsedSecondaryTraffic();
            totalSecondaryTraffic =80;// status.getSecondaryTraffic();
            strPrimaryTraffic = TrafficDisplay.getArcTraffic(usedPrimaryTraffic, totalPrimaryTraffic);
            strSecondaryTraffic = TrafficDisplay.getArcTraffic(usedSecondaryTraffic, totalSecondaryTraffic);
            //progressPrimaryUsage.setProgress(0, strPrimaryTraffic);
            //progressSecondaryUsage.setProgress(0, strSecondaryTraffic);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new PrimaryProgressTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new SecondaryTrafficTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new PrimaryProgressTask().execute();
            new SecondaryTrafficTask().execute();
        }
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

}
