package com.zohaltech.app.mobiledatamonitor.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.classes.SolarCalendar;
import com.zohaltech.app.mobiledatamonitor.entities.TrafficMonitor;

import java.util.ArrayList;


public class TrafficMonitorAdapter extends ArrayAdapter<TrafficMonitor> {
    public TrafficMonitorAdapter(ArrayList<TrafficMonitor> trafficMonitorList) {
        super(App.context, R.layout.adapter_traffic_monitor, trafficMonitorList);
    }

    private static class ViewHolder {

        TextView     txtDate;
        TextView     txtTraffic;

        public ViewHolder(View view) {
            txtDate = (TextView) view.findViewById(R.id.txtDate);
            txtTraffic = (TextView) view.findViewById(R.id.txtTraffic);
        }

        public void fill(final ArrayAdapter<TrafficMonitor> adapter, final TrafficMonitor item, final int position) {
            txtDate.setText(SolarCalendar.getShamsiDate(Helper.getDate(item.getDate())));
            txtTraffic.setText(item.getTotalTraffic() + "");
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        TrafficMonitor item = getItem(position);
        if (convertView == null) {
            convertView = App.inflater.inflate(R.layout.adapter_traffic_monitor, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill(this, item, position);
        return convertView;
    }
}
