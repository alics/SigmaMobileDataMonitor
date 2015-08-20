package com.zohaltech.app.mobiledatamonitor.activities;

import android.view.MenuItem;
import android.widget.ListView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.adapters.HistoryAdapter;
import com.zohaltech.app.mobiledatamonitor.dal.PackageHistories;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;

import java.util.ArrayList;

public class HistoryActivity extends EnhancedActivity {

    ListView                  lstPackagesHistories;
    ArrayList<PackageHistory> packageHistories;
    HistoryAdapter            adapter;

    @Override
    void onCreated() {
        setContentView(R.layout.activity_history);
        lstPackagesHistories = (ListView) findViewById(R.id.lstPackagesHistories);
        packageHistories = PackageHistories.select();
        adapter = new HistoryAdapter(packageHistories);
        lstPackagesHistories.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    void onToolbarCreated() {
        txtToolbarTitle.setText("سوابق بسته ها");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
