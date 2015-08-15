package com.zohaltech.app.mobiledatamonitor.classes;


public class RemainingTimeObject {

    public enum TimeType {
        DAY,
        HOUR
    }

    TimeType timeType;
    int    remained;

    public RemainingTimeObject(TimeType timeType, int remained) {
        setTimeType(timeType);
        setRemained(remained);
    }

    public TimeType getTimeType() {
        return timeType;
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }

    public int getRemained() {
        return remained;
    }

    public void setRemained(int remained) {
        this.remained = remained;
    }

}
