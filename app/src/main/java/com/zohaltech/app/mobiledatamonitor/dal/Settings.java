package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.mobiledatamonitor.classes.MyRuntimeException;
import com.zohaltech.app.mobiledatamonitor.entities.Setting;

import java.util.ArrayList;

public class Settings {

    static final String TableName                     = "Settings";
    static final String Id                            = "Id";
    static final String DataConnected                 = "DataConnected";
    static final String DailyTraffic                  = "DailyTraffic";
    static final String ShowAlarmAfterTerminate       = "ShowAlarmAfterTerminate";
    static final String ShowAlarmAfterTerminateRes    = "ShowAlarmAfterTerminateRes";
    static final String AlarmType                     = "AlarmType";
    static final String PercentTrafficAlarm           = "PercentTrafficAlarm";
    static final String LeftDaysAlarm                 = "LeftDaysAlarm";
    static final String AlarmTypeRes                  = "AlarmTypeRes";
    static final String PercentTrafficAlarmRes        = "PercentTrafficAlarmRes";
    static final String LeftDaysAlarmRes              = "LeftDaysAlarmRes";
    static final String ShowNotification              = "ShowNotification";
    static final String ShowNotificationWhenDataIsOn  = "ShowNotificationWhenDataIsOn";
    static final String ShowNotificationInLockScreen  = "ShowNotificationInLockScreen";
    static final String LeftDaysAlarmHasShown         = "LeftDaysAlarmHasShown";
    static final String TrafficAlarmHasShown          = "TrafficAlarmHasShown";
    static final String SecondaryTrafficAlarmHasShown = "SecondaryTrafficAlarmHasShown";
    static final String ShowUpDownSpeed               = "ShowUpDownSpeed";
    static final String VibrateInAlarms               = "VibrateInAlarms";

    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
                                      Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                      DataConnected + " BOOLEAN NOT NULL, " +
                                      DailyTraffic + " BIGINT NOT NULL, " +
                                      ShowAlarmAfterTerminate + " BOOLEAN NOT NULL, " +
                                      ShowAlarmAfterTerminateRes + " BOOLEAN NOT NULL, " +
                                      AlarmType + " INTEGER NOT NULL, " +
                                      PercentTrafficAlarm + " INTEGER NOT NULL, " +
                                      LeftDaysAlarm + " INTEGER NOT NULL, " +
                                      AlarmTypeRes + " INTEGER NOT NULL, " +
                                      PercentTrafficAlarmRes + " INTEGER NOT NULL, " +
                                      LeftDaysAlarmRes + " INTEGER NOT NULL, " +
                                      ShowNotification + " BOOLEAN NOT NULL, " +
                                      ShowNotificationWhenDataIsOn + " BOOLEAN NOT NULL, " +
                                      LeftDaysAlarmHasShown + " BOOLEAN NOT NULL, " +
                                      TrafficAlarmHasShown + " BOOLEAN NOT NULL, " +
                                      SecondaryTrafficAlarmHasShown + " BOOLEAN NOT NULL, " +
                                      ShowUpDownSpeed + " BOOLEAN NOT NULL, " +
                                      VibrateInAlarms + " BOOLEAN NOT NULL, " +
                                      ShowNotificationInLockScreen + " BOOLEAN NOT NULL ); ";

    static final String DropTable = "Drop Table If Exists " + TableName;


