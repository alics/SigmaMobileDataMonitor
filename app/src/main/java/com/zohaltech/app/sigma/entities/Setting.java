package com.zohaltech.app.sigma.entities;


public class Setting {

    public enum AlarmType {REMINDED_BYTES, LEFT_DAY, BOTH, NONE}

    private Integer id;
    private Boolean dataConnected;
    private Long dailyTraffic;
    private Boolean showAlarmAfterTerminate;
    private Boolean showAlarmAfterTerminateRes;
    private int alarmType;
    private Integer percentTrafficAlarm;
    private Integer leftDaysAlarm;
    private int alarmTypeRes;
    private Integer percentTrafficAlarmRes;
    private Integer leftDaysAlarmRes;
    private Boolean showNotification;
    private Boolean showNotificationWhenDataIsOn;
    private Boolean showNotificationInLockScreen;
    private Boolean leftDaysAlarmHasShown;
    private Boolean trafficAlarmHasShown;
    private Boolean secondaryTrafficAlarmHasShown;
    private Boolean showUpDownSpeed;
    private Boolean VibrateInAlarms;
    private Boolean SoundInAlarms;
    private Boolean Installed;
    private Boolean Registered;


    public Setting(Boolean dataConnected, Long dailyTraffic, Boolean showAlarmAfterTerminate, int alarmType,
                   Integer percentTrafficAlarm, Integer leftDaysAlarm, Boolean showAlarmAfterTerminateRes, int alarmTypeRes,
                   Integer percentTrafficAlarmRes, Integer leftDaysAlarmRes, Boolean showNotification, Boolean showNotificationWhenDataIsOn,
                   Boolean showNotificationInLockScreen, Boolean showUpDownSpeed, Boolean leftDaysAlarmHasShown, Boolean trafficAlarmHasShown,
                   Boolean secondaryTrafficAlarmHasShown, Boolean vibrateInAlarms, Boolean soundInAlarms, Boolean installed, Boolean registered) {
        setDataConnected(dataConnected);
        setDailyTraffic(dailyTraffic);
        setShowAlarmAfterTerminate(showAlarmAfterTerminate);
        setShowAlarmAfterTerminateRes(showAlarmAfterTerminateRes);
        setAlarmType(alarmType);
        setPercentTrafficAlarm(percentTrafficAlarm);
        setLeftDaysAlarm(leftDaysAlarm);
        setAlarmTypeRes(alarmTypeRes);
        setPercentTrafficAlarmRes(percentTrafficAlarmRes);
        setLeftDaysAlarmRes(leftDaysAlarmRes);
        setShowNotification(showNotification);
        setShowNotificationWhenDataIsOn(showNotificationWhenDataIsOn);
        setShowNotificationInLockScreen(showNotificationInLockScreen);
        setShowUpDownSpeed(showUpDownSpeed);
        setLeftDaysAlarmHasShown(leftDaysAlarmHasShown);
        setTrafficAlarmHasShown(trafficAlarmHasShown);
        setSecondaryTrafficAlarmHasShown(secondaryTrafficAlarmHasShown);
        setVibrateInAlarms(vibrateInAlarms);
        setSoundInAlarms(soundInAlarms);
        setInstalled(installed);
        setRegistered(registered);
    }

