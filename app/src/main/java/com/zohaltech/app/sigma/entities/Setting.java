package com.zohaltech.app.sigma.entities;


public class Setting
{

    private Integer id;
    private Boolean dataConnected;
    private Long dailyTraffic;
    private int alarmType;
    private Integer percentTrafficAlarm;
    private Integer leftDaysAlarm;
    private int alarmTypeRes;
    private Integer percentTrafficAlarmRes;
    private Integer leftDaysAlarmRes;
    private Boolean showNotification;
    private Boolean showNotificationWhenDataOrWifiIsOn;
    private Boolean showWifiUsage;
    private Boolean showNotificationInLockScreen;
    private Boolean showUpDownSpeed;
    private Boolean vibrateInAlarms;
    private Boolean soundInAlarms;
    private Boolean dailyTrafficLimitationAlarm;
    private Long dailyTrafficLimitation;

    public Setting(Boolean dataConnected, Long dailyTraffic, int alarmType, Integer percentTrafficAlarm, Integer leftDaysAlarm,
                   int alarmTypeRes, Integer percentTrafficAlarmRes, Integer leftDaysAlarmRes, Boolean showNotification,
                   Boolean showNotificationWhenDataIsOn,Boolean showWifiUsage, Boolean showNotificationInLockScreen, Boolean showUpDownSpeed,
                   Boolean vibrateInAlarms, Boolean soundInAlarms,Boolean dailyTrafficLimitationAlarm,Long dailyTrafficLimitation)
    {
        setDataConnected(dataConnected);
        setDailyTraffic(dailyTraffic);
        setAlarmType(alarmType);
        setPercentTrafficAlarm(percentTrafficAlarm);
        setLeftDaysAlarm(leftDaysAlarm);
        setAlarmTypeRes(alarmTypeRes);
        setPercentTrafficAlarmRes(percentTrafficAlarmRes);
        setLeftDaysAlarmRes(leftDaysAlarmRes);
        setShowNotification(showNotification);
        setShowNotificationWhenDataOrWifiIsOn(showNotificationWhenDataIsOn);
        setShowWifiUsage(showWifiUsage);
        setShowNotificationInLockScreen(showNotificationInLockScreen);
        setShowUpDownSpeed(showUpDownSpeed);
        setVibrateInAlarms(vibrateInAlarms);
        setSoundInAlarms(soundInAlarms);
        setDailyTrafficLimitationAlarm(dailyTrafficLimitationAlarm);
        setDailyTrafficLimitation(dailyTrafficLimitation);
    }

