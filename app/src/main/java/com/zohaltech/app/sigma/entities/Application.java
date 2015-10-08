package com.zohaltech.app.sigma.entities;


public class Application {
    private Integer id;
    private int uid;
    private String appName;
    private String packageName;

    public Application(Integer id, int uid, String appName, String packageName) {
        this(uid, appName, packageName);
        this.id = id;
    }

    public Application(int uid, String appName, String packageName) {
        setUid(uid);
        setAppName(appName);
        setPackageName(packageName);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
