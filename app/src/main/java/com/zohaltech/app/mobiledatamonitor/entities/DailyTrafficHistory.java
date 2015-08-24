package com.zohaltech.app.mobiledatamonitor.entities;


public class DailyTrafficHistory {

    private Integer id;
    private Long traffic;
    private String logDate;
    private String startLogTime;
    private String endLogTime;



    public DailyTrafficHistory(Long traffic,String logDate, String startLogTime,String endLogTime) {
        setTraffic(traffic);
        setLogDate(logDate);
        setStartLogTime(startLogTime);
        setEndLogTime(endLogTime);
    }

    public DailyTrafficHistory(Integer id, Long traffic,String logDate, String startLogTime,String endLogTime) {
        this(traffic,logDate,startLogTime,endLogTime);
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

    public String getStartLogTime() {
        return startLogTime;
    }

    public void setStartLogTime(String startLogTime) {
        this.startLogTime = startLogTime;
    }

    public String getEndLogTime() {
        return endLogTime;
    }

    public void setEndLogTime(String endLogTime) {
        this.endLogTime = endLogTime;
    }


}
