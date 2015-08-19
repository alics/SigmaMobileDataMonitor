package com.zohaltech.app.mobiledatamonitor.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.classes.MyUncaughtExceptionHandler;

import widgets.MyTextView;

public abstract class EnhancedActivity extends AppCompatActivity {

    Toolbar  toolbar;
    TextView txtToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler(this));
        App.currentActivity = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
        toolbar.setTitle("");

        txtToolbarTitle = new MyTextView(this);
        txtToolbarTitle.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        txtToolbarTitle.setTextColor(Color.WHITE);
        txtToolbarTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        txtToolbarTitle.setGravity(Gravity.CENTER);
        toolbar.addView(txtToolbarTitle, 0);

        setSupportActionBar(toolbar);

        onToolbarCreated();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }

    abstract void onToolbarCreated();
}
