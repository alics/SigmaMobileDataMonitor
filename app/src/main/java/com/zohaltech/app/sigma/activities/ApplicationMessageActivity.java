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

public class ApplicationMessageActivity extends Activity {

    TextView txtCaption;
    TextView txtMessage;
    Button   positiveButton;
    Button   negativeButton;

    public static final String MESSAGES_KEY = "MESSAGES";

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
                Intent intent = new Intent(ApplicationMessageActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}
