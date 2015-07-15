package com.zohaltech.app.mobiledatamonitor.classes;

/**
 * Created by Ali on 7/15/2015.
 */
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class App extends Application {

    public static Context           context;
    public static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
