package com.zohaltech.app.sigma.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.classes.App;
import com.zohaltech.app.sigma.classes.Helper;
import com.zohaltech.app.sigma.dal.Settings;
import com.zohaltech.app.sigma.entities.Setting;

public class ApplicationAlarmActivity extends Activity {

    public static final String MESSAGES_KEY = "MESSAGES";
    TextView txtCaption;
    TextView txtMessage;
    Button   positiveButton;
    Button   negativeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_popup);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setFinishOnTouchOutside(false);
        }

        txtCaption = (TextView) findViewById(R.id.txtCaption);
        txtMessage = (TextView) findViewById(R.id.txtMessage);
        positiveButton = (Button) findViewById(R.id.positiveButton);
        negativeButton = (Button) findViewById(R.id.negativeButton);

        txtCaption.setText("پیغام " + getString(R.string.app_name));
        txtMessage.setText(getIntent().getStringExtra(MESSAGES_KEY));
        positiveButton.setText("باز کردن برنامه");
        negativeButton.setText("بسیار خب");

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ApplicationAlarmActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Setting setting = Settings.getCurrentSettings();
        if (setting.getVibrateInAlarms()) {
            App.handler.post(new Runnable() {
                @Override
                public void run() {
                    Helper.vibrate();
                }
            });
        }
        if (setting.getSoundInAlarms()) {
            App.handler.post(new Runnable() {
                @Override
                public void run() {
                    Helper.playSound();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}
