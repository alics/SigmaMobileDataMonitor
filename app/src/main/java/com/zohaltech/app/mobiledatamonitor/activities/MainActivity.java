package com.zohaltech.app.mobiledatamonitor.activities;

import com.zohaltech.app.mobiledatamonitor.classes.AlarmHandler;
import com.zohaltech.app.mobiledatamonitor.R;
import com.zohaltech.app.mobiledatamonitor.classes.App;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends EnhancedActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnStartMonitoring).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmHandler.start(App.context);
                Toast.makeText(App.currentActivity, "Monitoring has been started!", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnStopMonitoring).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlarmHandler.cancel();
                Toast.makeText(App.currentActivity, "Monitoring has been stopped!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
