package com.zohaltech.app.sigma.classes;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zohaltech.app.sigma.dal.SnapshotStatus;

import java.io.IOException;

public class PowerOffReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            SnapshotStatus status = SnapshotStatus.getCurrentSnapshotStatus();
            if (status.getStatus() != SnapshotStatus.Running) {
                AppsTrafficSnapshot.captureSnapshot(SnapshotStatus.InitStatus.NORMAL);
            }
        }catch (MyRuntimeException e) {
            e.printStackTrace();
        }

        //if (status.getStatus() == SnapshotStatus.InitStatus.BEFORE_FIRST_BOOT.ordinal()) {
        //    status.setInitializationStatus(SnapshotStatus.InitStatus.NORMAL.ordinal());
        //    SnapshotStatus.update(status);
        //}

        //try {
        //    File dir = new File(App.context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath());
        //    if (!dir.exists()) {
        //        dir.mkdirs();
        //    }
        //    Boolean result = true;
        //    File file = new File(dir.getPath(), "power");
        //    if (!file.exists())
        //        result = file.createNewFile();
        //    if (result) {
        //        FileWriter writer = new FileWriter(file, false);
        //
        //        writer.write("off" + "");
        //        writer.flush();
        //        writer.close();
        //    }
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
    }
}
