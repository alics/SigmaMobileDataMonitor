package com.zohaltech.app.mobiledatamonitor.classes;

import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public final class Helper {

//    public static Date getCurrentDateTime() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
//        Date date = null;
//        try {
//            date = dateFormat.parse(String.valueOf(new Date()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return date;
//    }

    public static String getCurrentDateTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

//    public static Time getTime(String timeStr) {
//        Time time = null;
//        if (timeStr != null && !TextUtils.isEmpty(timeStr)) {
//            time = java.sql.Time.valueOf(timeStr);
//        }
//        return time;
//    }

    public static void runUssd(String code) {
        code = String.format("%s%s", code.substring(0, code.length() - 1), Uri.encode("#"));
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + code));
        App.currentActivity.startActivity(callIntent);
    }

    private Operator getOperator() {
        TelephonyManager tm = (TelephonyManager) App.context.getSystemService(App.context.TELEPHONY_SERVICE);
        String simOperatorName = tm.getSimOperatorName().toUpperCase();
        if (simOperatorName.compareTo("IR-MCI") == 0 || simOperatorName.compareTo("IR-TCI") == 0) {
            return Operator.MCI;
        } else if (simOperatorName.compareTo("RIGHTEL") == 0) {
            return Operator.RIGHTELL;
        }
        return Operator.IRANCELL;
    }

    public enum Operator {
        MCI,
        RIGHTELL,
        IRANCELL
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
