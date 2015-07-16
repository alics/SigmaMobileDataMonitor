package com.zohaltech.app.mobiledatamonitor.entities;


import java.util.Date;

/**
 * Created by Ali on 7/15/2015.
 */
public class DailyTrafficHistory {
    private Integer id;
    private Long traffic;
    private Date usageDate;

    public DailyTrafficHistory( Long traffic , Date usageDate)
    {
        setTraffic(traffic);
        setUsageDate(usageDate);
    }

    public DailyTrafficHistory(Integer id, Long traffic , Date usageDate)
    {
        this(traffic,usageDate);
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

    public Date getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(Date usageDate) {
        this.usageDate = usageDate;
    }

}
