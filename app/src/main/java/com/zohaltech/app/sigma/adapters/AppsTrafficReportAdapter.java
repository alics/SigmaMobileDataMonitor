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
import com.zohaltech.app.sigma.fragments.AppsTrafficReportFragment;

import java.util.ArrayList;

import widgets.PercentProgressbar;

public class AppsTrafficReportAdapter extends ArrayAdapter<AppsTrafficMonitor> {
    long                                 _mobileSum;
    long                                 _wifiSum;
    long                                 _total;
    AppsTrafficReportFragment.ReportType _reportType;

    public AppsTrafficReportAdapter(ArrayList<AppsTrafficMonitor> trafficMonitorList, AppsTrafficReportFragment.ReportType reportType) {
        super(App.context, R.layout.adapter_apps_traffic, trafficMonitorList);
        _reportType = reportType;
        for (AppsTrafficMonitor trafficMonitor : trafficMonitorList) {
            _mobileSum += trafficMonitor.getMobileTraffic();
            _wifiSum += trafficMonitor.getWifiTraffic();
            _total += trafficMonitor.getTotal();

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
        TextView           txtTraffic;
        PercentProgressbar progress;
        //TextView           txtTrafficWifi;
        //PercentProgressbar progressWifi;

        public ViewHolder(View view) {
            imgApp = (ImageView) view.findViewById(R.id.imgApp);
            txtAppName = (TextView) view.findViewById(R.id.txtAppName);
            txtTraffic = (TextView) view.findViewById(R.id.txtTraffic);
            //txtTrafficWifi = (TextView) view.findViewById(R.id.txtTrafficWifi);
            progress = (PercentProgressbar) view.findViewById(R.id.progress);
            //progressWifi = (PercentProgressbar) view.findViewById(R.id.progressWifi);
        }

        public void fill(final AppsTrafficMonitor item, final int position) {
            String packageName = App.context.getPackageManager().getNameForUid(item.getUid());
            Drawable icon = null;

            String appName = packageName + "-" + item.getUid();
            if (item.getUid() == 0) {
                appName = "Root System";
            } else {
                try {
                    icon = App.context.getPackageManager().getApplicationIcon(packageName);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                imgApp.setImageDrawable(icon);
                try {
                    ApplicationInfo info = App.context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                    appName = App.context.getPackageManager().getApplicationLabel(info).toString();
                    if (appName.equals(""))
                        appName = packageName + "-" + item.getUid();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }

            txtAppName.setText(appName);
            String traffic = "";
            if (_reportType == AppsTrafficReportFragment.ReportType.BOTH) {
                traffic = TrafficUnitsUtil.getTodayTraffic(item.getTotal()).getInlineDisplay();
                progress.setProgress(_total == 0 ? 0 : (int) (item.getTotal() * 100 / _total));
            } else if (_reportType == AppsTrafficReportFragment.ReportType.DATA) {
                traffic = TrafficUnitsUtil.getTodayTraffic(item.getMobileTraffic()).getInlineDisplay();
                progress.setProgress(_mobileSum == 0 ? 0 : (int) (item.getMobileTraffic() * 100 / _mobileSum));

            } else if (_reportType == AppsTrafficReportFragment.ReportType.WIFI) {
                traffic = TrafficUnitsUtil.getTodayTraffic(item.getWifiTraffic()).getInlineDisplay();
                progress.setProgress(_wifiSum == 0 ? 0 : (int) (item.getWifiTraffic() * 100 / _wifiSum));
            }
            txtTraffic.setText(traffic);
        }
    }
}
