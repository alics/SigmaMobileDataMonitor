package com.zohaltech.app.mobiledatamonitor.classes;

public final class SettingsHandler {

    public enum AlarmType {RemindedBytes, LeftDay, Both}

    private static final String PREF_IS_MONITORING_SERVICE_ON = "IS_MONITORING_SERVICE_ON";
    private static final String PREF_DAILY_TRAFFIC            = "DAILY_TRAFFIC";
    private static final String PREF_LAST_LOG_DATE            = "LAST_LOG_DATE";
    private static final String PREF_ALARM_TYPE               = "ALARM_TYPE";
    private static final String PREF_REMINDED_BYTE_ALARM      = "REMINDED_BYTE_ALARM";
    private static final String PREF_LEFT_DAYS_ALARM          = "LEFT_DAYS_ALARM";


    public static boolean isMonitoringServiceActive() {
        return App.preferences.getBoolean(PREF_IS_MONITORING_SERVICE_ON, true);
    }

    public static void setMonitoringServiceActive(boolean active) {
        App.preferences.edit().putBoolean(PREF_IS_MONITORING_SERVICE_ON, active);
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


    public static int getRemindedByteAlarm() {
        return App.preferences.getInt(PREF_REMINDED_BYTE_ALARM, 0);
    }

    public static void setRemindedByteAlarm(int threshold) {
        App.preferences.edit().putInt(PREF_REMINDED_BYTE_ALARM, threshold);
    }

    public static int getLeftDaysAlarm() {
        return App.preferences.getInt(PREF_LEFT_DAYS_ALARM, 0);
    }

    public static void setLeftDaysAlarm(int leftDaysAlarm) {
        App.preferences.edit().putInt(PREF_LEFT_DAYS_ALARM, leftDaysAlarm);
    }
}
