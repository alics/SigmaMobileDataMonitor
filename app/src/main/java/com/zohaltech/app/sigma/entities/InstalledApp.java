package com.zohaltech.app.sigma.entities;


public class InstalledApp {
    private int id;
    private String appName;
    private String packageName;

    public InstalledApp (String appName,String packageName){
        setAppName(appName);
        setPackageName(packageName);
    }

    public InstalledApp (int id,String appName,String packageName){
        this(appName,packageName);
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
