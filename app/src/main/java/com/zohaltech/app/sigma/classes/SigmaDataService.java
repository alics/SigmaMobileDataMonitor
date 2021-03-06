package com.zohaltech.app.sigma.classes;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SigmaDataService extends Service {

    DataUsageMeter dataUsageMeter;

    //todo : uncomment below lines for app usages
    AppDataUsageMeter appDataUsageMeter;

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        dataUsageMeter = new DataUsageMeter(this);
        dataUsageMeter.execute();

        //todo : uncomment below lines for app usages
        appDataUsageMeter=new AppDataUsageMeter();
        appDataUsageMeter.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataUsageMeter.shutdown();

        //todo : uncomment below lines for app usages
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