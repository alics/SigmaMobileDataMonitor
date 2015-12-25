package com.zohaltech.app.sigma.classes;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.entities.DataPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import widgets.MyToast;


public final class Helper {


    public static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String addDay(int day) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, day);
        return dateFormat.format(cal.getTime());

    }

    public static Date getDateTime(String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDate(String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Date date = null;
        try {
            date = dateFormat.parse(dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void runUssd(FragmentActivity activity, DataPackage dataPackage) {
        String code = String.format("%s%s", dataPackage.getUssdCode().substring(0, dataPackage.getUssdCode().length() - 1), Uri.encode("#"));
        Intent callIntent;
        if (Helper.isDualSim()) {
            callIntent = new Intent(Intent.ACTION_DIAL);
        } else {
            callIntent = new Intent(Intent.ACTION_CALL);
        }
        callIntent.setData(Uri.parse("tel:" + code));
        activity.startActivityForResult(callIntent, dataPackage.getId());
    }

    public static Operator getOperator() {
        Operator operator = Operator.NO_SIM;
        try {
            TelephonyManager tm = (TelephonyManager) App.context.getSystemService(Context.TELEPHONY_SERVICE);
            String simOperatorName = tm.getSimOperatorName().toUpperCase();
            if (simOperatorName.toUpperCase().compareTo("IR-MCI") == 0 || simOperatorName.compareTo("IR-TCI") == 0) {
                operator = Operator.MCI;
            } else if (simOperatorName.toUpperCase().compareTo("RIGHTEL") == 0) {
                operator = Operator.RIGHTELL;
            } else if (simOperatorName.toUpperCase().compareTo("IRANCELL") == 0) {
                operator = Operator.IRANCELL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return operator;
    }

    public static Boolean isDualSim() {
        TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(App.context);
        String sim1 = telephonyInfo.getImsiSIM1();
        String sim2 = telephonyInfo.getImsiSIM2();
        return !(sim2 == null || sim2.equals(sim1));
    }

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    public static String getDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) App.context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public static void goToWebsite(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        App.currentActivity.startActivity(browserIntent);
    }

    public static void playSound() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(App.context, notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void vibrate() {
        Vibrator vibrator = (Vibrator) App.context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);
    }

    public static String inputStreamToString(InputStream inputStream) {
        StringBuilder out = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                out.append(line);
                out.append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static void rateApp(Activity activity) {
        App.uiPreferences.edit().putBoolean("RATED", true).apply();
        Intent intent = new Intent(App.marketPollIntent);
        intent.setData(Uri.parse(App.marketPollUri));
        intent.setPackage(App.marketPackage);
        if (!myStartActivity(activity, intent)) {
            intent.setData(Uri.parse(App.marketWebsiteUri));
            if (!myStartActivity(activity, intent)) {
                MyToast.show(String.format(activity.getString(R.string.could_not_open_market), App.marketName, App.marketName), Toast.LENGTH_SHORT);
            }
        }
    }

    public static boolean myStartActivity(Activity activity, Intent intent) {
        try {
            activity.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public enum Operator {
        MCI,
        IRANCELL,
        RIGHTELL,
        NO_SIM
    }
}