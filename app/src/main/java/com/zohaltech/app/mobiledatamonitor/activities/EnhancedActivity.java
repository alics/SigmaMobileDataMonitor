package com.zohaltech.app.mobiledatamonitor.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.zohaltech.app.mobiledatamonitor.classes.App;

public class EnhancedActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        App.currentActivity = this;
    }
}
