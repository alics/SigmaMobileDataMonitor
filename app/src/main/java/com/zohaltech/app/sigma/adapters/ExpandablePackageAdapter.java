package com.zohaltech.app.sigma.adapters;

import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.activities.PaymentActivity;
import com.zohaltech.app.sigma.classes.DialogManager;
import com.zohaltech.app.sigma.classes.Helper;
import com.zohaltech.app.sigma.classes.LicenseManager;
import com.zohaltech.app.sigma.entities.DataPackage;

import java.util.HashMap;
import java.util.List;

import widgets.AnimatedExpandableListView;

public class ExpandablePackageAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

    private FragmentActivity                   activity;
    private List<String>                       periods; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<DataPackage>> dataPackages;

    public ExpandablePackageAdapter(FragmentActivity activity, List<String> periods, HashMap<String, List<DataPackage>> dataPackages) {
        this.activity = activity;
        this.periods = periods;
        this.dataPackages = dataPackages;
    }

    @Override
    public DataPackage getChild(int groupPosition, int childPosititon) {
        return this.dataPackages.get(this.periods.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final DataPackage dataPackage = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            convertView = inflater.inflate(R.layout.package_item, null);
        }

        Button btnPurchase = (Button) convertView.findViewById(R.id.btnPurchase);
        Button btnActivate = (Button) convertView.findViewById(R.id.btnActivate);
        TextView txtPackage = (TextView) convertView.findViewById(R.id.txtPackage);
        txtPackage.setText(dataPackage.getPackageDescription());

        if (LicenseManager.getLicenseStatus() == LicenseManager.Status.REGISTERED) {

            btnPurchase.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   DialogManager.showConfirmationDialog(activity, "خرید بسته", "آیا مایل به خرید بسته " + dataPackage.getDescription() + " هستید؟", "بله", "خیر", null, new Runnable() {
                                                       @Override
                                                       public void run() {
                                                           Helper.runUssd(activity, dataPackage);
                                                       }
                                                   });
                                               }
                                           }
                                          );

            btnActivate.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   DialogManager.showPackageActivationDialog(dataPackage);
                                               }
                                           }
                                          );
        } else {
            btnPurchase.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_premium_small, 0, 0, 0);
            btnActivate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_premium_small, 0, 0, 0);
            btnPurchase.setText("   خرید   ");

            btnPurchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((PaymentActivity) activity).showPaymentDialog();
                }
            });

            btnActivate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((PaymentActivity)activity).showPaymentDialog();
                }
            });
        }

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return this.dataPackages.get(this.periods.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return this.periods.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.periods.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            convertView = inflater.inflate(R.layout.period_item, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.txtPeriod);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
