package com.zohaltech.app.mobiledatamonitor.classes;


import com.zohaltech.app.mobiledatamonitor.dal.DataPackages;
import com.zohaltech.app.mobiledatamonitor.dal.PackageHistories;
import com.zohaltech.app.mobiledatamonitor.dal.UsageLogs;
import com.zohaltech.app.mobiledatamonitor.entities.DataPackage;
import com.zohaltech.app.mobiledatamonitor.entities.PackageHistory;

import java.util.Date;

public final class PackageStatus {
    long   primaryTraffic;
    long   usedPrimaryTraffic;
    long   secondaryTraffic;
    long   usedSecondaryTraffic;
    String secondaryCaption;

    public static PackageStatus getCurrentStatus() {
        PackageStatus status = new PackageStatus();
        PackageHistory history = PackageHistories.getActivePackage();

        if (history == null) {
            status.setPrimaryTraffic(0);
            status.setUsedPrimaryTraffic(0);
            status.setSecondaryTraffic(0);
            status.setUsedSecondaryTraffic(0);
            status.setSecondaryCaption(null);

            return status;
        }

        DataPackage dataPackage = DataPackages.selectPackageById(history.getDataPackageId());

        if (dataPackage != null) {
            status.setPrimaryTraffic(dataPackage.getPrimaryTraffic());
            status.setUsedPrimaryTraffic(UsageLogs.getUsedPrimaryTrafficOfPackage(dataPackage, history));

            if (status.getUsedPrimaryTraffic() >= dataPackage.getPrimaryTraffic()) {
                PackageHistories.finishPackageProcess(history, PackageHistory.StatusEnum.TRAFFIC_FINISHED);
                Helper.setMobileDataEnabled(false);
            }

            if (dataPackage.getSecondaryTraffic() != null && dataPackage.getSecondaryTraffic() != 0) {
                status.setSecondaryTraffic(dataPackage.getSecondaryTraffic());
                status.setUsedSecondaryTraffic(UsageLogs.getUsedSecondaryTrafficOfPackage(dataPackage, history));
                String caption = dataPackage.getSecondaryTrafficStartTime() + " تا " + dataPackage.getSecondaryTrafficEndTime();
                status.setSecondaryCaption(caption);
            }
        }
        return status;
    }

    public static RemainingTimeObject getLeftDays() {
        PackageHistory history = PackageHistories.getActivePackage();
        if (history == null)
            return null;

        DataPackage dataPackage = DataPackages.selectPackageById(history.getDataPackageId());
        Date packageActivationDate = Helper.getDateTime(history.getStartDateTime());
        Date currentDateTime = Helper.getDate(Helper.getCurrentDateTime());
        int diffDays = (int) ((currentDateTime.getTime() - packageActivationDate.getTime()) / (1000 * 60 * 60 * 24));
        int leftDays = dataPackage.getPeriod() - diffDays;

        if (leftDays == 0) {
            int leftHours = (int) ((currentDateTime.getTime() - packageActivationDate.getTime()) / (1000 * 60 * 60));
            return new RemainingTimeObject(RemainingTimeObject.TimeType.HOUR, leftHours);
        }
        return new RemainingTimeObject(RemainingTimeObject.TimeType.DAY, leftDays);
    }

    //    public ArrayList<AlarmObject> getCurrentAlarms(){
    //
    //        PackageHistory history = PackageHistories.getActivePackage();
    //
    //        if (history == null) {
    //            status.setPrimaryTraffic(0);
    //            status.setUsedPrimaryTraffic(0);
    //            status.setSecondaryTraffic(0);
    //            status.setUsedSecondaryTraffic(0);
    //            status.setSecondaryCaption(null);
    //
    //            return status;
    //        }
    //
    //        DataPackage dataPackage = DataPackages.selectPackageById(history.getDataPackageId());
    //
    //        if (dataPackage != null) {
    //            String currentAlarms = "";
    //            status.hasActivePackage = true;
    //            status.setPrimaryTraffic(dataPackage.getPrimaryTraffic());
    //            status.setUsedPrimaryTraffic(UsageLogs.getUsedPrimaryTrafficOfPackage(dataPackage, history));
    //
    //            if (status.getUsedPrimaryTraffic() >= dataPackage.getPrimaryTraffic()) {
    //                currentAlarms += "اعتبار حجمی بسته به پایان رسید.";
    //                PackageHistories.finishPackageProcess(history, PackageHistory.StatusEnum.TRAFFIC_FINISHED);
    //                Helper.setMobileDataEnabled(false);
    //            }
    //
    //            if (dataPackage.getSecondaryTraffic() != null && dataPackage.getSecondaryTraffic() != 0) {
    //                status.setSecondaryTraffic(dataPackage.getSecondaryTraffic());
    //                status.setUsedSecondaryTraffic(UsageLogs.getUsedSecondaryTrafficOfPackage(dataPackage, history));
    //
    //                if (status.getUsedSecondaryTraffic() >= dataPackage.getSecondaryTraffic()) {
    //                    currentAlarms += " حجم شبانه بسته به پایان رسید.";
    //                }
    //            }
    //
    //            Date packageActivationDate = Helper.getDateTime(history.getStartDateTime());
    //            Date currentDateTime = Helper.getDate(Helper.getCurrentDateTime());
    //            int diffDays = (int) ((currentDateTime.getTime() - packageActivationDate.getTime()) / (1000 * 60 * 60 * 24));
    //
    //            int leftDays = dataPackage.getPeriod() - diffDays;
    //            status.setPeriod(dataPackage.getPeriod());
    //            status.setLeftDays(leftDays);
    //
    //            if (leftDays <= 0) {
    //                currentAlarms += "مهلت اعتبار بسته به پایان رسید";
    //                PackageHistories.finishPackageProcess(history, PackageHistory.StatusEnum.PERIOD_FINISHED);
    //                Helper.setMobileDataEnabled(false);
    //            }
    //
    //            if (SettingsHandler.getAlarmType() == SettingsHandler.AlarmType.LeftDay.ordinal()) {
    //                int leftDayAlarm = SettingsHandler.getLeftDaysAlarm();
    //                if (leftDayAlarm >= diffDays && diffDays > 0)
    //                    currentAlarms += "روز باقیمانده به اتمام بسته " + diffDays;
    //            } else if (SettingsHandler.getAlarmType() == SettingsHandler.AlarmType.RemindedBytes.ordinal()) {
    //                long remindedByteAlarm = SettingsHandler.getRemindedByteAlarm();
    //                long reminded = status.getPrimaryTraffic() - status.getUsedPrimaryTraffic();
    //                if (reminded <= remindedByteAlarm) {
    //                    currentAlarms += "," + "حجم رو به اتمام است";
    //                }
    //            } else if (SettingsHandler.getAlarmType() == SettingsHandler.AlarmType.Both.ordinal()) {
    //                int leftDayAlarm = SettingsHandler.getLeftDaysAlarm();
    //                if (leftDayAlarm >= diffDays && diffDays > 0)
    //                    currentAlarms += "," + "روز باقیمانده به اتمام بسته " + diffDays;
    //
    //                long remindedByteAlarm = SettingsHandler.getRemindedByteAlarm() * 1024 * 1024;
    //                long reminded = status.getPrimaryTraffic() - status.getUsedPrimaryTraffic();
    //                if (reminded <= remindedByteAlarm) {
    //                    currentAlarms += "," + "حجم رو به اتمام است";
    //                }
    //            }
    //            status.setCurrentAlarms(currentAlarms);
    //
    //
    //    }

    //    public static int getLeftDays(){
    //
    //        return 1;
    //
    //    }


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
}
