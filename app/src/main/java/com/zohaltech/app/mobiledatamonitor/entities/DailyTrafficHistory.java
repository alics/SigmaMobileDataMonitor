package com.zohaltech.app.mobiledatamonitor.entities;


import java.sql.Time;
import java.util.Date;

public class DailyTrafficHistory {
    private Integer id;
    private Long traffic;
    private Date beginningDateTime;
    private Date endingDateTime;

    public DailyTrafficHistory(Long traffic, Date beginningDateTime, Date endingDateTime) {
        setTraffic(traffic);
        setBeginningDateTime(beginningDateTime);
        setEndingDateTime(endingDateTime);
    }

    public DailyTrafficHistory(Integer id, Long traffic, Date beginningDateTime, Date endingDateTime) {
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


    public Date getBeginningDateTime() {
        return beginningDateTime;
    }

    public void setBeginningDateTime(Date beginningDateTime) {
        this.beginningDateTime = beginningDateTime;
    }

    public Date getEndingDateTime() {
        return endingDateTime;
    }

    public void setEndingDateTime(Date endingDateTime) {
        this.endingDateTime = endingDateTime;
    }
}
