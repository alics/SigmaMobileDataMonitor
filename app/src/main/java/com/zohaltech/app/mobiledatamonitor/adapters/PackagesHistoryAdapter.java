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
import com.zohaltech.app.mobiledatamonitor.dal.DataPackages;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;

import java.util.ArrayList;

public class PackagesHistoryAdapter extends ArrayAdapter<PackageHistory> {
    public PackagesHistoryAdapter(ArrayList<PackageHistory> packageHistories) {
        super(App.context, R.layout.adapter_package_history, packageHistories);
    }

    private static class ViewHolder {

        TextView     txtPackageDesc;
        TextView     txtActivateDate;
        TextView     txtExpDate;
        TextView     txtStatus;
        LinearLayout layoutPackageHistory;

        public ViewHolder(View view) {
            txtPackageDesc = (TextView) view.findViewById(R.id.txtPackageDesc);
            txtActivateDate = (TextView) view.findViewById(R.id.txtActivateDate);
            txtExpDate = (TextView) view.findViewById(R.id.txtExpDate);
            txtStatus = (TextView) view.findViewById(R.id.txtStatus);
            layoutPackageHistory = (LinearLayout) view.findViewById(R.id.layoutPackageHistory);
        }

        public void fill(final ArrayAdapter<PackageHistory> adapter, final PackageHistory item, final int position) {
            DataPackage dataPackage = DataPackages.selectPackageById(item.getDataPackageId());
            txtPackageDesc.setText(dataPackage.getTitle());
            txtActivateDate.setText(SolarCalendar.getShamsiDateTime(Helper.getDateTime(item.getStartDateTime())));
            //txtExpDate.setText(SolarCalendar.getShamsiDateTime(Helper.getDateTime(item.getEndDateTime())));
            txtStatus.setText(item.getActive() ? "فعال" : "غیر فعال");
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        PackageHistory item = getItem(position);
        if (convertView == null) {
            convertView = App.inflater.inflate(R.layout.adapter_package_history, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill(this, item, position);
        return convertView;
    }
}
