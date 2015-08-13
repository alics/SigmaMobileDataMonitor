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
import java.util.Date;

public class HistoryAdapter extends ArrayAdapter<PackageHistory> {
    public HistoryAdapter(ArrayList<PackageHistory> packageHistories) {
        super(App.context, R.layout.adapter_history, packageHistories);
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

            String activationDate = item.getStartDateTime() != null && !item.getStartDateTime().isEmpty() ?
                                    SolarCalendar.getShamsiDateTime(Helper.getDateTime(item.getStartDateTime())).substring(0,16) : "بسته رزرو شده است و با پایان بسته فعلی فعال خواهد شد.";

            txtActivateDate.setText(activationDate);

            String expDate = item.getEndDateTime() != null && !item.getEndDateTime().isEmpty() ?
                             SolarCalendar.getShamsiDateTime(Helper.getDateTime(item.getEndDateTime())).substring(0,16) : "---";
            txtExpDate.setText(expDate);

            String status = "";
            if (item.getActive())
                status = "فعال";
            else {

                Date packageActivationDate = Helper.getDateTime(item.getStartDateTime());
                Date packageEndDate = Helper.getDateTime(item.getEndDateTime());
                int diffDays = (int) ((packageEndDate.getTime() - packageActivationDate.getTime()) / (1000 * 60 * 60 * 24));

                int usingDays = dataPackage.getPeriod() - diffDays;
                if (usingDays == 0)
                    status = "تاریخ اعتبار بسته به پایان رسیده است.";
                else
                    status = "حجم بسته به پایان رسیده است";
            }
            txtStatus.setText(status);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        PackageHistory item = getItem(position);
        if (convertView == null) {
            convertView = App.inflater.inflate(R.layout.adapter_history, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.fill(this, item, position);
        return convertView;
    }
}
