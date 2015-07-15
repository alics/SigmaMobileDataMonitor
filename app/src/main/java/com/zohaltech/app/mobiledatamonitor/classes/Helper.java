package com.zohaltech.app.mobiledatamonitor.classes;

import android.content.Intent;
import android.net.Uri;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public final class Helper
{


    //private static final char[] arabicDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    //private static final char[] persianDigits = {'۰', '۱', '۲', '۳', '۴', '۵', '۶', '۷', '۸', '۹'};
    //private static final char[] arabicIndicDigits = {'٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩'};

    //public static boolean validateEditText(EditText editText, String caption)
    //{
    //    if (editText.getText().toString().trim().length() > 0)
    //    {
    //        return true;
    //    }
    //    else
    //    {
    //        MyToast.show("لطفا " + caption + " را وارد کنید", Toast.LENGTH_SHORT, com.melkoma.app.R.drawable.ic_warning);
    //        editText.requestFocus();
    //        return false;
    //    }
    //}

    public static String getCurrentDateTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static Date getDateTime(String dateTime)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        Date date = null;
        try
        {
            date = dateFormat.parse(dateTime);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDate(String dateTime)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        Date date = null;
        try
        {
            date = dateFormat.parse(dateTime);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    public static Time getTime(String timeStr)
    {
        Time time = java.sql.Time.valueOf(timeStr);
        return time;
    }

    public static String getNewImageFileName()
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return ("img_" + timeStamp + ".jpg");
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
