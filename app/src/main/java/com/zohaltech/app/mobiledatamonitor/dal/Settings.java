package com.zohaltech.app.mobiledatamonitor.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.mobiledatamonitor.classes.MyRuntimeException;
import com.zohaltech.app.mobiledatamonitor.entities.Setting;

import java.util.ArrayList;

public class Settings {

    static final String TableName                    = "Settings";
    static final String Id                           = "Id";
    static final String MonitoringServiceOn          = "MonitoringServiceOn";
    static final String DailyTraffic                 = "DailyTraffic";
    static final String DcDataAfterTerminate         = "DcDataAfterTerminate";
    static final String AlarmType                    = "AlarmType";
    static final String RemindedByteAlarm            = "RemindedByteAlarm";
    static final String LeftDaysAlarm                = "LeftDaysAlarm";
    static final String AlarmTypeRes                 = "AlarmTypeRes";
    static final String RemindedByteAlarmRes         = "RemindedByteAlarmRes";
    static final String LeftDaysAlarmRes             = "LeftDaysAlarmRes";
    static final String ShowNotification             = "ShowNotification";
    static final String ShowNotificationWhenDataIsOn = "ShowNotificationWhenDataIsOn";
    static final String ShowNotificationInLockScreen = "ShowNotificationWhenDataIsOn";

    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
                                      Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                      MonitoringServiceOn + " BOOLEAN NOT NULL, " +
                                      DailyTraffic + " BIGINT NOT NULL, " +
                                      DcDataAfterTerminate + " BOOLEAN NOT NULL, " +
                                      AlarmType + " INTEGER NOT NULL, " +
                                      RemindedByteAlarm + " BIGINT NOT NULL, " +
                                      LeftDaysAlarm + " INTEGER NOT NULL, " +
                                      AlarmTypeRes + " INTEGER NOT NULL, " +
                                      RemindedByteAlarmRes + " BIGINT NOT NULL, " +
                                      LeftDaysAlarmRes + " INTEGER NOT NULL, " +
                                      ShowNotification + " BOOLEAN NOT NULL, " +
                                      ShowNotificationWhenDataIsOn + " BOOLEAN NOT NULL, " +
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
                                                  cursor.getInt(cursor.getColumnIndex(MonitoringServiceOn)) == 1,
                                                  cursor.getLong(cursor.getColumnIndex(DailyTraffic)),
                                                  cursor.getInt(cursor.getColumnIndex(DcDataAfterTerminate)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(AlarmType)),
                                                  cursor.getLong(cursor.getColumnIndex(RemindedByteAlarm)),
                                                  cursor.getInt(cursor.getColumnIndex(LeftDaysAlarm)),
                                                  cursor.getInt(cursor.getColumnIndex(AlarmTypeRes)),
                                                  cursor.getLong(cursor.getColumnIndex(RemindedByteAlarmRes)),
                                                  cursor.getInt(cursor.getColumnIndex(LeftDaysAlarmRes)),
                                                  cursor.getInt(cursor.getColumnIndex(ShowNotification)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(ShowNotificationWhenDataIsOn)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(ShowNotificationInLockScreen)) == 1);
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

    public static long insert(Setting setting) {
        ContentValues values = new ContentValues();

        values.put(MonitoringServiceOn, setting.getMonitoringServiceOn() ? 1 : 0);
        values.put(DailyTraffic, setting.getDailyTraffic());
        values.put(DcDataAfterTerminate, setting.getDcDataAfterTerminate() ? 1 : 0);
        values.put(AlarmType, setting.getAlarmType());
        values.put(RemindedByteAlarm, setting.getRemindedByteAlarm());
        values.put(LeftDaysAlarm, setting.getLeftDaysAlarm());
        values.put(AlarmTypeRes, setting.getAlarmTypeRes());
        values.put(RemindedByteAlarmRes, setting.getRemindedByteAlarmRes());
        values.put(LeftDaysAlarmRes, setting.getLeftDaysAlarmRes());
        values.put(ShowNotification, setting.getShowNotification() ? 1 : 0);
        values.put(ShowNotificationWhenDataIsOn, setting.getShowNotificationWhenDataIsOn() ? 1 : 0);
        values.put(ShowNotificationInLockScreen, setting.getShowNotificationInLockScreen() ? 1 : 0);

        DataAccess da = new DataAccess();
        return da.insert(TableName, values);
    }

    public static long update(Setting setting) {
        ContentValues values = new ContentValues();

        values.put(MonitoringServiceOn, setting.getMonitoringServiceOn() ? 1 : 0);
        values.put(DailyTraffic, setting.getDailyTraffic());
        values.put(DcDataAfterTerminate, setting.getDcDataAfterTerminate() ? 1 : 0);
        values.put(AlarmType, setting.getAlarmType());
        values.put(RemindedByteAlarm, setting.getRemindedByteAlarm());
        values.put(LeftDaysAlarm, setting.getLeftDaysAlarm());
        values.put(AlarmTypeRes, setting.getAlarmTypeRes());
        values.put(RemindedByteAlarmRes, setting.getRemindedByteAlarmRes());
        values.put(LeftDaysAlarmRes, setting.getLeftDaysAlarmRes());
        values.put(ShowNotification, setting.getShowNotification() ? 1 : 0);
        values.put(ShowNotificationWhenDataIsOn, setting.getShowNotificationWhenDataIsOn() ? 1 : 0);
        values.put(ShowNotificationInLockScreen, setting.getShowNotificationInLockScreen() ? 1 : 0);

        DataAccess da = new DataAccess();
        return da.update(TableName, values, Id + " =? ", new String[]{String.valueOf(setting.getId())});
    }

    public static long delete(Setting setting) {
        DataAccess db = new DataAccess();
        return db.delete(TableName, Id + " =? ", new String[]{String.valueOf(setting.getId())});
    }

    public static Setting getCurrentSettings() {
        ArrayList<Setting> settings = select(null, null);
        int count = settings.size();

        return (count == 0) ? null : settings.get(count - 1);
    }
}
