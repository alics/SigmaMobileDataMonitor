package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


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

    public static void runUssd(Activity activity, String code) {
        code = String.format("%s%s", code.substring(0, code.length() - 1), Uri.encode("#"));
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + code));
        activity.startActivity(callIntent);
    }

    public static boolean getConnectivityStatus() {
        ConnectivityManager cm = (ConnectivityManager) App.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public static void setMobileDataEnabled(boolean enabled) {
        try {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.FROYO) {
                Method dataConnSwitchMethod;
                Class telephonyManagerClass;
                Object ITelephonyStub;
                Class ITelephonyClass;
                TelephonyManager telephonyManager = (TelephonyManager) App.context.getSystemService(Context.TELEPHONY_SERVICE);

                telephonyManagerClass = Class.forName(telephonyManager.getClass().getName());
                Method getITelephonyMethod = telephonyManagerClass.getDeclaredMethod("getITelephony");
                getITelephonyMethod.setAccessible(true);
                ITelephonyStub = getITelephonyMethod.invoke(telephonyManager);
                ITelephonyClass = Class.forName(ITelephonyStub.getClass().getName());

                if (enabled) {
                    dataConnSwitchMethod = ITelephonyClass.getDeclaredMethod("enableDataConnectivity");
                } else {
                    dataConnSwitchMethod = ITelephonyClass.getDeclaredMethod("disableDataConnectivity");
                }
                dataConnSwitchMethod.setAccessible(true);
                dataConnSwitchMethod.invoke(ITelephonyStub);
            } else {
                final ConnectivityManager conman = (ConnectivityManager) App.context.getSystemService(Context.CONNECTIVITY_SERVICE);
                final Class conmanClass = Class.forName(conman.getClass().getName());
                final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
                connectivityManagerField.setAccessible(true);
                final Object connectivityManager = connectivityManagerField.get(conman);
                final Class connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());
                final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
                setMobileDataEnabledMethod.setAccessible(true);
                setMobileDataEnabledMethod.invoke(connectivityManager, enabled);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    public static String getDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) App.context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public enum Operator {
        MCI,
        IRANCELL,
        RIGHTELL,
        NO_SIM
    }

    //public static void goToWebsite(String url) {
    //    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    //    G.currentActivity.startActivity(browserIntent);
    //}

    //    public static void share(String message)
    //    {
    //        Intent intent = new Intent(Intent.ACTION_SEND);
    //        intent.setType("text/plain");
    //        intent.putExtra(Intent.EXTRA_TEXT, message);
    //        G.currentActivity.startActivity(Intent.createChooser(intent, "اشتراک گذاری"));
    //    }

    //    public static Bitmap decodeScaledBitmapFromSdCard(String filePath, int reqWidth, int reqHeight)
    //    {
    //
    //        // First decode with inJustDecodeBounds=true to check dimensions
    //        final BitmapFactory.Options options = new BitmapFactory.Options();
    //        options.inJustDecodeBounds = true;
    //        BitmapFactory.decodeFile(filePath, options);
    //
    //        // Calculate inSampleSize
    //        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
    //
    //        // Decode bitmap with inSampleSize set
    //        options.inJustDecodeBounds = false;
    //        return BitmapFactory.decodeFile(filePath, options);
    //    }
    //
    //    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    //    {
    //        // Raw height and width of image
    //        final int height = options.outHeight;
    //        final int width = options.outWidth;
    //        int inSampleSize = 1;
    //
    //        if (height > reqHeight || width > reqWidth)
    //        {
    //
    //            final int halfHeight = height / 2;
    //            final int halfWidth = width / 2;
    //
    //            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
    //            // height and width larger than the requested height and width.
    //            while ((halfHeight / inSampleSize) > reqHeight
    //                    && (halfWidth / inSampleSize) > reqWidth)
    //            {
    //                inSampleSize *= 2;
    //            }
    //        }
    //
    //        return inSampleSize;
    //    }
    //
    //    private static String formatNumber(String number, char[] digits)
    //    {
    //        if (digits == arabicDigits)
    //            return number;
    //
    //        StringBuilder sb = new StringBuilder();
    //        for (char chr : number.toCharArray())
    //        {
    //            if (Character.isDigit(chr))
    //            {
    //                sb.append(digits[Integer.parseInt(chr + "")]);
    //            }
    //            else
    //            {
    //                sb.append(chr);
    //            }
    //        }
    //        return sb.toString();
    //    }
    //
    //    public static String formatNumberToPersianDigits(String number)
    //    {
    //        return formatNumber(number, persianDigits);
    //    }
}
