package com.zohaltech.app.mobiledatamonitor.classes;

public final class SettingsHandler {

    private static final String PREF_IS_MONITORING_SERVICE_ON = "IS_MONITORING_SERVICE_ON";
    private static final String PREF_DAILY_TRAFFIC            = "DAILY_TRAFFIC";
    private static final String PREF_LAST_LOG_DATE            = "LAST_LOG_DATE";

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

}