    public Setting(Integer id, Boolean dataConnected, Long dailyTraffic, Boolean showAlarmAfterTerminate,
                   int alarmType, Integer percentTrafficAlarm, Integer leftDaysAlarm, Boolean showAlarmAfterTerminateRes,
                   int alarmTypeRes, Integer percentTrafficAlarmRes, Integer leftDaysAlarmRes, Boolean showNotification,
                   Boolean showNotificationWhenDataIsOn, Boolean showNotificationInLockScreen, Boolean showUpDownSpeed,
                   Boolean leftDaysAlarmHasShown, Boolean trafficAlarmHasShown, Boolean secondaryTrafficAlarmHasShown,
                   Boolean vibrateInAlarms, Boolean soundInAlarms, Boolean installed, Boolean registered) {
        this(dataConnected, dailyTraffic, showAlarmAfterTerminate, alarmType, percentTrafficAlarm,
                leftDaysAlarm, showAlarmAfterTerminateRes, alarmTypeRes, percentTrafficAlarmRes, leftDaysAlarmRes,
                showNotification, showNotificationWhenDataIsOn, showNotificationInLockScreen, showUpDownSpeed,
                leftDaysAlarmHasShown, trafficAlarmHasShown, secondaryTrafficAlarmHasShown, vibrateInAlarms, soundInAlarms
                ,installed, registered);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getDataConnected() {
        return dataConnected;
    }

    public void setDataConnected(Boolean dataConnected) {
        this.dataConnected = dataConnected;
    }

    public Long getDailyTraffic() {
        return dailyTraffic;
    }

    public void setDailyTraffic(Long dailyTraffic) {
        this.dailyTraffic = dailyTraffic;
    }

    public Boolean getShowAlarmAfterTerminate() {
        return showAlarmAfterTerminate;
    }

    public void setShowAlarmAfterTerminate(Boolean showAlarmAfterTerminate) {
        this.showAlarmAfterTerminate = showAlarmAfterTerminate;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public Integer getPercentTrafficAlarm() {
        return percentTrafficAlarm;
    }

    public void setPercentTrafficAlarm(Integer percentTrafficAlarm) {
        this.percentTrafficAlarm = percentTrafficAlarm;
    }

    public Integer getLeftDaysAlarm() {
        return leftDaysAlarm;
    }

    public void setLeftDaysAlarm(Integer leftDaysAlarm) {
        this.leftDaysAlarm = leftDaysAlarm;
    }

    public int getAlarmTypeRes() {
        return alarmTypeRes;
    }

    public void setAlarmTypeRes(int alarmTypeRes) {
        this.alarmTypeRes = alarmTypeRes;
    }

    public Integer getPercentTrafficAlarmRes() {
        return percentTrafficAlarmRes;
    }

    public void setPercentTrafficAlarmRes(Integer percentTrafficAlarmRes) {
        this.percentTrafficAlarmRes = percentTrafficAlarmRes;
    }

    public Integer getLeftDaysAlarmRes() {
        return leftDaysAlarmRes;
    }

    public void setLeftDaysAlarmRes(Integer leftDaysAlarmRes) {
        this.leftDaysAlarmRes = leftDaysAlarmRes;
    }

    public Boolean getShowNotification() {
        return showNotification;
    }

    public void setShowNotification(Boolean showNotification) {
        this.showNotification = showNotification;
    }

    public Boolean getShowNotificationWhenDataIsOn() {
        return showNotificationWhenDataIsOn;
    }

    public void setShowNotificationWhenDataIsOn(Boolean showNotificationWhenDataIsOn) {
        this.showNotificationWhenDataIsOn = showNotificationWhenDataIsOn;
    }

    public Boolean getShowNotificationInLockScreen() {
        return showNotificationInLockScreen;
    }

    public void setShowNotificationInLockScreen(Boolean showNotificationInLockScreen) {
        this.showNotificationInLockScreen = showNotificationInLockScreen;
    }

    public Boolean getShowAlarmAfterTerminateRes() {
        return showAlarmAfterTerminateRes;
    }

    public void setShowAlarmAfterTerminateRes(Boolean showAlarmAfterTerminateRes) {
        this.showAlarmAfterTerminateRes = showAlarmAfterTerminateRes;
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

    public Boolean getShowUpDownSpeed() {
        return showUpDownSpeed;
    }

    public void setShowUpDownSpeed(Boolean showUpDownSpeed) {
        this.showUpDownSpeed = showUpDownSpeed;
    }

    public Boolean getVibrateInAlarms() {
        return VibrateInAlarms;
    }

    public void setVibrateInAlarms(Boolean vibrateInAlarms) {
        VibrateInAlarms = vibrateInAlarms;
    }

    public Boolean getSoundInAlarms() {
        return SoundInAlarms;
    }

    public void setSoundInAlarms(Boolean soundInAlarms) {
        SoundInAlarms = soundInAlarms;
    }

    public Boolean getInstalled() {
        return Installed;
    }

    public void setInstalled(Boolean installed) {
        Installed = installed;
    }

    public Boolean getRegistered() {
        return Registered;
    }

    public void setRegistered(Boolean registered) {
        Registered = registered;
    }
}
