//package com.zohaltech.app.sigma.adapters;
//
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.zohaltech.app.sigma.R;
//import com.zohaltech.app.sigma.classes.App;
//import com.zohaltech.app.sigma.classes.TrafficUnitsUtil;
//import com.zohaltech.app.sigma.entities.AppsTrafficMonitor;
//
//import java.util.ArrayList;
//
//public class AppsTrafficReportAdapter extends ArrayAdapter<AppsTrafficMonitor> {
//    public AppsTrafficReportAdapter(ArrayList<AppsTrafficMonitor> trafficMonitorList) {
//        super(App.context, R.layout.adapter_apps_traffic, trafficMonitorList);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//
//        AppsTrafficMonitor item = getItem(position);
//        if (convertView == null) {
//            convertView = App.inflater.inflate(R.layout.adapter_apps_traffic, parent, false);
//            holder = new ViewHolder(convertView);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        holder.fill(item, position);
//        return convertView;
//    }
//
//    private static class ViewHolder {
//        LinearLayout layoutAppsTraffic;
//        TextView     txtAppName;
//        TextView     txtTraffic;
//        TextView     txtTrafficWifi;
//
//        public ViewHolder(View view) {
//            layoutAppsTraffic = (LinearLayout) view.findViewById(R.id.layoutAppsTraffic);
//            txtAppName = (TextView) view.findViewById(R.id.txtAppName);
//            txtTraffic = (TextView) view.findViewById(R.id.txtTraffic);
//            txtTrafficWifi = (TextView) view.findViewById(R.id.txtTrafficWifi);
//        }
//
//        public void fill(final AppsTrafficMonitor item, final int position) {
//            if (position % 2 == 1) {
//                layoutAppsTraffic.setBackgroundResource(R.color.primary_lighter);
//            } else {
//                layoutAppsTraffic.setBackgroundResource(R.color.white);
//            }
//            txtAppName.setText(item.getAppName());
//            txtTraffic.setText(TrafficUnitsUtil.getUsedTrafficWithPoint(item.getTotalTrafficData()));
//            txtTrafficWifi.setText(TrafficUnitsUtil.getUsedTrafficWithPoint(item.getTotalTrafficWifi()));
//        }
//    }
//}
