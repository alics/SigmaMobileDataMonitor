package com.zohaltech.app.mobiledatamonitor.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zohaltech.app.mobiledatamonitor.R;
import widgets.MyFragment;
import com.zohaltech.app.mobiledatamonitor.fragments.DashboardFragment;
import com.zohaltech.app.mobiledatamonitor.fragments.PackagesFragment;
import com.zohaltech.app.mobiledatamonitor.fragments.ReportFragment;

import widgets.MyTextView;

public class MainActivity extends EnhancedActivity {

    public AnimType animType = AnimType.OPEN;
    MyFragment fragment = null;
    Toolbar  toolbar;
    TextView txtTitle;

    Button v;


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
        PACKAGES
    }

    public enum AnimType {
        OPEN,
        CLOSE
    }
}
