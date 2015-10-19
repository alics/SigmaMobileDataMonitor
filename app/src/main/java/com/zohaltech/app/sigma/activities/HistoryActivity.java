package com.zohaltech.app.sigma.activities;

import android.database.DataSetObserver;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.adapters.HistoryAdapter;
import com.zohaltech.app.sigma.classes.DialogManager;
import com.zohaltech.app.sigma.dal.PackageHistories;
import com.zohaltech.app.sigma.entities.PackageHistory;

import java.util.ArrayList;

public class HistoryActivity extends EnhancedActivity {

    ListView lstPackagesHistories;
    TextView txtNothingFound;

    ArrayList<PackageHistory> packageHistories = new ArrayList<>();
    HistoryAdapter adapter;

    @Override
    void onCreated() {
        setContentView(R.layout.activity_history);
        lstPackagesHistories = (ListView) findViewById(R.id.lstPackagesHistories);
        txtNothingFound = (TextView) findViewById(R.id.txtNothingFound);
        packageHistories.addAll(PackageHistories.select());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_delete) {
            DialogManager.showConfirmationDialog(this, "حذف سوابق", "آیا تمامی سوابق بسته ها به غیر از بسته فعال و بسته رزرو حذف شوند؟", "بله", "خیر", null, new Runnable() {
                @Override
                public void run() {
                    if (PackageHistories.clear() > 0) {
                        packageHistories.clear();
                        packageHistories.addAll(PackageHistories.select());
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    void onToolbarCreated() {
        txtToolbarTitle.setText(getString(R.string.package_history));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
