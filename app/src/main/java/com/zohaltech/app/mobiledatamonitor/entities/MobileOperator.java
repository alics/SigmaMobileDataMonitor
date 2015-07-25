package com.zohaltech.app.mobiledatamonitor.entities;

public class MobileOperator {

    Integer id;
    String name;

    public MobileOperator(String name) {
        this.name = name;
    }

    public MobileOperator(Integer id, String name) {
        this(name);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