    public Setting(Integer id, Boolean dataConnected, Long dailyTraffic, int alarmType, Integer percentTrafficAlarm,
                   Integer leftDaysAlarm, int alarmTypeRes, Integer percentTrafficAlarmRes, Integer leftDaysAlarmRes,
                   Boolean showNotification, Boolean showNotificationWhenDataIsOn,Boolean showWifiUsage, Boolean showNotificationInLockScreen,
                   Boolean showUpDownSpeed, Boolean vibrateInAlarms, Boolean soundInAlarms,Boolean dailyTrafficLimitationAlarm,Long dailyTrafficLimitation)
    {
        this(dataConnected, dailyTraffic, alarmType, percentTrafficAlarm,
                leftDaysAlarm, alarmTypeRes, percentTrafficAlarmRes, leftDaysAlarmRes,
                showNotification, showNotificationWhenDataIsOn,showWifiUsage, showNotificationInLockScreen, showUpDownSpeed,
                vibrateInAlarms, soundInAlarms,dailyTrafficLimitationAlarm,dailyTrafficLimitation);
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Boolean getDataConnected()
    {
        return dataConnected;
    }

    public void setDataConnected(Boolean dataConnected)
    {
        this.dataConnected = dataConnected;
    }

    public Long getDailyTraffic()
    {
        return dailyTraffic;
    }

    public void setDailyTraffic(Long dailyTraffic)
    {
        this.dailyTraffic = dailyTraffic;
    }

    public int getAlarmType()
    {
        return alarmType;
    }

    public void setAlarmType(int alarmType)
    {
        this.alarmType = alarmType;
    }

    public Integer getPercentTrafficAlarm()
    {
        return percentTrafficAlarm;
    }

    public void setPercentTrafficAlarm(Integer percentTrafficAlarm)
    {
        this.percentTrafficAlarm = percentTrafficAlarm;
    }

    public Integer getLeftDaysAlarm()
    {
        return leftDaysAlarm;
    }

    public void setLeftDaysAlarm(Integer leftDaysAlarm)
    {
        this.leftDaysAlarm = leftDaysAlarm;
    }

    public int getAlarmTypeRes()
    {
        return alarmTypeRes;
    }

    public void setAlarmTypeRes(int alarmTypeRes)
    {
        this.alarmTypeRes = alarmTypeRes;
    }

    public Integer getPercentTrafficAlarmRes()
    {
        return percentTrafficAlarmRes;
    }

    public void setPercentTrafficAlarmRes(Integer percentTrafficAlarmRes)
    {
        this.percentTrafficAlarmRes = percentTrafficAlarmRes;
    }

    public Integer getLeftDaysAlarmRes()
    {
        return leftDaysAlarmRes;
    }

    public void setLeftDaysAlarmRes(Integer leftDaysAlarmRes)
    {
        this.leftDaysAlarmRes = leftDaysAlarmRes;
    }

    public Boolean getShowNotification()
    {
        return showNotification;
    }

    public void setShowNotification(Boolean showNotification)
    {
        this.showNotification = showNotification;
    }

    public Boolean getShowNotificationWhenDataOrWifiIsOn()
    {
        return showNotificationWhenDataOrWifiIsOn;
    }

    public void setShowNotificationWhenDataOrWifiIsOn(Boolean showNotificationWhenDataOrWifiIsOn)
    {
        this.showNotificationWhenDataOrWifiIsOn = showNotificationWhenDataOrWifiIsOn;
    }

    public Boolean getShowNotificationInLockScreen()
    {
        return showNotificationInLockScreen;
    }

    public void setShowNotificationInLockScreen(Boolean showNotificationInLockScreen)
    {
        this.showNotificationInLockScreen = showNotificationInLockScreen;
    }

    public Boolean getShowUpDownSpeed()
    {
        return showUpDownSpeed;
    }

    public void setShowUpDownSpeed(Boolean showUpDownSpeed)
    {
        this.showUpDownSpeed = showUpDownSpeed;
    }

    public Boolean getVibrateInAlarms()
    {
        return vibrateInAlarms;
    }

    public void setVibrateInAlarms(Boolean vibrateInAlarms)
    {
        this.vibrateInAlarms = vibrateInAlarms;
    }

    public Boolean getSoundInAlarms()
    {
        return soundInAlarms;
    }

    public void setSoundInAlarms(Boolean soundInAlarms)
    {
        this.soundInAlarms = soundInAlarms;
    }

    public Boolean getShowWifiUsage()
    {
        return showWifiUsage;
    }

    public void setShowWifiUsage(Boolean showWifiUsage)
    {
        this.showWifiUsage = showWifiUsage;
    }

    public Boolean getDailyTrafficLimitationAlarm() {
        return dailyTrafficLimitationAlarm;
    }

    public void setDailyTrafficLimitationAlarm(Boolean dailyTrafficLimitationAlarm) {
        this.dailyTrafficLimitationAlarm = dailyTrafficLimitationAlarm;
    }

    public Long getDailyTrafficLimitation() {
        return dailyTrafficLimitation;
    }

    public void setDailyTrafficLimitation(Long dailyTrafficLimitation) {
        this.dailyTrafficLimitation = dailyTrafficLimitation;
    }

    public enum AlarmType
    {
        REMINDED_BYTES, LEFT_DAY, BOTH, NONE
    }
}
