package com.zohaltech.app.sigma.classes;

import android.app.Activity;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;

import java.util.Locale;

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
    public static Locale            locale;
    public static KeyguardManager   keyguardManager;

    public static void setAppLocal() {
        locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = context.getResources().getConfiguration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        englishFont = Typeface.createFromAsset(context.getAssets(), "fonts/calibril.ttf");
        persianFont = Typeface.createFromAsset(context.getAssets(), "fonts/byekan.ttf");
        persianFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/byekan.ttf");
        handler = new Handler();
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        AlarmHandler.start(context);

        setAppLocal();

        Intent service = new Intent(context, SigmaDataService.class);
        context.startService(service);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}
