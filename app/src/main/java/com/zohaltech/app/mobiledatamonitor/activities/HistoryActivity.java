package com.zohaltech.app.mobiledatamonitor.activities;

import android.database.DataSetObserver;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.adapters.HistoryAdapter;
import com.zohaltech.app.mobiledatamonitor.dal.PackageHistories;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;

import java.util.ArrayList;

public class HistoryActivity extends EnhancedActivity {

    ListView lstPackagesHistories;
    TextView txtNothingFound;

    ArrayList<PackageHistory> packageHistories;
    HistoryAdapter            adapter;

    @Override
    void onCreated() {
        setContentView(R.layout.activity_history);
        lstPackagesHistories = (ListView) findViewById(R.id.lstPackagesHistories);
        txtNothingFound = (TextView) findViewById(R.id.txtNothingFound);
        packageHistories = PackageHistories.select();
        adapter = new HistoryAdapter(packageHistories);

        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (adapter.getCount() > 0) {
                    txtNothingFound.setVisibility(View.GONE);
                } else {
                    txtNothingFound.setVisibility(View.VISIBLE);
                }
            }
        });

        lstPackagesHistories.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
