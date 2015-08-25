package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.Activity;
import android.os.Build;
import android.os.Environment;

import com.zohaltech.app.mobiledatamonitor.BuildConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class MyUncaughtExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler
{
    public MyUncaughtExceptionHandler() {
    }

    public void uncaughtException(Thread thread, final Throwable exception) {
        logException(exception);
    }

    public static void logException(final Throwable exception) {
        new Thread(new Runnable() {
            public void run() {
                StringBuilder errorReport = new StringBuilder();
                StringWriter stackTrace = new StringWriter();
                exception.printStackTrace(new PrintWriter(stackTrace));
                errorReport.append("************ CAUSE OF ERROR ************\n\n");
                errorReport.append(stackTrace.toString());
                errorReport.append("--------------------------------------------------\n");
                writeToFile(errorReport.toString());
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(10);
            }
        }).start();
    }

    private static void writeToFile(String errorText)
    {
        try
        {
            File file = new File(Environment.getExternalStorageDirectory(), "mobiledatamonitor_log.txt");
            if (!file.exists())
            {
                // file.mkdirs();
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file, true);
            writer.append(errorText);
            writer.flush();
            writer.close();
        } catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }

}