    private static ArrayList<Setting> select(String whereClause, String[] selectionArgs) {
        ArrayList<Setting> settings = new ArrayList<>();
        DataAccess da = new DataAccess();
        SQLiteDatabase db = da.getReadableDB();
        Cursor cursor = null;

        try {
            String query = "Select * From " + TableName + " " + whereClause;
            cursor = db.rawQuery(query, selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Setting setting = new Setting(cursor.getInt(cursor.getColumnIndex(Id)),
                                                  cursor.getInt(cursor.getColumnIndex(DataConnected)) == 1,
                                                  cursor.getLong(cursor.getColumnIndex(DailyTraffic)),
                                                  cursor.getInt(cursor.getColumnIndex(ShowAlarmAfterTerminate)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(AlarmType)),
                                                  cursor.getInt(cursor.getColumnIndex(PercentTrafficAlarm)),
                                                  cursor.getInt(cursor.getColumnIndex(LeftDaysAlarm)),
                                                  cursor.getInt(cursor.getColumnIndex(ShowAlarmAfterTerminateRes)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(AlarmTypeRes)),
                                                  cursor.getInt(cursor.getColumnIndex(PercentTrafficAlarmRes)),
                                                  cursor.getInt(cursor.getColumnIndex(LeftDaysAlarmRes)),
                                                  cursor.getInt(cursor.getColumnIndex(ShowNotification)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(ShowNotificationWhenDataIsOn)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(ShowNotificationInLockScreen)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(ShowUpDownSpeed)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(LeftDaysAlarmHasShown)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(TrafficAlarmHasShown)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(SecondaryTrafficAlarmHasShown)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(VibrateInAlarms))==1);
                    settings.add(setting);
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

    //public static long insert(Setting setting) {
    //    ContentValues values = new ContentValues();
    //
    //    values.put(DataConnected, setting.getDataConnected() ? 1 : 0);
    //    values.put(DailyTraffic, setting.getDailyTraffic());
    //    values.put(ShowAlarmAfterTerminate, setting.getShowAlarmAfterTerminate() ? 1 : 0);
    //    values.put(ShowAlarmAfterTerminateRes, setting.getShowAlarmAfterTerminateRes() ? 1 : 0);
    //    values.put(AlarmType, setting.getAlarmType());
    //    values.put(PercentTrafficAlarm, setting.getPercentTrafficAlarm());
    //    values.put(LeftDaysAlarm, setting.getLeftDaysAlarm());
    //    values.put(AlarmTypeRes, setting.getAlarmTypeRes());
    //    values.put(PercentTrafficAlarmRes, setting.getPercentTrafficAlarmRes());
    //    values.put(LeftDaysAlarmRes, setting.getLeftDaysAlarmRes());
    //    values.put(ShowNotification, setting.getShowNotification() ? 1 : 0);
    //    values.put(ShowNotificationWhenDataIsOn, setting.getShowNotificationWhenDataIsOn() ? 1 : 0);
    //    values.put(ShowNotificationInLockScreen, setting.getShowNotificationInLockScreen() ? 1 : 0);
    //    values.put(TrafficAlarmHasShown, setting.getTrafficAlarmHasShown() ? 1 : 0);
    //    values.put(LeftDaysAlarmHasShown, setting.getLeftDaysAlarmHasShown() ? 1 : 0);
    //    values.put(SecondaryTrafficAlarmHasShown, setting.getSecondaryTrafficAlarmHasShown() ? 1 : 0);
    //    values.put(ShowUpDownSpeed, setting.getShowUpDownSpeed() ? 1 : 0);
    //
    //    DataAccess da = new DataAccess();
    //    return da.insert(TableName, values);
    //}

    public static long update(Setting setting) {
        ContentValues values = new ContentValues();

        values.put(DataConnected, setting.getDataConnected() ? 1 : 0);
        values.put(DailyTraffic, setting.getDailyTraffic());
        values.put(ShowAlarmAfterTerminate, setting.getShowAlarmAfterTerminate() ? 1 : 0);
        values.put(ShowAlarmAfterTerminateRes, setting.getShowAlarmAfterTerminateRes() ? 1 : 0);
        values.put(AlarmType, setting.getAlarmType());
        values.put(PercentTrafficAlarm, setting.getPercentTrafficAlarm());
        values.put(LeftDaysAlarm, setting.getLeftDaysAlarm());
        values.put(AlarmTypeRes, setting.getAlarmTypeRes());
        values.put(PercentTrafficAlarmRes, setting.getPercentTrafficAlarmRes());
        values.put(LeftDaysAlarmRes, setting.getLeftDaysAlarmRes());
        values.put(ShowNotification, setting.getShowNotification() ? 1 : 0);
        values.put(ShowNotificationWhenDataIsOn, setting.getShowNotificationWhenDataIsOn() ? 1 : 0);
        values.put(ShowNotificationInLockScreen, setting.getShowNotificationInLockScreen() ? 1 : 0);
        values.put(TrafficAlarmHasShown, setting.getTrafficAlarmHasShown() ? 1 : 0);
        values.put(LeftDaysAlarmHasShown, setting.getLeftDaysAlarmHasShown() ? 1 : 0);
        values.put(SecondaryTrafficAlarmHasShown, setting.getSecondaryTrafficAlarmHasShown() ? 1 : 0);
        values.put(ShowUpDownSpeed, setting.getShowUpDownSpeed() ? 1 : 0);
        values.put(VibrateInAlarms, setting.getVibrateInAlarms() ? 1 : 0);

        DataAccess da = new DataAccess();
        return da.update(TableName, values, Id + " = ? ", new String[]{String.valueOf(setting.getId())});
    }

    public static long delete(Setting setting) {
        DataAccess db = new DataAccess();
        return db.delete(TableName, Id + " =? ", new String[]{String.valueOf(setting.getId())});
    }

    public static Setting getCurrentSettings() {
        ArrayList<Setting> settings = select("", null);
        int count = settings.size();

        return (count == 0) ? null : settings.get(count - 1);
    }
}
