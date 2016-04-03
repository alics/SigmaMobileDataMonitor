package com.zohaltech.app.sigma.dal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zohaltech.app.sigma.classes.MyRuntimeException;
import com.zohaltech.app.sigma.entities.Setting;

import java.util.ArrayList;

public class Settings {

    static final String TableName                          = "Settings";
    static final String Id                                 = "Id";
    static final String DataConnected                      = "DataConnected";
    static final String DailyTraffic                       = "DailyTraffic";
    static final String AlarmType                          = "AlarmType";
    static final String PercentTrafficAlarm                = "PercentTrafficAlarm";
    static final String LeftDaysAlarm                      = "LeftDaysAlarm";
    static final String AlarmTypeRes                       = "AlarmTypeRes";
    static final String PercentTrafficAlarmRes             = "PercentTrafficAlarmRes";
    static final String LeftDaysAlarmRes                   = "LeftDaysAlarmRes";
    static final String ShowNotification                   = "ShowNotification";
    static final String ShowNotificationWhenDataOrWifiIsOn = "ShowNotificationWhenDataIsOn";
    static final String ShowWifiUsage                      = "ShowWifiUsage";
    static final String ShowNotificationInLockScreen       = "ShowNotificationInLockScreen";
    static final String ShowUpDownSpeed                    = "ShowUpDownSpeed";
    static final String VibrateInAlarms                    = "VibrateInAlarms";
    static final String SoundInAlarms                      = "SoundInAlarms";
    static final String DailyTrafficLimitationAlarm        = "DailyTrafficLimitationAlarm";
    static final String DailyTrafficLimitation             = "DailyTrafficLimitation";

    static final String CreateTable = "CREATE TABLE " + TableName + " (" +
                                      Id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                      DataConnected + " BOOLEAN NOT NULL, " +
                                      DailyTraffic + " BIGINT NOT NULL, " +
                                      AlarmType + " INTEGER NOT NULL, " +
                                      PercentTrafficAlarm + " INTEGER NOT NULL, " +
                                      LeftDaysAlarm + " INTEGER NOT NULL, " +
                                      AlarmTypeRes + " INTEGER NOT NULL, " +
                                      PercentTrafficAlarmRes + " INTEGER NOT NULL, " +
                                      LeftDaysAlarmRes + " INTEGER NOT NULL, " +
                                      ShowNotification + " BOOLEAN NOT NULL, " +
                                      ShowNotificationWhenDataOrWifiIsOn + " BOOLEAN NOT NULL, " +
                                      ShowWifiUsage + " BOOLEAN NOT NULL, " +
                                      ShowUpDownSpeed + " BOOLEAN NOT NULL, " +
                                      VibrateInAlarms + " BOOLEAN NOT NULL, " +
                                      SoundInAlarms + " BOOLEAN NOT NULL, " +
                                      DailyTrafficLimitationAlarm + " BOOLEAN NOT NULL, " +
                                      DailyTrafficLimitation + " BIGINT NOT NULL, " +
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
                                                  cursor.getInt(cursor.getColumnIndex(AlarmType)),
                                                  cursor.getInt(cursor.getColumnIndex(PercentTrafficAlarm)),
                                                  cursor.getInt(cursor.getColumnIndex(LeftDaysAlarm)),
                                                  cursor.getInt(cursor.getColumnIndex(AlarmTypeRes)),
                                                  cursor.getInt(cursor.getColumnIndex(PercentTrafficAlarmRes)),
                                                  cursor.getInt(cursor.getColumnIndex(LeftDaysAlarmRes)),
                                                  cursor.getInt(cursor.getColumnIndex(ShowNotification)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(ShowNotificationWhenDataOrWifiIsOn)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(ShowWifiUsage)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(ShowNotificationInLockScreen)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(ShowUpDownSpeed)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(VibrateInAlarms)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(SoundInAlarms)) == 1,
                                                  cursor.getInt(cursor.getColumnIndex(DailyTrafficLimitationAlarm)) == 1,
                                                  cursor.getLong(cursor.getColumnIndex(DailyTrafficLimitation))
                    );
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

    public static long update(Setting setting) {
        ContentValues values = new ContentValues();

        values.put(DataConnected, setting.getDataConnected() ? 1 : 0);
        values.put(DailyTraffic, setting.getDailyTraffic());
        values.put(AlarmType, setting.getAlarmType());
        values.put(PercentTrafficAlarm, setting.getPercentTrafficAlarm());
        values.put(LeftDaysAlarm, setting.getLeftDaysAlarm());
        values.put(AlarmTypeRes, setting.getAlarmTypeRes());
        values.put(PercentTrafficAlarmRes, setting.getPercentTrafficAlarmRes());
        values.put(LeftDaysAlarmRes, setting.getLeftDaysAlarmRes());
        values.put(ShowNotification, setting.getShowNotification() ? 1 : 0);
        values.put(ShowNotificationWhenDataOrWifiIsOn, setting.getShowNotificationWhenDataOrWifiIsOn() ? 1 : 0);
        values.put(ShowWifiUsage, setting.getShowWifiUsage() ? 1 : 0);
        values.put(ShowNotificationInLockScreen, setting.getShowNotificationInLockScreen() ? 1 : 0);
        values.put(ShowUpDownSpeed, setting.getShowUpDownSpeed() ? 1 : 0);
        values.put(VibrateInAlarms, setting.getVibrateInAlarms() ? 1 : 0);
        values.put(SoundInAlarms, setting.getSoundInAlarms() ? 1 : 0);
        values.put(DailyTrafficLimitationAlarm, setting.getDailyTrafficLimitationAlarm() ? 1 : 0);
        values.put(DailyTrafficLimitation, setting.getDailyTrafficLimitation());

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

        return (count == 0) ? null : settings.get(0);
    }
}
