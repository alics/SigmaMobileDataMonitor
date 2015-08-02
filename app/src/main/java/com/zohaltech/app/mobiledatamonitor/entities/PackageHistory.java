package com.zohaltech.app.mobiledatamonitor.entities;

public class PackageHistory {

    Integer id;
    Integer dataPackageId;
    String  startDateTime;
    String  endDateTime;
    String  secondaryTrafficEndDateTime;
    String  simSerial;
    Boolean active;
    Boolean reserved;


    public PackageHistory(Integer dataPackageId, String startDateTime, String endDateTime, String secondaryTrafficEndDateTime, String simSerial, Boolean active, Boolean reserved) {
        setDataPackageId(dataPackageId);
        setStartDateTime(startDateTime);
        setEndDateTime(endDateTime);
        setSimSerial(simSerial);
        setActive(active);
        setReserved(reserved);
        setSecondaryTrafficEndDateTime(secondaryTrafficEndDateTime);
    }

    public PackageHistory(Integer id, Integer dataPackageId, String startDateTime, String endDateTime, String secondaryTrafficEndDateTime, String simSerial, Boolean active, Boolean reserved) {
        this(dataPackageId, startDateTime, endDateTime, secondaryTrafficEndDateTime, simSerial, active, reserved);
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

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getSimSerial() {
        return simSerial;
    }

    public void setSimSerial(String simSerial) {
        this.simSerial = simSerial;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public String getSecondaryTrafficEndDateTime() {
        return secondaryTrafficEndDateTime;
    }

    public void setSecondaryTrafficEndDateTime(String secondaryTrafficEndDateTime) {
        this.secondaryTrafficEndDateTime = secondaryTrafficEndDateTime;
    }
}
