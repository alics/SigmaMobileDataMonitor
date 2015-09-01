package com.zohaltech.app.sigma.classes;

import com.zohaltech.app.sigma.dal.DataPackages;
import com.zohaltech.app.sigma.dal.PackageHistories;
import com.zohaltech.app.sigma.dal.Settings;
import com.zohaltech.app.sigma.dal.UsageLogs;
import com.zohaltech.app.sigma.entities.DataPackage;
import com.zohaltech.app.sigma.entities.PackageHistory;
import com.zohaltech.app.sigma.entities.Setting;

import java.util.ArrayList;
import java.util.Date;

public final class PackageStatus {
    long primaryTraffic;
    long usedPrimaryTraffic;
    long secondaryTraffic;
    long usedSecondaryTraffic;
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
                history.setPrimaryPackageEndDateTime(Helper.getCurrentDateTime());
                PackageHistories.update(history);
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
            return new RemainingTimeObject("روز", 0);

        DataPackage dataPackage = DataPackages.selectPackageById(history.getDataPackageId());
        Date packageActivationDate = Helper.getDateTime(history.getStartDateTime());
        Date currentDateTime = Helper.getDateTime(Helper.getCurrentDateTime());
        int diffDays = (int) ((currentDateTime.getTime() - packageActivationDate.getTime()) / (1000 * 60 * 60 * 24));
        int leftDays = dataPackage.getPeriod() - diffDays;

        if (leftDays == 0) {
            int leftHours = (int) ((currentDateTime.getTime() - packageActivationDate.getTime()) / (1000 * 60 * 60)) - dataPackage.getPeriod() * 24;
            return new RemainingTimeObject("ساعت", leftHours);
        }
        return new RemainingTimeObject("روز", leftDays);
    }

    public static ArrayList<AlarmObject> getCurrentAlarms() {
        Setting setting = Settings.getCurrentSettings();
        PackageHistory history = PackageHistories.getActivePackage();
        ArrayList<AlarmObject> alarmObjects = new ArrayList<>();

        if (history == null) {
            return alarmObjects;
        }

        DataPackage dataPackage = DataPackages.selectPackageById(history.getDataPackageId());
        PackageHistory reservedPackageHistory = PackageHistories.getReservedPackage();
        long usedPrimaryTraffic = UsageLogs.getUsedPrimaryTrafficOfPackage(dataPackage, history);

        if (usedPrimaryTraffic >= dataPackage.getPrimaryTraffic()) {
            String msg = "اعتبار حجمی بسته به پایان رسید";
            if (reservedPackageHistory != null)
                msg += " و بسته رزور شده فعال شد";
            alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.FINISH_TRAFFIC_ALARM, msg));

            history.setPrimaryPackageEndDateTime(Helper.getCurrentDateTime());
            PackageHistories.update(history);
            //PackageHistories.finishPackageProcess(history, PackageHistory.StatusEnum.TRAFFIC_FINISHED);
            //Helper.setMobileDataEnabled(false);
        }

        if (dataPackage.getSecondaryTraffic() != null && dataPackage.getSecondaryTraffic() != 0) {
            long usedSecondaryTraffic = UsageLogs.getUsedSecondaryTrafficOfPackage(dataPackage, history);

            if (usedSecondaryTraffic >= dataPackage.getSecondaryTraffic()) {
                String msg = "حجم شبانه بسته به پایان رسید";
                alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.FINISH_SECONDARY_TRAFFIC_ALARM, msg));
            }
        }

        Date packageActivationDate = Helper.getDateTime(history.getStartDateTime());
        Date currentDateTime = Helper.getDateTime(Helper.getCurrentDateTime());
        int diffDays = (int) ((currentDateTime.getTime() - packageActivationDate.getTime()) / (1000 * 60 * 60 * 24));
        int leftDays = dataPackage.getPeriod() - diffDays;
        if (leftDays <= 0) {
            String msg = "مهلت اعتبار بسته به پایان رسید";
            if (reservedPackageHistory != null)
                msg += " و بسته رزور شده فعال شد";
            alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.FINISH_VALIDATION_DATE_ALARM, msg));
            PackageHistories.finishPackageProcess(history, PackageHistory.StatusEnum.PERIOD_FINISHED);
        }

        if (setting.getAlarmType() == Setting.AlarmType.LEFT_DAY.ordinal()) {
            int leftDayAlarm = setting.getLeftDaysAlarm();
            if (leftDayAlarm >= leftDays && leftDays > 0) {
                String msg = leftDays + " روز باقیمانده به اتمام بسته";
                alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.REMINDED_DAYS_ALARM, msg));
            }

        } else if (setting.getAlarmType() == Setting.AlarmType.REMINDED_BYTES.ordinal()) {
            long trafficAlarm = Math.round(setting.getPercentTrafficAlarm() * 0.01 * dataPackage.getPrimaryTraffic());
            if (usedPrimaryTraffic >= trafficAlarm) {
                String msg = "بیشتر از " + setting.getPercentTrafficAlarm() + " درصد از حجم بسته مصرف شده است";
                alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.REMINDED_TRAFFIC_ALARM, msg));
            }
        } else if (setting.getAlarmType() == Setting.AlarmType.BOTH.ordinal()) {
            int leftDayAlarm = setting.getLeftDaysAlarm();
            if (leftDayAlarm >= leftDays && leftDays > 0) {
                String msg = leftDays + " روز باقیمانده به اتمام بسته";
                alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.REMINDED_DAYS_ALARM, msg));
            }

            long trafficAlarm = Math.round(setting.getPercentTrafficAlarm() * 0.01 * dataPackage.getPrimaryTraffic());
            long reminded = dataPackage.getPrimaryTraffic() - usedPrimaryTraffic;
            if (usedPrimaryTraffic >= trafficAlarm) {
                String msg = "بیشتر از " + setting.getPercentTrafficAlarm() + " درصد از حجم بسته مصرف شده است";
                alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.REMINDED_TRAFFIC_ALARM, msg));
            }
        }
        return alarmObjects;
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
}
