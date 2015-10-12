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
import com.zohaltech.app.sigma.dal.DataPackages;
import com.zohaltech.app.sigma.entities.DataPackage;
import com.zohaltech.app.sigma.entities.PackageHistory;

import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<PackageHistory> {
    public HistoryAdapter(ArrayList<PackageHistory> packageHistories) {
        super(App.context, R.layout.adapter_history, packageHistories);
    }

    private static class ViewHolder {

        TextView txtPackageDesc;
        TextView txtActivateDate;
        TextView txtExpDate;
        TextView txtStatus;
        TextView txtPrimaryExpDate;
        TextView txtPrimaryExpDateTitle;
        TextView txtSecondaryExpDate;
        TextView txtSecondaryExpDateTitle;
        LinearLayout layoutPackageHistory;

        public ViewHolder(View view) {
            txtPackageDesc = (TextView) view.findViewById(R.id.txtPackageDesc);
            txtActivateDate = (TextView) view.findViewById(R.id.txtActivateDate);
            txtExpDate = (TextView) view.findViewById(R.id.txtExpDate);
            txtStatus = (TextView) view.findViewById(R.id.txtStatus);
            txtSecondaryExpDate = (TextView) view.findViewById(R.id.txtSecondaryExpDate);
            txtPrimaryExpDate = (TextView) view.findViewById(R.id.txtPrimaryExpDate);
            txtPrimaryExpDateTitle = (TextView) view.findViewById(R.id.txtPrimaryExpDateTitle);
            txtSecondaryExpDateTitle = (TextView) view.findViewById(R.id.txtSecondaryExpDateTitle);
            layoutPackageHistory = (LinearLayout) view.findViewById(R.id.layoutPackageHistory);
        }

        public void fill(final PackageHistory item, final int position) {
            DataPackage dataPackage = DataPackages.selectPackageById(item.getDataPackageId());
            txtPackageDesc.setText(dataPackage.getTitle());
            String activationDate = item.getStartDateTime() != null && !item.getStartDateTime().isEmpty() ?
                    SolarCalendar.getShamsiDateTime(Helper.getDateTime(item.getStartDateTime())).substring(0, 16) : "بسته رزرو شده است و با پایان بسته فعلی فعال خواهد شد.";
            txtActivateDate.setText(activationDate);
            String expDate = item.getEndDateTime() != null && !item.getEndDateTime().isEmpty() ?
                    SolarCalendar.getShamsiDateTime(Helper.getDateTime(item.getEndDateTime())).substring(0, 16) : "---";
            txtExpDate.setText(expDate);

            if (dataPackage.getPrimaryTraffic() != 0) {
                String primaryExpDate = item.getPrimaryPackageEndDateTime() != null && !item.getPrimaryPackageEndDateTime().isEmpty() ?
                        SolarCalendar.getShamsiDateTime(Helper.getDateTime(item.getPrimaryPackageEndDateTime())).substring(0, 16) : "---";
                txtPrimaryExpDate.setText(primaryExpDate);
            } else {
                txtPrimaryExpDate.setVisibility(View.GONE);
                txtPrimaryExpDateTitle.setVisibility(View.GONE);
            }

            if (dataPackage.getSecondaryTraffic() != 0) {
                String secondaryExpDate = item.getSecondaryTrafficEndDateTime() != null && !item.getSecondaryTrafficEndDateTime().isEmpty() ?
                        SolarCalendar.getShamsiDateTime(Helper.getDateTime(item.getSecondaryTrafficEndDateTime())).substring(0, 16) : "---";
                txtSecondaryExpDate.setText(secondaryExpDate);
            } else {
                txtSecondaryExpDate.setVisibility(View.GONE);
                txtSecondaryExpDateTitle.setVisibility(View.GONE);
            }
            String status = "";
            if (item.getStatus() == PackageHistory.StatusEnum.ACTIVE.ordinal())
                status = "فعال";
            else if (item.getStatus() == PackageHistory.StatusEnum.RESERVED.ordinal())
                status = "رزرو شده";
            else if (item.getStatus() == PackageHistory.StatusEnum.CANCELED.ordinal())
                status = "لغو شده";
            else if (item.getStatus() == PackageHistory.StatusEnum.PERIOD_FINISHED.ordinal())
                status = "تاریخ اعتبار بسته به پایان رسیده است.";
            else if (item.getStatus() == PackageHistory.StatusEnum.TRAFFIC_FINISHED.ordinal())
                status = "حجم بسته به پایان رسیده است";
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

        holder.fill( item, position);
        return convertView;
    }
}
