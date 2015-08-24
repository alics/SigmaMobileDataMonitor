package com.zohaltech.app.mobiledatamonitor.entities;


public class Setting {

    public enum AlarmType {REMINDED_BYTES, LEFT_DAY, BOTH, NONE}

    private Integer id;
    private Boolean dataConnected;
    private Long    dailyTraffic;
    private Boolean dcDataAfterTerminate;
    private Boolean dcDataAfterTerminateRes;
    private int     alarmType;
    private Long    remindedByteAlarm;
    private Integer leftDaysAlarm;
    private int     alarmTypeRes;
    private Long    remindedByteAlarmRes;
    private Integer leftDaysAlarmRes;
    private Boolean showNotification;
    private Boolean showNotificationWhenDataIsOn;
    private Boolean showNotificationInLockScreen;


    public Setting(Boolean dataConnected, Long dailyTraffic, Boolean dcDataAfterTerminate,
                   int alarmType, Long remindedByteAlarm, Integer leftDaysAlarm,Boolean dcDataAfterTerminateRes,
                   int alarmTypeRes, Long remindedByteAlarmRes, Integer leftDaysAlarmRes,
                   Boolean showNotification, Boolean showNotificationWhenDataIsOn, Boolean showNotificationInLockScreen) {
        setDataConnected(dataConnected);
        setDailyTraffic(dailyTraffic);
        setDcDataAfterTerminate(dcDataAfterTerminate);
        setDcDataAfterTerminateRes(dcDataAfterTerminateRes);
        setAlarmType(alarmType);
        setRemindedByteAlarm(remindedByteAlarm);
        setLeftDaysAlarm(leftDaysAlarm);
        setAlarmTypeRes(alarmTypeRes);
        setRemindedByteAlarmRes(remindedByteAlarmRes);
        setLeftDaysAlarmRes(leftDaysAlarmRes);
        setShowNotification(showNotification);
        setShowNotificationWhenDataIsOn(showNotificationWhenDataIsOn);
        setShowNotificationInLockScreen(showNotificationInLockScreen);
    }

    public Setting(Integer id, Boolean dataConnected, Long dailyTraffic, Boolean dcDataAfterTerminate,
                   int alarmType, Long remindedByteAlarm, Integer leftDaysAlarm,Boolean dcDataAfterTerminateRes,
                   int alarmTypeRes, Long remindedByteAlarmRes, Integer leftDaysAlarmRes,
                   Boolean showNotification, Boolean showNotificationWhenDataIsOn, Boolean showNotificationInLockScreen) {
        this(dataConnected, dailyTraffic, dcDataAfterTerminate, alarmType, remindedByteAlarm, leftDaysAlarm,dcDataAfterTerminateRes,
             alarmTypeRes, remindedByteAlarmRes, leftDaysAlarmRes, showNotification, showNotificationWhenDataIsOn, showNotificationInLockScreen);
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

    public Boolean getDcDataAfterTerminate() {
        return dcDataAfterTerminate;
    }

    public void setDcDataAfterTerminate(Boolean dcDataAfterTerminate) {
        this.dcDataAfterTerminate = dcDataAfterTerminate;
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

    public Boolean getDcDataAfterTerminateRes() {
        return dcDataAfterTerminateRes;
    }

    public void setDcDataAfterTerminateRes(Boolean dcDataAfterTerminateRes) {
        this.dcDataAfterTerminateRes = dcDataAfterTerminateRes;
    }
}
