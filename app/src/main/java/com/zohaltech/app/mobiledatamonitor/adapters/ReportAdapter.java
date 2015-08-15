package com.zohaltech.app.mobiledatamonitor.adapters;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.classes.SolarCalendar;
import com.zohaltech.app.mobiledatamonitor.classes.TrafficUnitsUtil;
import com.zohaltech.app.mobiledatamonitor.entities.TrafficMonitor;

import java.util.ArrayList;


public class ReportAdapter extends ArrayAdapter<TrafficMonitor> {
    public ReportAdapter(ArrayList<TrafficMonitor> trafficMonitorList) {
        super(App.context, R.layout.adapter_report, trafficMonitorList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        TrafficMonitor item = getItem(position);
        if (convertView == null) {
            convertView = App.inflater.inflate(R.layout.adapter_report, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill(this, item, position);
        return convertView;
    }

    private static class ViewHolder {

        LinearLayout layoutReport;
        TextView     txtDate;
        TextView     txtTraffic;

        public ViewHolder(View view) {
            layoutReport = (LinearLayout) view.findViewById(R.id.layoutReport);
            txtDate = (TextView) view.findViewById(R.id.txtDate);
            txtTraffic = (TextView) view.findViewById(R.id.txtTraffic);
        }

        public void fill(final ArrayAdapter<TrafficMonitor> adapter, final TrafficMonitor item, final int position) {
            if (position % 2 == 1) {
                layoutReport.setBackgroundColor(Color.parseColor("#14009688"));
                //txtDate.setTextColor(Color.WHITE);
                //txtTraffic.setTextColor(Color.WHITE);
            }
            String date = item.getDate().equals(Helper.getCurrentDate()) ? "امروز" : SolarCalendar.getShamsiDate(Helper.getDate(item.getDate()));
            txtDate.setText(date);
            txtTraffic.setText(TrafficUnitsUtil.getUsedTraffic(item.getTotalTraffic()));
        }
    }
}
