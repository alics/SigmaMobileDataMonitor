package com.zohaltech.app.mobiledatamonitor.fragments;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.PackageStatus;
import com.zohaltech.app.mobiledatamonitor.classes.TrafficUnitsUtil;

import widgets.ArcProgress;

public class TrafficUsageFragment extends Fragment {

    ArcProgress progressPrimaryUsage;
    ArcProgress progressSecondaryUsage;
    TextView    txtSecondary;
    TextView    txtSecondaryCaption;

    //long   usedPrimaryTraffic;
    //long   totalPrimaryTraffic;
    //String strPrimaryTraffic;
    //
    //long   usedSecondaryTraffic;
    //long   totalSecondaryTraffic;
    //String strSecondaryTraffic;

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
        txtSecondary = (TextView) view.findViewById(R.id.txtSecondary);
        txtSecondaryCaption = (TextView) view.findViewById(R.id.txtSecondaryCaption);

        progressPrimaryUsage.setLayoutParams(new LinearLayout.LayoutParams(size1, size1));
        progressSecondaryUsage.setLayoutParams(new LinearLayout.LayoutParams(size2, size2));

        txtSecondary.setLayoutParams(new LinearLayout.LayoutParams(size2, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtSecondaryCaption.setLayoutParams(new LinearLayout.LayoutParams(size2, ViewGroup.LayoutParams.WRAP_CONTENT));

        progressPrimaryUsage.setProgress(0, "0MB/0MB");
        progressSecondaryUsage.setProgress(0, "0MB/0MB");
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new PrimaryProgressTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            new SecondaryTrafficTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new PrimaryProgressTask().execute();
            new SecondaryTrafficTask().execute();
        }
    }

    private class PrimaryProgressTask extends AsyncTask<Void, Integer, Void> {

        long   usedPrimaryTraffic;
        long   totalPrimaryTraffic;
        String strPrimaryTraffic;

        public PrimaryProgressTask() {
            PackageStatus status = PackageStatus.getCurrentStatus();
            usedPrimaryTraffic = status.getUsedPrimaryTraffic();
            totalPrimaryTraffic = status.getPrimaryTraffic();
            strPrimaryTraffic = TrafficUnitsUtil.getArcTraffic(usedPrimaryTraffic, totalPrimaryTraffic);
        }

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

        long   usedSecondaryTraffic;
        long   totalSecondaryTraffic;
        String strSecondaryTraffic;

        public SecondaryTrafficTask() {
            PackageStatus status = PackageStatus.getCurrentStatus();
            usedSecondaryTraffic = status.getUsedSecondaryTraffic();
            totalSecondaryTraffic = status.getSecondaryTraffic();
            strSecondaryTraffic = TrafficUnitsUtil.getArcTraffic(usedSecondaryTraffic, totalSecondaryTraffic);
            txtSecondaryCaption.setText(status.getSecondaryCaption());
        }

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
