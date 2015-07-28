package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;

public class App extends Application {

    public static Context           context;
    public static Activity          currentActivity;
    public static SharedPreferences preferences;
    public static Typeface          englishFont;
    public static Typeface          persianFont;
    public static Handler           handler;
    public static LayoutInflater    inflater;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        englishFont = Typeface.createFromAsset(context.getAssets(), "fonts/calibril.ttf");
        persianFont = Typeface.createFromAsset(context.getAssets(), "fonts/byekan.ttf");
        handler = new Handler();
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        //AlarmHandler.start(context);

        Intent service = new Intent(context, DataUsageUpdateService.class);
        context.startService(service);
    }
}
