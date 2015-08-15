package com.zohaltech.app.mobiledatamonitor.classes;


public class AlarmObject {

    public enum AlarmType {
        REMINDED_DAYS_ALARM,
        REMINDED_TRAFFIC_ALARM,
        FINISH_VALIDATION_DATE_ALARM,
        FINISH_TRAFFIC_ALARM,
        FINISH_SECONDARY_TRAFFIC_ALARM
    }

    AlarmType alarmType;
    String    alarmMessage;

    public AlarmObject(AlarmType alarmType, String alarmMessage) {
        setAlarmType(alarmType);
        setAlarmMessage(alarmMessage);
    }

    public AlarmType getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(AlarmType alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmMessage() {
        return alarmMessage;
    }

    public void setAlarmMessage(String alarmMessage) {
        this.alarmMessage = alarmMessage;
    }
}
