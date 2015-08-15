package com.zohaltech.app.mobiledatamonitor.classes;


public class RemainingTimeObject {

    String timeDesc;
    int    remained;

    public RemainingTimeObject(String timeDesc, int remained) {
        setTimeDesc(timeDesc);
        setRemained(remained);
    }

    public String getTimeDesc() {
        return timeDesc;
    }

    public void setTimeDesc(String timeDesc) {
        this.timeDesc = timeDesc;
    }

    public int getRemained() {
        return remained;
    }

    public void setRemained(int remained) {
        this.remained = remained;
    }

}
