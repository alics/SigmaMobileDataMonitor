package com.zohaltech.app.mobiledatamonitor.classes;

public class TrafficDisplay {

    private String value;
    private String postfix;

    public TrafficDisplay() {
    }

    public TrafficDisplay(String value, String postfix) {
        this.value = value;
        this.postfix = postfix;
    }

    public static String getTransferRate(long bytes) {
        String result = "0 B/s";
        if (bytes < 1024) {
            result = bytes + " B/s";
        } else if (bytes >= 1024 && bytes < (1024 * 1024)) {
            result = getCorrectTrafficText((float) bytes / 1024) + " KB/s";
        } else if (bytes >= (1024 * 1024)) {
            result = getCorrectTrafficText((float) bytes / (1024 * 1024)) + " MB/s";
        }
        return result;
    }

    public static String getUsedTraffic(long bytes) {
        String result = "0 MB";
        if (bytes < (1024 * 1024 * 1024)) {
            result = getCorrectTrafficText((float) bytes / (1024 * 1024)) + " MB";
        } else if (bytes >= (1024 * 1024 * 1024)) {
            result = getCorrectTrafficText((float) bytes / (1024 * 1024 * 1024)) + " GB";
        }
        return result;
    }

    public static String getArcTraffic(long usedBytes, long totalBytes) {
        return getCorrectTrafficText((float) usedBytes / (1024 * 1024)) + "/" + getCorrectTrafficText((float) totalBytes / (1024 * 1024)) + " MB";
    }

    public static TrafficDisplay getTodayTraffic(long bytes) {
        TrafficDisplay result = new TrafficDisplay();
        if (bytes < (1024 * 1024)) {
            result.setValue(getCorrectTrafficText((float) bytes / 1024));
            result.setPostfix("KB");
        } else if (bytes >= (1024 * 1024) && bytes < (1024 * 1024 * 1024)) {
            result.setValue(getCorrectTrafficText((float) bytes / (1024 * 1024)));
            result.setPostfix("MB");
        } else if (bytes >= (1024 * 1024 * 1024)) {
            result.setValue(getCorrectTrafficText((float) bytes / (1024 * 1024 * 1024)));
            result.setPostfix("GB");
        }
        return result;
    }

    private static String getCorrectTrafficText(float value) {
        String result = Helper.round(value, 1).toString();
        if (result.endsWith(".0")) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public String getInlineDisplay() {
        return getValue() + " " + getPostfix();
    }
}
