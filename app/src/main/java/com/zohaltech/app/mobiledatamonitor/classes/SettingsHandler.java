package com.zohaltech.app.mobiledatamonitor.classes;

public class SettingsHandler {

    private static final String PREF_IS_MONITORING_SERVICE_ON = "IS_MONITORING_SERVICE_ON";

    public static boolean isMonitoringServiceActive() {
        return App.preferences.getBoolean(PREF_IS_MONITORING_SERVICE_ON, true);
    }

    public static void setMonitoringServiceActive(boolean active) {
        App.preferences.edit().putBoolean(PREF_IS_MONITORING_SERVICE_ON, true);
    }
}
