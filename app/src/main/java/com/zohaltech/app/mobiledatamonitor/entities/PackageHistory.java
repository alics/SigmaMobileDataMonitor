package com.zohaltech.app.mobiledatamonitor.entities;

import java.util.Date;

public class PackageHistory {
    Integer id;
    Integer dataPackageId;
    Date    startDateTime;
    Date    endDateTime;
    String  simId;
    Boolean active;


    public PackageHistory(Integer dataPackageId, Date startDateTime, Date endDateTime, String simId, Boolean active) {
        setDataPackageId(dataPackageId);
        setStartDateTime(startDateTime);
        setEndDateTime(endDateTime);
        setSimId(simId);
        setActive(active);
    }

    public PackageHistory(Integer id, Integer dataPackageId, Date startDateTime, Date endDateTime, String simId, Boolean active) {
        this(dataPackageId, startDateTime, endDateTime, simId, active);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDataPackageId() {
        return dataPackageId;
    }

    public void setDataPackageId(Integer dataPackageId) {
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
