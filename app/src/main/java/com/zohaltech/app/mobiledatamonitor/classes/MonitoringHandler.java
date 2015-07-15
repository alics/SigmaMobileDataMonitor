package com.zohaltech.app.mobiledatamonitor.classes;

/**
 * Created by Ali on 7/15/2015.
 */
public class MonitoringHandler {

    private static final String PREF_IS_MONITORING_ACTIVE = "IS_MONITORING_ACTIVE";

    public static boolean isActive() {
        return App.preferences.getBoolean(PREF_IS_MONITORING_ACTIVE, true);
    }

    public static void setActive(boolean active) {
        App.preferences.edit().putBoolean(PREF_IS_MONITORING_ACTIVE, true);
    }
}
