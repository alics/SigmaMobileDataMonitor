package com.zohaltech.app.sigma.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zohaltech.app.sigma.BuildConfig;
import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.Helper;

import widgets.MyToast;

public class AboutActivity extends EnhancedActivity {

    TextView txtVersion;
    Button   btnShare;
    Button   btnFeedback;
    Button   btnRate;
    LinearLayout layoutWebsite;

    @Override
    void onCreated() {
        setContentView(R.layout.activity_about);

        txtVersion = (TextView) findViewById(R.id.txtVersion);
        btnShare = (Button) findViewById(R.id.btnShare);
        btnFeedback = (Button) findViewById(R.id.btnFeedback);
        btnRate = (Button) findViewById(R.id.btnRate);
        layoutWebsite = (LinearLayout) findViewById(R.id.layoutWebsite);

        final String email = "info@zohaltech.com";

        txtVersion.setText(getString(R.string.version) + BuildConfig.VERSION_NAME);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String message = String.format(getResources().getString(R.string.sharing_message),
                                               getResources().getString(R.string.app_name),
                                               App.marketWebsiteUri);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.share_title)));
            }
        });

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.feedback_subject));
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.feedback_title)));
            }
        });

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App.marketPollIntent);
                intent.setData(Uri.parse(App.marketPollUri));
                intent.setPackage(App.marketPackage);
                if (!myStartActivity(intent)) {
                    intent.setData(Uri.parse(App.marketWebsiteUri));
                    if (!myStartActivity(intent)) {
                        MyToast.show(String.format(getResources().getString(R.string.could_not_open_market), App.marketName, App.marketName), Toast.LENGTH_SHORT);
                    }
                }
            }
        });

        layoutWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.goToWebsite("http://zohaltech.com");
            }
        });
    }

    private boolean myStartActivity(Intent intent) {
        try {
            startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
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
        txtToolbarTitle.setText(getString(R.string.about));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
