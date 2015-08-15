package com.zohaltech.app.mobiledatamonitor.classes;

public final class SettingsHandler {

    public enum AlarmType {RemindedBytes, LeftDay, Both}

    private static final String PREF_IS_MONITORING_SERVICE_ON = "IS_MONITORING_SERVICE_ON";
    private static final String PREF_DAILY_TRAFFIC            = "DAILY_TRAFFIC";
    private static final String PREF_LAST_LOG_DATE            = "LAST_LOG_DATE";
    private static final String PREF_DC_DATA_AFTER_TERMINATE  = "DC_DATA_AFTER_TERMINATE";
    private static final String PREF_ALARM_TYPE               = "ALARM_TYPE";
    private static final String PREF_REMINDED_BYTE_ALARM      = "REMINDED_BYTE_ALARM";
    private static final String PREF_LEFT_DAYS_ALARM          = "LEFT_DAYS_ALARM";
    private static final String PREF_ALARM_TYPE_RES           = "ALARM_TYPE_RES";
    private static final String PREF_REMINDED_BYTE_ALARM_RES  = "REMINDED_BYTE_ALARM_RES";
    private static final String PREF_LEFT_DAYS_ALARM_RES      = "LEFT_DAYS_ALARM_RES";


    public static boolean isMonitoringServiceActive() {
        return App.preferences.getBoolean(PREF_IS_MONITORING_SERVICE_ON, true);
    }

    public static void setMonitoringServiceActive(boolean active) {
        App.preferences.edit().putBoolean(PREF_IS_MONITORING_SERVICE_ON, active);
    }

    public static boolean dcDataAfterTerminatePackage() {
        return App.preferences.getBoolean(PREF_DC_DATA_AFTER_TERMINATE, true);
    }

    public static void setDcDataAfterTerminatePackage(boolean active) {
        App.preferences.edit().putBoolean(PREF_DC_DATA_AFTER_TERMINATE, active);
    }

    public static long getDailyTraffic() {
        return App.preferences.getLong(PREF_DAILY_TRAFFIC, 0);
    }

    public static void setDailyTraffic(long traffic) {
        App.preferences.edit().putLong(PREF_DAILY_TRAFFIC, traffic);
    }

    public static String getLastLogDate() {
        return App.preferences.getString(PREF_LAST_LOG_DATE, Helper.getCurrentDate());
    }

    public static void setLastLogDate() {
        App.preferences.edit().putString(PREF_LAST_LOG_DATE, Helper.getCurrentDate());
    }

    public static int getAlarmType() {
        return App.preferences.getInt(PREF_ALARM_TYPE, AlarmType.Both.ordinal());
    }

    public static void setAlarmType(int alarmType) {
        App.preferences.edit().putInt(PREF_ALARM_TYPE, alarmType);
    }

    public static Long getRemindedByteAlarm() {
        return App.preferences.getLong(PREF_REMINDED_BYTE_ALARM, 0);
    }

    public static void setRemindedByteAlarm(Long threshold) {
        App.preferences.edit().putLong(PREF_REMINDED_BYTE_ALARM, threshold);
    }

    public static int getLeftDaysAlarm() {
        return App.preferences.getInt(PREF_LEFT_DAYS_ALARM, 1);
    }

    public static void setLeftDaysAlarm(int leftDaysAlarm) {
        App.preferences.edit().putInt(PREF_LEFT_DAYS_ALARM, leftDaysAlarm);
    }

    public static int getAlarmTypeRes() {
        return App.preferences.getInt(PREF_ALARM_TYPE_RES, AlarmType.Both.ordinal());
    }

    public static void setAlarmTypeRes(int alarmType) {
        App.preferences.edit().putInt(PREF_ALARM_TYPE_RES, alarmType);
    }

    public static Long getRemindedByteAlarmRes() {
        return App.preferences.getLong(PREF_REMINDED_BYTE_ALARM_RES, 0);
    }

    public static void setRemindedByteAlarmRes(Long threshold) {
        App.preferences.edit().putLong(PREF_REMINDED_BYTE_ALARM_RES, threshold);
    }

    public static int getLeftDaysAlarmRes() {
        return App.preferences.getInt(PREF_LEFT_DAYS_ALARM_RES, 1);
    }

    public static void setLeftDaysAlarmRes(int leftDaysAlarm) {
        App.preferences.edit().putInt(PREF_LEFT_DAYS_ALARM_RES, leftDaysAlarm);
    }
}
