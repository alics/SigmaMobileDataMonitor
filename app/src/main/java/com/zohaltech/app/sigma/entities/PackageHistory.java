package com.zohaltech.app.sigma.entities;

public class PackageHistory {

    Integer id;
    Integer dataPackageId;
    String startDateTime;
    String endDateTime;
    String primaryPackageEndDateTime;
    String secondaryTrafficEndDateTime;
    String simSerial;
    Integer status;

    public enum StatusEnum {
        ACTIVE,
        RESERVED,
        CANCELED,
        TRAFFIC_FINISHED,
        PERIOD_FINISHED
    }

    public PackageHistory(Integer dataPackageId, String startDateTime, String endDateTime, String primaryPackageEndDateTime, String secondaryTrafficEndDateTime, String simSerial, Integer status) {
        setDataPackageId(dataPackageId);
        setStartDateTime(startDateTime);
        setEndDateTime(endDateTime);
        setSimSerial(simSerial);
        setPrimaryPackageEndDateTime(primaryPackageEndDateTime);
        setSecondaryTrafficEndDateTime(secondaryTrafficEndDateTime);
        setStatus(status);

    }

    public PackageHistory(Integer id, Integer dataPackageId, String startDateTime, String endDateTime, String primaryPackageEndDateTime, String secondaryTrafficEndDateTime, String simSerial, Integer status) {
        this(dataPackageId, startDateTime, endDateTime, primaryPackageEndDateTime, secondaryTrafficEndDateTime, simSerial, status);
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

    public String getSecondaryTrafficEndDateTime() {
        return secondaryTrafficEndDateTime;
    }

    public void setSecondaryTrafficEndDateTime(String secondaryTrafficEndDateTime) {
        this.secondaryTrafficEndDateTime = secondaryTrafficEndDateTime;
    }

    public String getPrimaryPackageEndDateTime() {
        return primaryPackageEndDateTime;
    }

    public void setPrimaryPackageEndDateTime(String primaryPackageEndDateTime) {
        this.primaryPackageEndDateTime = primaryPackageEndDateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
