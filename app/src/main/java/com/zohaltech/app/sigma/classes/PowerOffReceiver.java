package com.zohaltech.app.sigma.classes;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;

public class PowerOffReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            File dir = new File(App.context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Boolean result = true;
            File file = new File(dir.getPath(), "power");
            if (!file.exists())
                result = file.createNewFile();
            if (result) {
                FileWriter writer = new FileWriter(file, false);

                writer.write("off" + "");
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
