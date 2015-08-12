package com.zohaltech.app.mobiledatamonitor.classes;


import com.zohaltech.app.mobiledatamonitor.dal.DataPackages;
import com.zohaltech.app.mobiledatamonitor.dal.PackageHistories;
import com.zohaltech.app.mobiledatamonitor.dal.UsageLogs;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;

import java.util.Date;

public final class PackageStatus {
    Boolean hasActivePackage;
    long    dailyTraffic;
    long    primaryTraffic;
    long    usedPrimaryTraffic;
    long    secondaryTraffic;
    long    usedSecondaryTraffic;
    String  secondaryCaption;
    int     period;
    int     leftDays;
    String  currentAlarms;


    public static PackageStatus getCurrentStatus() {
        PackageStatus status = new PackageStatus();
        PackageHistory history = PackageHistories.getActivePackage();

        status.dailyTraffic = SettingsHandler.getDailyTraffic();

        if (history == null) {
            status.hasActivePackage = false;
            return status;
        }

        DataPackage dataPackage = DataPackages.selectPackageById(history.getDataPackageId());

        if (dataPackage != null) {
            String currentAlarms = "";
            status.hasActivePackage = true;
            status.setPrimaryTraffic(dataPackage.getPrimaryTraffic());
            status.setUsedPrimaryTraffic(UsageLogs.getUsedPrimaryTrafficOfPackage(dataPackage, history));

            if (status.getUsedPrimaryTraffic() >= dataPackage.getPrimaryTraffic()) {
                currentAlarms += "اعتبار حجمی بسته به پایان رسید.";
                PackageHistories.finishPackageProcess(history);
                Helper.setMobileDataEnabled(false);
            }

            if (dataPackage.getSecondaryTraffic() != null && dataPackage.getSecondaryTraffic() != 0) {
                status.setSecondaryTraffic(dataPackage.getSecondaryTraffic());
                status.setUsedSecondaryTraffic(UsageLogs.getUsedSecondaryTrafficOfPackage(dataPackage, history));

                if (status.getUsedSecondaryTraffic() >= dataPackage.getSecondaryTraffic()) {
                    currentAlarms += " حجم شبانه بسته به پایان رسید.";
                }
            }

            Date packageActivationDate = Helper.getDateTime(history.getStartDateTime());
            Date currentDateTime = Helper.getDate(Helper.getCurrentDateTime());
            int diffDays = (int) ((currentDateTime.getTime() - packageActivationDate.getTime()) / (1000 * 60 * 60 * 24));

            int leftDays = dataPackage.getPeriod() - diffDays;
            status.setPeriod(dataPackage.getPeriod());
            status.setLeftDays(leftDays);

            if (leftDays <= 0)
                currentAlarms += "مهلت اعتبار بسته به پایان رسید";

            if (SettingsHandler.getAlarmType() == SettingsHandler.AlarmType.LeftDay.ordinal()) {
                int leftDayAlarm = SettingsHandler.getLeftDaysAlarm();
                if (leftDayAlarm >= diffDays && diffDays > 0)
                    currentAlarms += "روز باقیمانده به اتمام بسته " + diffDays;
            } else if (SettingsHandler.getAlarmType() == SettingsHandler.AlarmType.RemindedBytes.ordinal()) {
                long remindedByteAlarm = SettingsHandler.getRemindedByteAlarm();
                long reminded = status.getPrimaryTraffic() - status.getUsedPrimaryTraffic();
                if (reminded <= remindedByteAlarm) {
                    currentAlarms += "," + "حجم رو به اتمام است";
                }
            } else if (SettingsHandler.getAlarmType() == SettingsHandler.AlarmType.Both.ordinal()) {
                int leftDayAlarm = SettingsHandler.getLeftDaysAlarm();
                if (leftDayAlarm >= diffDays && diffDays > 0)
                    currentAlarms += "," + "روز باقیمانده به اتمام بسته " + diffDays;

                long remindedByteAlarm = SettingsHandler.getRemindedByteAlarm() * 1024 * 1024;
                long reminded = status.getPrimaryTraffic() - status.getUsedPrimaryTraffic();
                if (reminded <= remindedByteAlarm) {
                    currentAlarms += "," + "حجم رو به اتمام است";
                }
            }
            status.setCurrentAlarms(currentAlarms);
        }
        return status;
    }


    public Boolean getHasActivePackage() {
        return hasActivePackage;
    }

    public void setHasActivePackage(Boolean hasActivePackage) {
        this.hasActivePackage = hasActivePackage;
    }

    public long getDailyTraffic() {
        return dailyTraffic;
    }

    public void setDailyTraffic(long dailyTraffic) {
        this.dailyTraffic = dailyTraffic;
    }

    public long getPrimaryTraffic() {
        return primaryTraffic;
    }

    public void setPrimaryTraffic(long primaryTraffic) {
        this.primaryTraffic = primaryTraffic;
    }

    public long getUsedPrimaryTraffic() {
        return usedPrimaryTraffic;
    }

    public void setUsedPrimaryTraffic(long usedPrimaryTraffic) {
        this.usedPrimaryTraffic = usedPrimaryTraffic;
    }

    public long getSecondaryTraffic() {
        return secondaryTraffic;
    }

    public void setSecondaryTraffic(long secondaryTraffic) {
        this.secondaryTraffic = secondaryTraffic;
    }

    public long getUsedSecondaryTraffic() {
        return usedSecondaryTraffic;
    }

    public void setUsedSecondaryTraffic(long usedSecondaryTraffic) {
        this.usedSecondaryTraffic = usedSecondaryTraffic;
    }

    public String getSecondaryCaption() {
        return secondaryCaption;
    }

    public void setSecondaryCaption(String secondaryCaption) {
        this.secondaryCaption = secondaryCaption;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getLeftDays() {
        return leftDays;
    }

    public void setLeftDays(int leftDays) {
        this.leftDays = leftDays;
    }

    public String getCurrentAlarms() {
        return currentAlarms;
    }

    public void setCurrentAlarms(String currentAlarms) {
        this.currentAlarms = currentAlarms;
    }

}
