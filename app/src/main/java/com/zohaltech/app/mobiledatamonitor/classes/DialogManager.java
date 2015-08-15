package com.zohaltech.app.mobiledatamonitor.classes;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.rey.material.app.TimePickerDialog;
import com.zohaltech.app.mobiledatamonitor.R;

import java.text.SimpleDateFormat;

public final class DialogManager {

    public static String timeResult;

    public static void showConfirmationDialog(
            Activity activity
            , final String caption
            , final String message
            , String positiveButtonText
            , String negativeButtonText
            , final Runnable onDialogShown
            , final Runnable onPositiveButtonClick) {
        if (onDialogShown != null) {
            onDialogShown.run();
        }
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirmation);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        TextView txtDialogCaption = (TextView) dialog.findViewById(R.id.txtDialogCaption);
        TextView txtDialogMessage = (TextView) dialog.findViewById(R.id.txtDialogMessage);
        Button positiveButton = (Button) dialog.findViewById(R.id.positiveButton);
        Button negativeButton = (Button) dialog.findViewById(R.id.negativeButton);
        txtDialogCaption.setText(caption);
        txtDialogMessage.setText(message);
        positiveButton.setText(positiveButtonText);
        negativeButton.setText(negativeButtonText);

        positiveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onPositiveButtonClick.run();
                dialog.dismiss();
            }
        });

        negativeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showChoiceDialog(
            Activity activity
            , final String caption
            , final String message
            , String positiveButtonText
            , String negativeButtonText
            , final Runnable onDialogShown
            , final Runnable onPositiveButtonClick
            , final Runnable onNegativeButtonClick) {
        if (onDialogShown != null) {
            onDialogShown.run();
        }
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirmation);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        TextView txtDialogCaption = (TextView) dialog.findViewById(R.id.txtDialogCaption);
        TextView txtDialogMessage = (TextView) dialog.findViewById(R.id.txtDialogMessage);
        Button positiveButton = (Button) dialog.findViewById(R.id.positiveButton);
        Button negativeButton = (Button) dialog.findViewById(R.id.negativeButton);
        txtDialogCaption.setText(caption);
        txtDialogMessage.setText(message);
        positiveButton.setText(positiveButtonText);
        negativeButton.setText(negativeButtonText);

        positiveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onPositiveButtonClick.run();
                dialog.dismiss();
            }
        });

        negativeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onNegativeButtonClick.run();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showTimePickerDialog(Activity activity
            , String caption, int hour, int minute, final Runnable onPositiveActionClick) {

        final TimePickerDialog timePickerDialog = new TimePickerDialog(activity);
        timePickerDialog.title(caption);
        timePickerDialog.cancelable(true);
        timePickerDialog.cornerRadius(5);
        timePickerDialog.hour(hour);
        timePickerDialog.minute(minute);
        timePickerDialog.negativeAction("انصراف");
        timePickerDialog.positiveAction("تایید");
        timePickerDialog.positiveActionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeResult = timePickerDialog.getFormattedTime(new SimpleDateFormat("HH:mm"));
                onPositiveActionClick.run();
                timeResult = "";
                timePickerDialog.dismiss();
            }
        });
        timePickerDialog.negativeActionClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.dismiss();
            }
        });
        timePickerDialog.show();
    }
}
