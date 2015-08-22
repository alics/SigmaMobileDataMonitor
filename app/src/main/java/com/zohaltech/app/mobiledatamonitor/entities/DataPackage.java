package com.zohaltech.app.mobiledatamonitor.entities;

public class DataPackage {

    Integer id;
    Integer operatorId;
    String  title;
    Integer period;
    Integer price;
    Long    primaryTraffic;
    Long    secondaryTraffic;
    String  secondaryTrafficStartTime;
    String  secondaryTrafficEndTime;
    String  ussdCode;
    Boolean custom;

    public DataPackage(Integer operatorId, String title, Integer period, Integer price, Long primaryTraffic, Long secondaryTraffic,
                       String secondaryTrafficEndTime, String secondaryTrafficStartTime, String ussdCode, Boolean custom) {
        setOperatorId(operatorId);
        setTitle(title);
        setPeriod(period);
        setPrice(price);
        setPrimaryTraffic(primaryTraffic);
        setSecondaryTraffic(secondaryTraffic);
        setSecondaryTrafficStartTime(secondaryTrafficStartTime);
        setSecondaryTrafficEndTime(secondaryTrafficEndTime);
        setUssdCode(ussdCode);
        setCustom(custom);
    }

    public DataPackage(Integer id, Integer operatorId, String title, Integer period, Integer price, Long primaryTraffic, Long secondaryTraffic,
                       String secondaryTrafficEndTime, String secondaryTrafficStartTime, String ussdCode, Boolean custom) {
        this(operatorId, title, period, price, primaryTraffic, secondaryTraffic, secondaryTrafficStartTime, secondaryTrafficEndTime, ussdCode, custom);
        this.id = id;
    }

    public DataPackage() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getPrimaryTraffic() {
        return primaryTraffic;
    }

    public void setPrimaryTraffic(Long primaryTraffic) {
        this.primaryTraffic = primaryTraffic;
    }

    public Long getSecondaryTraffic() {
        return secondaryTraffic;
    }

    public void setSecondaryTraffic(Long secondaryTraffic) {
        this.secondaryTraffic = secondaryTraffic;
    }

    public String getSecondaryTrafficStartTime() {
        return secondaryTrafficStartTime;
    }

    public void setSecondaryTrafficStartTime(String secondaryTrafficStartTime) {
        this.secondaryTrafficStartTime = secondaryTrafficStartTime;
    }

    public String getSecondaryTrafficEndTime() {
        return secondaryTrafficEndTime;
    }

    public void setSecondaryTrafficEndTime(String secondaryTrafficEndTime) {
        this.secondaryTrafficEndTime = secondaryTrafficEndTime;
    }

    public String getUssdCode() {
        return ussdCode;
    }

    public void setUssdCode(String ussdCode) {
        this.ussdCode = ussdCode;
    }

    public Boolean getCustom() {
        return custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }

    public String getDescription() {
        return String.format("%s %d تومان", getTitle(), getPrice());
    }
}
