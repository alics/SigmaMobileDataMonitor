package com.zohaltech.app.mobiledatamonitor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.activities.MainActivity;
import com.zohaltech.app.mobiledatamonitor.adapters.ReportAdapter;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.DataUsageService;
import com.zohaltech.app.mobiledatamonitor.classes.Helper;
import com.zohaltech.app.mobiledatamonitor.dal.DailyTrafficHistories;
import com.zohaltech.app.mobiledatamonitor.entities.TrafficMonitor;

import java.util.ArrayList;

import widgets.MyFragment;
import widgets.MyToast;

public class ReportFragment extends MyFragment {

    ListView lstTraffics;
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

        View rootView = inflater.inflate(R.layout.fragment_report, container, false);

        lstTraffics = (ListView) rootView.findViewById(R.id.lstTraffics);

        trafficMonitors = DailyTrafficHistories.getMonthlyTraffic();
        long bytes = App.preferences.getLong(DataUsageService.DAILY_USAGE_BYTES, 0);
        trafficMonitors.add(0, new TrafficMonitor(bytes, Helper.getCurrentDate()));
        adapter = new ReportAdapter(trafficMonitors);
        lstTraffics.setAdapter(adapter);

        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        return rootView;
    }

    //@Override
    //public void onResume() {
    //    super.onResume();
    //    App.handler.postDelayed(new Runnable() {
    //        @Override
    //        public void run() {
    //            ArrayList<TrafficMonitor> trafficMonitors = DailyTrafficHistories.getMonthlyTraffic();
    //            ReportAdapter adapter = new ReportAdapter(trafficMonitors);
    //            trafficMonitors.add(0, new TrafficMonitor(App.preferences.getLong(DataUsageService.DAILY_USAGE_BYTES, 0), Helper.getCurrentDate()));
    //            lstTraffics.setAdapter(adapter);
    //        }
    //    }, 50);
    //}

    @Override
    public void onDetach() {
        super.onDetach();
        adapter = null;
        lstTraffics.setAdapter(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.menu_dashboard, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            close();
        }
        //else if (id == R.id.action_settings) {
        //    trafficMonitors.clear();
        //    trafficMonitors.addAll(DailyTrafficHistories.getMonthlyTraffic());
        //    long bytes = ((MainActivity)getActivity()).dailyUsage;
        //    trafficMonitors.add(0, new TrafficMonitor(bytes, Helper.getCurrentDate()));
        //    adapter.notifyDataSetChanged();
        //}
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
