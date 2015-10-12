package com.zohaltech.app.sigma.classes;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SigmaAppsService extends Service {
    AppDataUsageMeter appDataUsageMeter;

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

        appDataUsageMeter = new AppDataUsageMeter();
        appDataUsageMeter.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        appDataUsageMeter.shutdown();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
