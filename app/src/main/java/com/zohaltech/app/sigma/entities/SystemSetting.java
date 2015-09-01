package com.zohaltech.app.sigma.entities;


public class SystemSetting {
    private int id;
    private Boolean leftDaysAlarmHasShown;
    private Boolean trafficAlarmHasShown;
    private Boolean secondaryTrafficAlarmHasShown;
    private Boolean primaryTrafficFinishHasShown;
    private Boolean secondaryTrafficFinishHasShown;
    private Boolean installed;
    private Boolean registered;


    public SystemSetting(Boolean leftDaysAlarmHasShown, Boolean trafficAlarmHasShown, Boolean secondaryTrafficAlarmHasShown,
                         Boolean primaryTrafficFinishHasShown, Boolean secondaryTrafficFinishHasShown, Boolean installed, Boolean registered) {
        setLeftDaysAlarmHasShown(leftDaysAlarmHasShown);
        setTrafficAlarmHasShown(trafficAlarmHasShown);
        setSecondaryTrafficAlarmHasShown(secondaryTrafficAlarmHasShown);
        setPrimaryTrafficFinishHasShown(primaryTrafficFinishHasShown);
        setSecondaryTrafficFinishHasShown(secondaryTrafficFinishHasShown);
        setInstalled(installed);
        setRegistered(registered);

    }

    public SystemSetting(int id, Boolean leftDaysAlarmHasShown, Boolean trafficAlarmHasShown, Boolean secondaryTrafficAlarmHasShown,
                         Boolean primaryTrafficFinishHasShown, Boolean secondaryTrafficFinishHasShown, Boolean installed, Boolean registered) {
        this(leftDaysAlarmHasShown, trafficAlarmHasShown, secondaryTrafficAlarmHasShown, primaryTrafficFinishHasShown,
                secondaryTrafficFinishHasShown, installed, registered);
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getLeftDaysAlarmHasShown() {
        return leftDaysAlarmHasShown;
    }

    public void setLeftDaysAlarmHasShown(Boolean leftDaysAlarmHasShown) {
        this.leftDaysAlarmHasShown = leftDaysAlarmHasShown;
    }

    public Boolean getTrafficAlarmHasShown() {
        return trafficAlarmHasShown;
    }

    public void setTrafficAlarmHasShown(Boolean trafficAlarmHasShown) {
        this.trafficAlarmHasShown = trafficAlarmHasShown;
    }

    public Boolean getSecondaryTrafficAlarmHasShown() {
        return secondaryTrafficAlarmHasShown;
    }

    public void setSecondaryTrafficAlarmHasShown(Boolean secondaryTrafficAlarmHasShown) {
        this.secondaryTrafficAlarmHasShown = secondaryTrafficAlarmHasShown;
    }

    public Boolean getPrimaryTrafficFinishHasShown() {
        return primaryTrafficFinishHasShown;
    }

    public void setPrimaryTrafficFinishHasShown(Boolean primaryTrafficFinishHasShown) {
        this.primaryTrafficFinishHasShown = primaryTrafficFinishHasShown;
    }

    public Boolean getSecondaryTrafficFinishHasShown() {
        return secondaryTrafficFinishHasShown;
    }

    public void setSecondaryTrafficFinishHasShown(Boolean secondaryTrafficFinishHasShown) {
        this.secondaryTrafficFinishHasShown = secondaryTrafficFinishHasShown;
    }

    public Boolean getInstalled() {
        return installed;
    }

    public void setInstalled(Boolean installed) {
        this.installed = installed;
    }

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }
}
