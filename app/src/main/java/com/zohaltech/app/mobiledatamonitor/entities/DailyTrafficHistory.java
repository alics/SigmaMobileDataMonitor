package com.zohaltech.app.mobiledatamonitor.entities;


import java.sql.Time;
import java.util.Date;

public class DailyTrafficHistory {
    private Integer id;
    private Long traffic;
    private String beginningDateTime;
    private String endingDateTime;

    public DailyTrafficHistory(Long traffic, String beginningDateTime, String endingDateTime) {
        setTraffic(traffic);
        setBeginningDateTime(beginningDateTime);
        setEndingDateTime(endingDateTime);
    }

    public DailyTrafficHistory(Integer id, Long traffic, String beginningDateTime, String endingDateTime) {
        this(traffic, beginningDateTime, endingDateTime);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTraffic() {
        return traffic;
    }

    public void setTraffic(Long traffic) {
        this.traffic = traffic;
    }


    public String getBeginningDateTime() {
        return beginningDateTime;
    }

    public void setBeginningDateTime(String beginningDateTime) {
        this.beginningDateTime = beginningDateTime;
    }

    public String getEndingDateTime() {
        return endingDateTime;
    }

    public void setEndingDateTime(String endingDateTime) {
        this.endingDateTime = endingDateTime;
    }
}
