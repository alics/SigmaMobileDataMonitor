package com.zohaltech.app.mobiledatamonitor.entities;


public class DailyTrafficHistory {

    private Integer id;
    private Long traffic;



    private String logDate;

    public DailyTrafficHistory(Long traffic, String logDate) {
        setTraffic(traffic);
        setLogDate(logDate);
    }

    public DailyTrafficHistory(Integer id, Long traffic, String logDate) {
        this(traffic,logDate);
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

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }
}
