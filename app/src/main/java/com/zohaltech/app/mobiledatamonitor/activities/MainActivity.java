package com.zohaltech.app.mobiledatamonitor.activities;

import com.zohaltech.app.mobiledatamonitor.classes.AlarmHandler;
import com.zohaltech.app.mobiledatamonitor.R;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends EnhancedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startMonitoring(View view) {
        AlarmHandler.start(this);
        Toast.makeText(this, "Monitoring has been started!", Toast.LENGTH_SHORT).show();
    }

    public void stopMonitoring(View view) {
        AlarmHandler.cancel();
        Toast.makeText(this, "Monitoring has been stopped!", Toast.LENGTH_SHORT).show();
    }
}
