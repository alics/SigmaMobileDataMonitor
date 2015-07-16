package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

public class App extends Application {

    public static Context           context;
    public static Activity          currentActivity;
    public static SharedPreferences preferences;
    public static Typeface appFont;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        appFont = Typeface.createFromAsset(context.getAssets(), "fonts/default.ttf");
    }
}
