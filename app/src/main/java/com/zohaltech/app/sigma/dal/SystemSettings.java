package com.zohaltech.app.sigma.dal;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.sigma.classes.MyRuntimeException;
import com.zohaltech.app.sigma.entities.Setting;
import com.zohaltech.app.sigma.entities.SystemSetting;

import java.util.ArrayList;

public class SystemSettings {

    static final String TableName                      = "SystemSettings";
    static final String Id                             = "Id";
    static final String LeftDaysAlarmHasShown          = "LeftDaysAlarmHasShown";
    static final String TrafficAlarmHasShown           = "TrafficAlarmHasShown";
    static final String PrimaryTrafficFinishHasShown   = "PrimaryTrafficFinishHasShown";
    static final String SecondaryTrafficFinishHasShown = "SecondaryTrafficFinishHasShown";
    static final String Installed                      = "Installed";
    static final String Registered                     = "Registered";
    static final String ActiveSim                      = "ActiveSim";

    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
                                      Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                      LeftDaysAlarmHasShown + " BOOLEAN NOT NULL, " +
                                      TrafficAlarmHasShown + " BOOLEAN NOT NULL, " +
                                      PrimaryTrafficFinishHasShown + " BOOLEAN NOT NULL, " +
                                      SecondaryTrafficFinishHasShown + " BOOLEAN NOT NULL, " +
                                      Installed + " BOOLEAN NOT NULL, " +
                                      Registered + " BOOLEAN NOT NULL , " +
                                      ActiveSim + " INTEGER NOT NULL ); ";

    static final String DropTable = "Drop Table If Exists " + TableName;

    private static ArrayList<SystemSetting> select(String whereClause, String[] selectionArgs) {
        ArrayList<SystemSetting> settings = new ArrayList<>();
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;

        try {
            String query = "Select * From " + TableName + " " + whereClause;
            cursor = db.rawQuery(query, selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    SystemSetting systemSetting = new SystemSetting(cursor.getInt(cursor.getColumnIndex(Id)),
                                                                    cursor.getInt(cursor.getColumnIndex(LeftDaysAlarmHasShown)) == 1,
                                                                    cursor.getInt(cursor.getColumnIndex(TrafficAlarmHasShown)) == 1,
                                                                    cursor.getInt(cursor.getColumnIndex(PrimaryTrafficFinishHasShown)) == 1,
                                                                    cursor.getInt(cursor.getColumnIndex(SecondaryTrafficFinishHasShown)) == 1,
                                                                    cursor.getInt(cursor.getColumnIndex(Installed)) == 1,
                                                                    cursor.getInt(cursor.getColumnIndex(Registered)) == 1,
                                                                    cursor.getInt(cursor.getColumnIndex(ActiveSim)));
                    settings.add(systemSetting);
                } while (cursor.moveToNext());
            }
        } catch (MyRuntimeException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
            if (db != null && db.isOpen())
                db.close();
        }
        return settings;
    }


    public static long update(SystemSetting setting) {
        ContentValues values = new ContentValues();

        values.put(LeftDaysAlarmHasShown, setting.getLeftDaysAlarmHasShown() ? 1 : 0);
        values.put(TrafficAlarmHasShown, setting.getTrafficAlarmHasShown() ? 1 : 0);
        values.put(PrimaryTrafficFinishHasShown, setting.getPrimaryTrafficFinishHasShown() ? 1 : 0);
        values.put(SecondaryTrafficFinishHasShown, setting.getSecondaryTrafficFinishHasShown() ? 1 : 0);
        values.put(Installed, setting.getInstalled() ? 1 : 0);
        values.put(Registered, setting.getRegistered() ? 1 : 0);
        values.put(ActiveSim, setting.getActiveSim());

        DataAccess da = new DataAccess();
        return da.update(TableName, values, Id + " = ? ", new String[]{String.valueOf(setting.getId())});
    }

    public static long delete(Setting setting) {
        DataAccess db = new DataAccess();
        return db.delete(TableName, Id + " =? ", new String[]{String.valueOf(setting.getId())});
    }

    public static SystemSetting getCurrentSettings() {
        ArrayList<SystemSetting> settings = select("", null);
        int count = settings.size();

        return (count == 0) ? null : settings.get(0);
    }
}
