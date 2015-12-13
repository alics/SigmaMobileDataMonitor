package com.zohaltech.app.sigma.adapters;


import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.TrafficUnitsUtil;
import com.zohaltech.app.sigma.entities.AppsTrafficMonitor;

import java.util.ArrayList;

import widgets.PercentProgressbar;

public class AppsTrafficReportAdapter extends ArrayAdapter<AppsTrafficMonitor> {

    long mobileSum;
    long wifiSum;

    public AppsTrafficReportAdapter(ArrayList<AppsTrafficMonitor> trafficMonitorList) {
        super(App.context, R.layout.adapter_apps_traffic, trafficMonitorList);
        for (AppsTrafficMonitor trafficMonitor : trafficMonitorList) {
            mobileSum += trafficMonitor.getMobileTraffic();
            wifiSum += trafficMonitor.getWifiTraffic();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        AppsTrafficMonitor item = getItem(position);
        if (convertView == null) {
            convertView = App.inflater.inflate(R.layout.adapter_apps_traffic, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.fill(item, position);
        return convertView;
    }

    private class ViewHolder {
        ImageView          imgApp;
        TextView           txtAppName;
        TextView           txtTrafficMobile;
        PercentProgressbar progressMobile;
        TextView           txtTrafficWifi;
        PercentProgressbar progressWifi;

        public ViewHolder(View view) {
            imgApp = (ImageView) view.findViewById(R.id.imgApp);
            txtAppName = (TextView) view.findViewById(R.id.txtAppName);
            txtTrafficMobile = (TextView) view.findViewById(R.id.txtTrafficMobile);
            txtTrafficWifi = (TextView) view.findViewById(R.id.txtTrafficWifi);
            progressMobile = (PercentProgressbar) view.findViewById(R.id.progressMobile);
            progressWifi = (PercentProgressbar) view.findViewById(R.id.progressWifi);
        }

        public void fill(final AppsTrafficMonitor item, final int position) {
            String packageName = App.context.getPackageManager().getNameForUid(item.getUid());
            Drawable icon = null;

            String appName = packageName+"-"+item.getUid();
            if (item.getUid() == 0) {
                appName = "Root System+ 0";
            } else {
                try {
                    icon = App.context.getPackageManager().getApplicationIcon(packageName);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                imgApp.setImageDrawable(icon);
                try {
                    ApplicationInfo info = App.context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                    appName = App.context.getPackageManager().getApplicationLabel(info).toString()+"-"+packageName;

                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }

            txtAppName.setText(appName);
            String trafficMobile = TrafficUnitsUtil.getTodayTraffic(item.getMobileTraffic()).getInlineDisplay();
            txtTrafficMobile.setText(trafficMobile);
            progressMobile.setProgress(mobileSum == 0 ? 0 : (int) (item.getMobileTraffic() * 100 / mobileSum));
            String trafficWifi = TrafficUnitsUtil.getTodayTraffic(item.getWifiTraffic()).getInlineDisplay();
            txtTrafficWifi.setText(trafficWifi);
            progressWifi.setProgress((int) (wifiSum == 0 ? 0 : item.getWifiTraffic() * 100 / wifiSum));
        }
    }
}
