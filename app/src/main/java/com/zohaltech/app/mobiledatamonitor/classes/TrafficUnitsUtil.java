package com.zohaltech.app.mobiledatamonitor.classes;

public class TrafficUnitsUtil {

    private String value;
    private String postfix;

    public TrafficUnitsUtil() {
    }

    public TrafficUnitsUtil(String value, String postfix) {
        this.value = value;
        this.postfix = postfix;
    }

    public static String getTransferRate(long bytes) {
        String result = "0 B/s";
        if (bytes < 1024) {
            result = bytes + " B/s";
        } else if (bytes >= 1024 && bytes < power(1024, 2)) {
            result = getTrafficWithPoint((float) bytes / 1024) + " KB/s";
        } else if (bytes >= (1024 * 1024)) {
            result = getTrafficWithPoint((float) bytes / power(1024, 2)) + " MB/s";
        }
        return result;
    }

    public static String getUsedTrafficWithPoint(long bytes) {
        String result = "0 MB";
        if (bytes < power(1024, 3)) {
            result = getTrafficWithPoint((float) bytes / power(1024, 2)) + " MB";
        } else if (bytes >= (1024 * 1024 * 1024)) {
            result = getTrafficWithPoint((float) bytes / power(1024, 3)) + " GB";
        }
        return result;
    }

    public static String getUsedTrafficWithoutPoint(long bytes) {
        String result = "0MB";
        if (bytes < power(1024, 3)) {
            result = getTrafficWithoutPoint((float) bytes / power(1024, 2)) + "MB";
        } else if (bytes >= (1024 * 1024 * 1024)) {
            result = getTrafficWithoutPoint((float) bytes / power(1024, 3)) + "GB";
        }
        return result;
    }

    public static String getArcTraffic(long usedBytes, long totalBytes) {
        String usedTraffic = getUsedTrafficWithoutPoint(usedBytes);
        String totalTraffic = getUsedTrafficWithoutPoint(totalBytes);
        return usedTraffic + "/" + totalTraffic;
    }

    public static TrafficUnitsUtil getTodayTraffic(long bytes) {
        TrafficUnitsUtil result = new TrafficUnitsUtil();
        if (bytes < (1024 * 1024)) {
            result.setValue(getTrafficWithPoint((float) bytes / 1024));
            result.setPostfix("KB");
        } else if (bytes >= (1024 * 1024) && bytes < power(1024, 3)) {
            result.setValue(getTrafficWithPoint((float) bytes / power(1024, 2)));
            result.setPostfix("MB");
        } else if (bytes >= (1024 * 1024 * 1024)) {
            result.setValue(getTrafficWithPoint((float) bytes / power(1024, 3)));
            result.setPostfix("GB");
        }
        return result;
    }

    public static long MbToByte(int mb) {
        return mb * power(1024, 2);
    }

    public static long ByteToMb(long bytes) {
        return bytes / power(1024, 2);
    }

    private static String getTrafficWithoutPoint(float value) {
        return Helper.round(value, 0).toString();
    }

    private static String getTrafficWithPoint(float value) {
        String result = Helper.round(value, 1).toString();
        if (result.endsWith(".0")) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    public static long power(long x, int y) {
        return Math.round(Math.pow(x, y));
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
