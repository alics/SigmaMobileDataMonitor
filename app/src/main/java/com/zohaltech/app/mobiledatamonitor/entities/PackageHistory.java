package com.zohaltech.app.mobiledatamonitor.entities;

import java.sql.Date;

/**
 * Created by Ali on 7/15/2015.
 */
public class PackageHistory {
    int id;
    int dataPackageId;
    Date startDateTime;
    Date endDateTime;
    String simId;
    boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDataPackageId() {
        return dataPackageId;
    }

    public void setDataPackageId(int dataPackageId) {
        this.dataPackageId = dataPackageId;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getSimId() {
        return simId;
    }

    public void setSimId(String simId) {
        this.simId = simId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
