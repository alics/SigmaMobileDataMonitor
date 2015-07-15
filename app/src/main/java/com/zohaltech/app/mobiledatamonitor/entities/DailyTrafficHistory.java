package com.zohaltech.app.mobiledatamonitor.entities;


import java.util.Date;

/**
 * Created by Ali on 7/15/2015.
 */
public class DailyTrafficHistory {
    private int id;
    private long traffic;
    private Date usageDate;


    public DailyTrafficHistory( long traffic , Date usageDate)
    {
        setTraffic(traffic);
        setUsageDate(usageDate);
    }

    public DailyTrafficHistory(int id, long traffic , Date usageDate)
    {
        this(traffic,usageDate);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTraffic() {
        return traffic;
    }

    public void setTraffic(long traffic) {
        this.traffic = traffic;
    }

    public Date getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(Date usageDate) {
        this.usageDate = usageDate;
    }
}
