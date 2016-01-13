package com.zohaltech.app.sigma.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.Helper;
import com.zohaltech.app.sigma.classes.SolarCalendar;
import com.zohaltech.app.sigma.classes.TrafficUnitsUtil;
import com.zohaltech.app.sigma.entities.TrafficMonitor;

import java.util.ArrayList;


public class TotalTrafficReportAdapter extends ArrayAdapter<TrafficMonitor> {
    public TotalTrafficReportAdapter(ArrayList<TrafficMonitor> trafficMonitorList) {
        super(App.context, R.layout.adapter_total_traffic, trafficMonitorList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        TrafficMonitor item = getItem(position);
        if (convertView == null) {
            convertView = App.inflater.inflate(R.layout.adapter_total_traffic, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill( item, position);
        return convertView;
    }

    private static class ViewHolder {

        LinearLayout layoutReport;
        TextView     txtDate;
        TextView     txtTrafficMobile;
        TextView     txtTrafficWifi;

        public ViewHolder(View view) {
            layoutReport = (LinearLayout) view.findViewById(R.id.layoutReport);
            txtDate = (TextView) view.findViewById(R.id.txtDate);
            txtTrafficMobile = (TextView) view.findViewById(R.id.txtTrafficMobile);
            txtTrafficWifi = (TextView) view.findViewById(R.id.txtTrafficWifi);
        }

        public void fill(final TrafficMonitor item, final int position) {
            if (position % 2 == 1) {
                layoutReport.setBackgroundResource(R.color.primary_lighter);
            } else {
                layoutReport.setBackgroundResource(R.color.white);
            }
            String date = item.getDate().equals(Helper.getCurrentDate()) ? "امروز" : SolarCalendar.getShamsiDate(Helper.getDate(item.getDate()));
            txtDate.setText(date);
            txtTrafficMobile.setText(TrafficUnitsUtil.getUsedTrafficWithPoint(item.getTotalTraffic()));
            txtTrafficWifi.setText(TrafficUnitsUtil.getUsedTrafficWithPoint(item.getTotalTrafficWifi()));
        }
    }
}
