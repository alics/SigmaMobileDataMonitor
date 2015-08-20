package com.zohaltech.app.mobiledatamonitor.activities;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import com.zohaltech.app.mobiledatamonitor.R;

public class ApplicationMessageActivity extends Activity {

    //public static final String NOTIFICATION_ID_KEY = "NOTIFICATION_ID";
    //public static final String SYSTEM_MESSAGES_KEY = "SYSTEM_MESSAGES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_application_message);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setFinishOnTouchOutside(false);
        }
        //todo initialize
    }


    @Override
    public void onBackPressed() {
        //do nothing
    }
}
