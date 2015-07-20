package com.zohaltech.app.mobiledatamonitor.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;

import java.util.HashMap;
import java.util.List;

import widgets.AnimatedExpandableListView;

public class ExpandablePackageAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> listDataChild;

    public ExpandablePackageAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.package_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.txtPackage);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
    }

    @Override
    //public View getChildView(int groupPosition, final int childPosition,
    //                         boolean isLastChild, View convertView, ViewGroup parent) {
    //
    //    final String childText = (String) getChild(groupPosition, childPosition);
    //
    //    if (convertView == null) {
    //        LayoutInflater infalInflater = (LayoutInflater) this.context
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
    //    return this.listDataChild.get(this.listDataHeader.get(groupPosition))
    //                              .size();
    //}

    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.group_item, null);
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
