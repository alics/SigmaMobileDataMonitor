package com.zohaltech.app.mobiledatamonitor.entities;

import java.sql.Time;

/**
 * Created by Ali on 7/15/2015.
 */
public class DataPackage {
    int id;
    int operatorId;
    String title;
    int period;
    int price;
    long primaryTraffic;
    long secondaryTraffic;
    Time secondaryTrafficStartTime;
    Time secondaryTrafficEndTime;
    String ussdCode;
    boolean custom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public long getPrimaryTraffic() {
        return primaryTraffic;
    }

    public void setPrimaryTraffic(long primaryTraffic) {
        this.primaryTraffic = primaryTraffic;
    }

    public long getSecondaryTraffic() {
        return secondaryTraffic;
    }

    public void setSecondaryTraffic(long secondaryTraffic) {
        this.secondaryTraffic = secondaryTraffic;
    }

    public Time getSecondaryTrafficStartTime() {
        return secondaryTrafficStartTime;
    }

    public void setSecondaryTrafficStartTime(Time secondaryTrafficStartTime) {
        this.secondaryTrafficStartTime = secondaryTrafficStartTime;
    }

    public Time getSecondaryTrafficEndTime() {
        return secondaryTrafficEndTime;
    }

    public void setSecondaryTrafficEndTime(Time secondaryTrafficEndTime) {
        this.secondaryTrafficEndTime = secondaryTrafficEndTime;
    }

    public String getUssdCode() {
        return ussdCode;
    }

    public void setUssdCode(String ussdCode) {
        this.ussdCode = ussdCode;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }
}
