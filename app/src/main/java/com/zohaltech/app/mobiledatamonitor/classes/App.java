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
    public static Typeface          persianFontBold;
    public static Handler           handler;
    public static LayoutInflater    inflater;
    public static int               screenWidth;
    public static int               screenHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        englishFont = Typeface.createFromAsset(context.getAssets(), "fonts/calibril.ttf");
        persianFont = Typeface.createFromAsset(context.getAssets(), "fonts/default.ttf");
        //persianFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/iran_b.ttf");
        handler = new Handler();
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        AlarmHandler.start(context);

        Intent service = new Intent(context, DataUsageService.class);
        context.startService(service);
    }
}
