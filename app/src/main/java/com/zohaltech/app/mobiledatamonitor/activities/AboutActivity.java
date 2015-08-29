package com.zohaltech.app.mobiledatamonitor.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zohaltech.app.mobiledatamonitor.BuildConfig;
import com.zohaltech.app.mobiledatamonitor.R;

import widgets.MyToast;

public class AboutActivity extends EnhancedActivity {

    TextView txtVersion;
    Button   btnShare;
    Button   btnFeedback;
    Button   btnRate;

    @Override
    void onCreated() {
        setContentView(R.layout.activity_about);

        txtVersion = (TextView) findViewById(R.id.txtVersion);
        btnShare = (Button) findViewById(R.id.btnShare);
        btnFeedback = (Button) findViewById(R.id.btnFeedback);
        btnRate = (Button) findViewById(R.id.btnRate);

        final String marketWebsiteUri = "http://cafebazaar.ir/app/" + getPackageName();
        final String email = "info@zohaltech.com";
        final String marketUri = "bazaar://details?id=" + getPackageName();

        txtVersion.setText("نسخه " + BuildConfig.VERSION_NAME);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String message = String.format(getResources().getString(R.string.sharing_message),
                                               getResources().getString(R.string.app_name),
                                               marketWebsiteUri);
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
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(marketUri));
                if (!myStartActivity(intent)) {
                    intent.setData(Uri.parse(marketWebsiteUri));
                    if (!myStartActivity(intent)) {
                        MyToast.show(String.format(getResources().getString(R.string.could_not_open_market), "بازار", "بازار"), Toast.LENGTH_SHORT);
                    }
                }
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
        txtToolbarTitle.setText("درباره");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
