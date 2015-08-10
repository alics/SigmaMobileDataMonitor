package com.zohaltech.app.mobiledatamonitor.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;
import com.zohaltech.app.mobiledatamonitor.fragments.DashboardFragment;
import com.zohaltech.app.mobiledatamonitor.fragments.HistoryFragment;
import com.zohaltech.app.mobiledatamonitor.fragments.ManagementFragment;
import com.zohaltech.app.mobiledatamonitor.fragments.PackagesFragment;
import com.zohaltech.app.mobiledatamonitor.fragments.ReportFragment;

import widgets.MyFragment;
import widgets.MyTextView;

public class MainActivity extends EnhancedActivity {

    public AnimType animType = AnimType.OPEN;
    MyFragment fragment = null;
    Toolbar  toolbar;
    TextView txtTitle;
    boolean notified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("");

        txtTitle = new MyTextView(this);
        txtTitle.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        txtTitle.setTextColor(Color.WHITE);
        txtTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        txtTitle.setGravity(Gravity.CENTER);
        toolbar.addView(txtTitle);

        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        displayView(EnumFragment.DASHBOARD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (notified) {
            displayView(EnumFragment.DASHBOARD);
            notified = false;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        notified = intent.getBooleanExtra("NOTIFIED", false);
    }

    @Override
    public void onBackPressed() {
        fragment.onBackPressed();
    }

    public void displayView(EnumFragment input) {
        fragment = null;
        String title = getString(R.string.app_name);
        switch (input) {
            case DASHBOARD:
                fragment = new DashboardFragment();
                title = getString(R.string.app_name);
                break;
            case REPORT:
                fragment = new ReportFragment();
                title = "گزارش مصرف";
                break;
            case PACKAGES:
                fragment = new PackagesFragment();
                title = "خرید بسته";
                break;
            case HISTORY:
                fragment = new HistoryFragment();
                title = "سوابق بسته ها";
                break;
            case MANAGEMENT:
                fragment = new ManagementFragment();
                title = "مدیریت بسته";
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (animType == AnimType.OPEN) {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            } else {
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            //toolbar.setTitle(title);
            txtTitle.setText(title);
        }
    }

    public enum EnumFragment {
        DASHBOARD,
        REPORT,
        PACKAGES,
        HISTORY,
        MANAGEMENT
    }

    public enum AnimType {
        OPEN,
        CLOSE
    }
}
