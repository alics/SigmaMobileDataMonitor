package com.zohaltech.app.mobiledatamonitor.activities;

import android.view.MenuItem;

import com.zohaltech.app.mobiledatamonitor.R;

public class AboutActivity extends EnhancedActivity {

    @Override
    void onCreated() {
        setContentView(R.layout.activity_about);
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
        txtToolbarTitle.setText("درباره");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
