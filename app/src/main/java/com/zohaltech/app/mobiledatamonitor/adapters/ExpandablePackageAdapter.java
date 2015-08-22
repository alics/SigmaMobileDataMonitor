package com.zohaltech.app.mobiledatamonitor.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.PackageSettingsActivity;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.DialogManager;
import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.dal.DataPackages;
import com.zohaltech.app.mobiledatamonitor.dal.PackageHistories;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;

import java.util.HashMap;
import java.util.List;

import widgets.AnimatedExpandableListView;

public class ExpandablePackageAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

    private Activity                           activity;
    private List<String>                       periods; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<DataPackage>> dataPackages;

    public ExpandablePackageAdapter(Activity activity, List<String> periods, HashMap<String, List<DataPackage>> dataPackages) {
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

        LinearLayout layoutPackage = (LinearLayout) convertView.findViewById(R.id.layoutPackage);
        TextView txtPackage = (TextView) convertView.findViewById(R.id.txtPackage);
        txtPackage.setText(dataPackage.getDescription());

        layoutPackage.setOnClickListener
                (
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                DialogManager.showConfirmationDialog(activity, "خرید بسته", "آیا مایل به خرید بسته هستید؟", "بله", "خیر", null, new Runnable() {
                                    @Override
                                    public void run() {
                                        //Helper.runUssd(activity, dataPackage.getUssdCode());
                                        Helper.runUssd(activity, dataPackage);

                                        //DialogManager.showConfirmationDialog(activity, "فعالسازی بسته", "آیا مایل به فعالسازی بسته " + dataPackage.getDescription() + " هستید؟",
                                        //                                     "بله", "خیر", null, new Runnable() {
                                        //            @Override
                                        //            public void run() {
                                        //                final PackageHistory history = PackageHistories.getActivePackage();
                                        //                if (history == null) {
                                        //                    PackageHistories.insert(new PackageHistory(dataPackage.getId(), Helper.getCurrentDateTime(), null, null, null, PackageHistory.StatusEnum.ACTIVE.ordinal()));
                                        //                    Intent intent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                                        //                    intent.putExtra(PackageSettingsActivity.INIT_MODE_KEY, PackageSettingsActivity.MODE_SETTING_ACTIVE);
                                        //                    intent.putExtra(PackageSettingsActivity.PACKAGE_ID_KEY, dataPackage.getId());
                                        //                    App.currentActivity.startActivity(intent);
                                        //
                                        //                } else {
                                        //                    DataPackage activePackage = DataPackages.selectPackageById(history.getDataPackageId());
                                        //                    DialogManager.showChoiceDialog(activity, "رزرو بسته", "هم اکنون یک بسته فعال " + activePackage.getTitle() + " وجود دارد،آیا بسته" + dataPackage.getDescription() + "به عنوان رزرو در نظر گرفته شود یا از ابتدا محاسبه گردد؟",
                                        //                                                   "از ابتدا محاسبه گردد", "رزرو شود", null, new Runnable() {
                                        //                                @Override
                                        //                                public void run() {
                                        //                                    PackageHistories.deletedReservedPackages();
                                        //                                    PackageHistories.terminateAll(PackageHistory.StatusEnum.CANCELED);
                                        //                                    PackageHistories.insert(new PackageHistory(dataPackage.getId(), Helper.getCurrentDateTime(), null, null, null, PackageHistory.StatusEnum.ACTIVE.ordinal()));
                                        //                                    Intent intent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                                        //                                    intent.putExtra(PackageSettingsActivity.INIT_MODE_KEY, PackageSettingsActivity.MODE_SETTING_ACTIVE);
                                        //                                    intent.putExtra(PackageSettingsActivity.PACKAGE_ID_KEY, dataPackage.getId());
                                        //                                    App.currentActivity.startActivity(intent);
                                        //                                }
                                        //
                                        //                            }, new Runnable() {
                                        //                                public void run() {
                                        //                                    PackageHistories.deletedReservedPackages();
                                        //                                    PackageHistories.insert(new PackageHistory(dataPackage.getId(), null, null, null, null, PackageHistory.StatusEnum.RESERVED.ordinal()));
                                        //                                    Intent intent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                                        //                                    intent.putExtra(PackageSettingsActivity.INIT_MODE_KEY, PackageSettingsActivity.MODE_SETTING_RESERVED);
                                        //                                    intent.putExtra(PackageSettingsActivity.PACKAGE_ID_KEY, dataPackage.getId());
                                        //                                    App.currentActivity.startActivity(intent);
                                        //                                }
                                        //                            });
                                        //                }
                                        //            }
                                        //        });
                                    }
                                });
                            }
                        }
                );

        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return this.dataPackages.get(this.periods.get(groupPosition)).size();
    }

    //@Override
    //public View getChildView(int groupPosition, final int childPosition,
    //                         boolean isLastChild, View convertView, ViewGroup parent) {
    //
    //    final String childText = (String) getChild(groupPosition, childPosition);
    //
    //    if (convertView == null) {
    //        LayoutInflater infalInflater = (LayoutInflater) this.activity
    //                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    //        convertView = infalInflater.inflate(R.layout.package_item, null);
    //    }
    //
    //    TextView txtListChild = (TextView) convertView
    //            .findViewById(R.id.txtPackage);
    //
    //    txtListChild.setText(childText);
    //    return convertView;
    //}
    //
    //@Override
    //public int getChildrenCount(int groupPosition) {
    //    return this.dataPackages.get(this.periods.get(groupPosition))
    //                              .size();
    //}

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
