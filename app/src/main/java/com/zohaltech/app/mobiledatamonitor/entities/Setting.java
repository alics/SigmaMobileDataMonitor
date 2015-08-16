package com.zohaltech.app.mobiledatamonitor.entities;


public class Setting {

    public enum AlarmType {REMINDED_BYTES, LEFT_DAY, BOTH}

    private Integer   id;
    private Boolean   monitoringServiceOn;
    private Long      dailyTraffic;
    private Boolean   dcDataAfterTerminate;
    private int alarmType;
    private Long      remindedByteAlarm;
    private Integer   leftDaysAlarm;
    private int alarmTypeRes;
    private Long      remindedByteAlarmRes;
    private Integer   leftDaysAlarmRes;
    private Boolean   showNotification;
    private Boolean   showNotificationWhenDataIsOn;
    private Boolean   showNotificationInLockScreen;



    public Setting(Boolean monitoringServiceOn, Long dailyTraffic, Boolean dcDataAfterTerminate,
                   int alarmType, Long remindedByteAlarm, Integer leftDaysAlarm,
                   int alarmTypeRes, Long remindedByteAlarmRes, Integer leftDaysAlarmRes,
                   Boolean showNotification, Boolean showNotificationWhenDataIsOn, Boolean showNotificationInLockScreen) {
        setMonitoringServiceOn(monitoringServiceOn);
        setDailyTraffic(dailyTraffic);
        setDcDataAfterTerminate(dcDataAfterTerminate);
        setAlarmType(alarmType);
        setRemindedByteAlarm(remindedByteAlarm);
        setLeftDaysAlarm(leftDaysAlarm);
        setAlarmTypeRes(alarmTypeRes);
        setRemindedByteAlarmRes(getRemindedByteAlarmRes());
        setLeftDaysAlarmRes(leftDaysAlarmRes);
        setShowNotification(showNotification);
        setShowNotificationWhenDataIsOn(showNotificationWhenDataIsOn);
        setShowNotificationInLockScreen(showNotificationInLockScreen);
    }

    public Setting(Integer id, Boolean monitoringServiceOn, Long dailyTraffic, Boolean dcDataAfterTerminate,
                   int alarmType, Long remindedByteAlarm, Integer leftDaysAlarm,
                   int alarmTypeRes, Long remindedByteAlarmRes, Integer leftDaysAlarmRes,
                   Boolean showNotification, Boolean showNotificationWhenDataIsOn, Boolean showNotificationInLockScreen) {
        this(monitoringServiceOn, dailyTraffic, dcDataAfterTerminate, alarmType, remindedByteAlarm, leftDaysAlarm,
             alarmTypeRes, remindedByteAlarmRes, leftDaysAlarmRes, showNotification, showNotificationWhenDataIsOn, showNotificationInLockScreen);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getMonitoringServiceOn() {
        return monitoringServiceOn;
    }

    public void setMonitoringServiceOn(Boolean monitoringServiceOn) {
        this.monitoringServiceOn = monitoringServiceOn;
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
}
