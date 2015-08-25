package com.zohaltech.app.mobiledatamonitor.entities;


public class Setting {

    public enum AlarmType {REMINDED_BYTES, LEFT_DAY, BOTH, NONE}

    private Integer id;
    private Boolean dataConnected;
    private Long    dailyTraffic;
    private Boolean showAlarmAfterTerminate;
    private Boolean showAlarmAfterTerminateRes;
    private int     alarmType;
    private Long    remindedByteAlarm;
    private Integer leftDaysAlarm;
    private int     alarmTypeRes;
    private Long    remindedByteAlarmRes;
    private Integer leftDaysAlarmRes;
    private Boolean showNotification;
    private Boolean showNotificationWhenDataIsOn;
    private Boolean showNotificationInLockScreen;
    private Boolean leftDaysAlarmHasShown;
    private Boolean trafficAlarmHasShown;
    private Boolean secondaryTrafficAlarmHasShown;
    private Boolean showUpDownSpeed;


    public Setting(Boolean dataConnected, Long dailyTraffic, Boolean showAlarmAfterTerminate, int alarmType,
                   Long remindedByteAlarm, Integer leftDaysAlarm, Boolean showAlarmAfterTerminateRes,
                   int alarmTypeRes, Long remindedByteAlarmRes, Integer leftDaysAlarmRes, Boolean showNotification,
                   Boolean showNotificationWhenDataIsOn, Boolean showNotificationInLockScreen, Boolean showUpDownSpeed,
                   Boolean leftDaysAlarmHasShown, Boolean trafficAlarmHasShown, Boolean secondaryTrafficAlarmHasShown) {
        setDataConnected(dataConnected);
        setDailyTraffic(dailyTraffic);
        setShowAlarmAfterTerminate(showAlarmAfterTerminate);
        setShowAlarmAfterTerminateRes(showAlarmAfterTerminateRes);
        setAlarmType(alarmType);
        setRemindedByteAlarm(remindedByteAlarm);
        setLeftDaysAlarm(leftDaysAlarm);
        setAlarmTypeRes(alarmTypeRes);
        setRemindedByteAlarmRes(remindedByteAlarmRes);
        setLeftDaysAlarmRes(leftDaysAlarmRes);
        setShowNotification(showNotification);
        setShowNotificationWhenDataIsOn(showNotificationWhenDataIsOn);
        setShowNotificationInLockScreen(showNotificationInLockScreen);
        setShowUpDownSpeed(showUpDownSpeed);
        setLeftDaysAlarmHasShown(leftDaysAlarmHasShown);
        setTrafficAlarmHasShown(trafficAlarmHasShown);
        setSecondaryTrafficAlarmHasShown(secondaryTrafficAlarmHasShown);
    }

    public Setting(Integer id, Boolean dataConnected, Long dailyTraffic, Boolean showAlarmAfterTerminate,
                   int alarmType, Long remindedByteAlarm, Integer leftDaysAlarm, Boolean showAlarmAfterTerminateRes,
                   int alarmTypeRes, Long remindedByteAlarmRes, Integer leftDaysAlarmRes, Boolean showNotification,
                   Boolean showNotificationWhenDataIsOn, Boolean showNotificationInLockScreen, Boolean showUpDownSpeed,
                   Boolean leftDaysAlarmHasShown, Boolean trafficAlarmHasShown, Boolean secondaryTrafficAlarmHasShown) {
        this(dataConnected, dailyTraffic, showAlarmAfterTerminate, alarmType, remindedByteAlarm,
             leftDaysAlarm, showAlarmAfterTerminateRes, alarmTypeRes, remindedByteAlarmRes,
             leftDaysAlarmRes, showNotification, showNotificationWhenDataIsOn, showNotificationInLockScreen,
             showUpDownSpeed, leftDaysAlarmHasShown, trafficAlarmHasShown, secondaryTrafficAlarmHasShown);
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

    public Long getRemindedByteAlarm() {
        return remindedByteAlarm;
    }

    public void setRemindedByteAlarm(Long remindedByteAlarm) {
        this.remindedByteAlarm = remindedByteAlarm;
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

    public Long getRemindedByteAlarmRes() {
        return remindedByteAlarmRes;
    }

    public void setRemindedByteAlarmRes(Long remindedByteAlarmRes) {
        this.remindedByteAlarmRes = remindedByteAlarmRes;
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
}
