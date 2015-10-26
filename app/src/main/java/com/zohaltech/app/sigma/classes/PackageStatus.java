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
            if (history.getPrimaryPackageEndDateTime() == null || "".equals(history.getPrimaryPackageEndDateTime())) {
                status.setUsedPrimaryTraffic(UsageLogs.getUsedPrimaryTrafficOfPackage(dataPackage, history));
            } else {
                status.setUsedPrimaryTraffic(dataPackage.getPrimaryTraffic());
            }

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
        assert dataPackage != null;
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
        assert dataPackage != null;

        PackageHistory reservedPackageHistory = PackageHistories.getReservedPackage();

        long usedPrimaryTraffic;
        if (history.getPrimaryPackageEndDateTime() == null || "".equals(history.getPrimaryPackageEndDateTime())) {
            usedPrimaryTraffic = UsageLogs.getUsedPrimaryTrafficOfPackage(dataPackage, history);
        } else {
            usedPrimaryTraffic = dataPackage.getPrimaryTraffic();
        }
        long usedSecondaryTraffic;
        if (history.getSecondaryTrafficEndDateTime() == null || "".equals(history.getSecondaryTrafficEndDateTime())) {
            usedSecondaryTraffic = UsageLogs.getUsedSecondaryTrafficOfPackage(dataPackage, history);
        } else {
            usedSecondaryTraffic = dataPackage.getSecondaryTraffic();
        }

        if (dataPackage.getSecondaryTraffic() != 0 && dataPackage.getPrimaryTraffic() != 0) {
            if (usedSecondaryTraffic >= dataPackage.getSecondaryTraffic() &&
                usedPrimaryTraffic >= dataPackage.getPrimaryTraffic()) {
                String msg = "حجم اصلی و شبانه تمام شد";
                if (reservedPackageHistory != null) {
                    msg += " و بسته رزرو فعال گردید.";
                } else {
                    msg += " و بسته به پایان رسید.";
                }
                alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.FINISH_TRAFFIC_ALARM, msg));
                PackageHistories.finishPackageProcess(history, PackageHistory.StatusEnum.TRAFFIC_FINISHED);
            } else if (usedSecondaryTraffic >= dataPackage.getSecondaryTraffic()) {
                String msg = "حجم شبانه بسته به پایان رسید و ترافیک مصرفی در ساعات شبانه، از حجم اصلی کاسته خواهد شد.";
                alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.FINISH_SECONDARY_TRAFFIC_ALARM, msg));
            } else if (usedPrimaryTraffic >= dataPackage.getPrimaryTraffic()) {
                String msg = "حجم اصلی بسته به پایان رسید، در صورت تمام شدن بسته از سوی اپراتور، هم اکنون به فعال کردن بسته جدید اقدام نمایید.";
                alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.FINISH_PRIMARY_TRAFFIC_ALARM, msg));
            }
        } else if (dataPackage.getSecondaryTraffic() == 0 && dataPackage.getPrimaryTraffic() != 0) {
            if (usedPrimaryTraffic >= dataPackage.getPrimaryTraffic()) {
                String msg = "حجم اصلی تمام شد";
                if (reservedPackageHistory != null)
                    msg += " و بسته رزرو فعال گردید.";
                else
                    msg += " و بسته به پایان رسید.";
                alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.FINISH_PRIMARY_TRAFFIC_ALARM, msg));
                PackageHistories.finishPackageProcess(history, PackageHistory.StatusEnum.TRAFFIC_FINISHED);
            }
        } else {//if (dataPackage.getSecondaryTraffic() == 0 && dataPackage.getPrimaryTraffic() != 0) {
            if (usedSecondaryTraffic >= dataPackage.getSecondaryTraffic()) {
                String msg = "حجم شبانه تمام شد";
                if (reservedPackageHistory != null)
                    msg += " و بسته رزرو فعال گردید.";
                else
                    msg += " و بسته به پایان رسید.";
                alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.FINISH_SECONDARY_TRAFFIC_ALARM, msg));
                PackageHistories.finishPackageProcess(history, PackageHistory.StatusEnum.TRAFFIC_FINISHED);
            }
        }

        Date packageActivationDate = Helper.getDateTime(history.getStartDateTime());
        Date currentDateTime = Helper.getDateTime(Helper.getCurrentDateTime());
        int diffDays = (int) ((currentDateTime.getTime() - packageActivationDate.getTime()) / (1000 * 60 * 60 * 24));
        int leftDays = dataPackage.getPeriod() - diffDays;
        if (leftDays <= 0) {
            String msg = "مهلت اعتبار بسته به پایان رسید";
            if (reservedPackageHistory != null)
                msg += " و بسته رزور فعال شد.";
            else
                msg += ".";
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
            if (dataPackage.getPrimaryTraffic() != 0) {
                long trafficAlarm = Math.round(setting.getPercentTrafficAlarm() * 0.01 * dataPackage.getPrimaryTraffic());
                if (usedPrimaryTraffic >= trafficAlarm) {
                    String msg = "بیشتر از " + setting.getPercentTrafficAlarm() + " درصد از حجم بسته مصرف شده است";
                    alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.REMINDED_TRAFFIC_ALARM, msg));
                }
            } else {
                long trafficAlarm = Math.round(setting.getPercentTrafficAlarm() * 0.01 * dataPackage.getSecondaryTraffic());
                if (usedSecondaryTraffic >= trafficAlarm) {
                    String msg = "بیشتر از " + setting.getPercentTrafficAlarm() + " درصد از حجم بسته مصرف شده است";
                    alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.REMINDED_TRAFFIC_ALARM, msg));
                }
            }
        } else if (setting.getAlarmType() == Setting.AlarmType.BOTH.ordinal()) {
            int leftDayAlarm = setting.getLeftDaysAlarm();
            if (leftDayAlarm >= leftDays && leftDays > 0) {
                String msg = leftDays + " روز باقیمانده به اتمام بسته";
                alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.REMINDED_DAYS_ALARM, msg));
            }
            if (dataPackage.getPrimaryTraffic() != 0) {
                long trafficAlarm = Math.round(setting.getPercentTrafficAlarm() * 0.01 * dataPackage.getPrimaryTraffic());
                if (usedPrimaryTraffic >= trafficAlarm) {
                    String msg = "بیشتر از " + setting.getPercentTrafficAlarm() + " درصد از حجم بسته مصرف شده است";
                    alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.REMINDED_TRAFFIC_ALARM, msg));
                }
            } else {
                long trafficAlarm = Math.round(setting.getPercentTrafficAlarm() * 0.01 * dataPackage.getSecondaryTraffic());
                if (usedSecondaryTraffic >= trafficAlarm) {
                    String msg = "بیشتر از " + setting.getPercentTrafficAlarm() + " درصد از حجم بسته مصرف شده است";
                    alarmObjects.add(new AlarmObject(AlarmObject.AlarmType.REMINDED_TRAFFIC_ALARM, msg));
                }
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
