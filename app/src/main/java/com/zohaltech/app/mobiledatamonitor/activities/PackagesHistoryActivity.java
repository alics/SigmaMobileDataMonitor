package com.zohaltech.app.mobiledatamonitor.activities;

import android.os.Bundle;
import android.widget.ListView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.adapters.PackagesHistoryAdapter;
import com.zohaltech.app.mobiledatamonitor.dal.PackageHistories;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;

import java.util.ArrayList;

public class PackagesHistoryActivity extends EnhancedActivity {

    ListView                  lstPackagesHistories;
    ArrayList<PackageHistory> packageHistories;
    PackagesHistoryAdapter    adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_history);
        initialize();
    }

    private void initialize() {
        lstPackagesHistories = (ListView) findViewById(R.id.lstPackagesHistories);
        packageHistories = PackageHistories.select();
        adapter = new PackagesHistoryAdapter(packageHistories);
        lstPackagesHistories.setAdapter(adapter);
    }
}
