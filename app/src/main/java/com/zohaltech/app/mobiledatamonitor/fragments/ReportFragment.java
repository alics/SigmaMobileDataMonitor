package com.zohaltech.app.mobiledatamonitor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.MainActivity;
import com.zohaltech.app.mobiledatamonitor.adapters.ReportAdapter;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.DataUsageService;
import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.classes.TrafficDisplay;
import com.zohaltech.app.mobiledatamonitor.dal.DailyTrafficHistories;
import com.zohaltech.app.mobiledatamonitor.entities.TrafficMonitor;

import java.util.ArrayList;

import widgets.MyFragment;
import widgets.MyToast;

public class ReportFragment extends MyFragment {

    ListView lstTraffics;
    TextView txtTotalTraffic;
    ArrayList<TrafficMonitor> trafficMonitors = new ArrayList<>();
    ReportAdapter adapter;

    public ReportFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lstTraffics = (ListView) view.findViewById(R.id.lstTraffics);
        txtTotalTraffic = (TextView) view.findViewById(R.id.txtTotalTraffic);

        //trafficMonitors = DailyTrafficHistories.getMonthlyTraffic();
        //long bytes = App.preferences.getLong(DataUsageService.DAILY_USAGE_BYTES, 0);
        //trafficMonitors.add(0, new TrafficMonitor(bytes, Helper.getCurrentDate()));
        //adapter = new ReportAdapter(trafficMonitors);
        //lstTraffics.setAdapter(adapter);

        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        trafficMonitors = DailyTrafficHistories.getMonthlyTraffic();
        long bytes = App.preferences.getLong(DataUsageService.DAILY_USAGE_BYTES, 0);
        trafficMonitors.add(0, new TrafficMonitor(bytes, Helper.getCurrentDate()));
        adapter = new ReportAdapter(trafficMonitors);
        lstTraffics.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        long sum = 0;
        for(TrafficMonitor trafficMonitor: trafficMonitors){
            sum+=trafficMonitor.getTotalTraffic();
        }
        txtTotalTraffic.setText(TrafficDisplay.getUsedTraffic(sum));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        adapter = null;
        lstTraffics.setAdapter(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            close();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        close();
    }

    private void close() {
        MainActivity parent = ((MainActivity) getActivity());
        parent.animType = MainActivity.AnimType.CLOSE;
        parent.displayView(MainActivity.EnumFragment.DASHBOARD);
    }
}
