package com.zohaltech.app.sigma.classes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.rey.material.app.TimePickerDialog;
import com.zohaltech.app.sigma.R;
import com.zohaltech.app.sigma.activities.PackageSettingsActivity;
import com.zohaltech.app.sigma.datepicker.PersianCalendar;
import com.zohaltech.app.sigma.datepicker.PersianDatePicker;
import com.zohaltech.app.sigma.dal.DataPackages;
import com.zohaltech.app.sigma.dal.PackageHistories;
import com.zohaltech.app.sigma.entities.DataPackage;
import com.zohaltech.app.sigma.entities.PackageHistory;

import java.text.SimpleDateFormat;

public final class DialogManager {

    public static String timeResult;
    public static String dateResult;

    public static void showConfirmationDialog(
            final Context context
            , final String caption
            , final String message
            , final String positiveButtonText
            , final String negativeButtonText
            , final Runnable onDialogShown
            , final Runnable onPositiveButtonClick) {
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                if (onDialogShown != null) {
                    onDialogShown.run();
                }
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_confirmation);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(true);
                TextView txtCaption = (TextView) dialog.findViewById(R.id.txtCaption);
                TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
                Button positiveButton = (Button) dialog.findViewById(R.id.positiveButton);
                Button negativeButton = (Button) dialog.findViewById(R.id.negativeButton);
                txtCaption.setText(caption);
                txtMessage.setText(message);
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
        });
    }

    public static void showNotificationDialog(
            final Context context
            , final String caption
            , final String message
            , final String positiveButtonText) {
        App.handler.post(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_confirmation);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(true);
                TextView txtCaption = (TextView) dialog.findViewById(R.id.txtCaption);
                TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
                Button positiveButton = (Button) dialog.findViewById(R.id.positiveButton);
                Button negativeButton = (Button) dialog.findViewById(R.id.negativeButton);
                negativeButton.setVisibility(View.GONE);
                txtCaption.setText(caption);
                txtMessage.setText(message);
                positiveButton.setText(positiveButtonText);

                positiveButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public static void showChoiceDialog(
            Context context
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
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_confirmation);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        TextView txtCaption = (TextView) dialog.findViewById(R.id.txtCaption);
        TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
        Button positiveButton = (Button) dialog.findViewById(R.id.positiveButton);
        Button negativeButton = (Button) dialog.findViewById(R.id.negativeButton);
        txtCaption.setText(caption);
        txtMessage.setText(message);
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

    public static Dialog getPopupDialog(
            Context context
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
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_popup);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        TextView txtCaption = (TextView) dialog.findViewById(R.id.txtCaption);
        TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
        Button positiveButton = (Button) dialog.findViewById(R.id.positiveButton);
        Button negativeButton = (Button) dialog.findViewById(R.id.negativeButton);
        txtCaption.setText(caption);
        txtMessage.setText(message);
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
        return dialog;
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

    public static void showDatePickerDialog(Activity activity, int year, int month, int day, final Runnable onPositiveActionClick) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_date_picker);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);

        final PersianDatePicker datePicker = (PersianDatePicker) dialog.findViewById(R.id.datePicker);
        PersianCalendar persianCalendar = new PersianCalendar();
        persianCalendar.setPersianDate(year, month, day);
        datePicker.setDisplayPersianDate(persianCalendar);

        Button positiveButton = (Button) dialog.findViewById(R.id.positiveButton);
        Button negativeButton = (Button) dialog.findViewById(R.id.negativeButton);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateResult = Helper.getDate(datePicker.getDisplayDate());
                onPositiveActionClick.run();
                dateResult="";
                dialog.dismiss();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public static void showPackageActivationDialog(final DataPackage dataPackage) {
        DialogManager.showConfirmationDialog(App.currentActivity, "فعالسازی بسته", "آیا مایل به فعالسازی بسته " + dataPackage.getTitle() + " هستید؟",
                                             "بله", "خیر", null, new Runnable() {
                    @Override
                    public void run() {
                        final PackageHistory history = PackageHistories.getActivePackage();
                        if (history == null) {
                            PackageHistories.insert(new PackageHistory(dataPackage.getId(), Helper.getCurrentDateTime(), null, null, null, null, PackageHistory.StatusEnum.ACTIVE.ordinal()));
                            Intent intent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                            intent.putExtra(PackageSettingsActivity.INIT_MODE_KEY, PackageSettingsActivity.MODE_SETTING_ACTIVE);
                            intent.putExtra(PackageSettingsActivity.PACKAGE_ID_KEY, dataPackage.getId());
                            intent.putExtra(PackageSettingsActivity.FORM_MODE_KEY, PackageSettingsActivity.FORM_MODE_NEW);
                            App.currentActivity.startActivity(intent);
                            App.currentActivity.finish();

                        } else {
                            DataPackage activePackage = DataPackages.selectPackageById(history.getDataPackageId());
                            DialogManager.showChoiceDialog(App.currentActivity,
                                                           "رزرو بسته",
                                                           String.format("هم اکنون بسته فعال %s وجود دارد، آیا بسته %s به عنوان بسته رزرو در نظر گرفته شود؟", activePackage.getTitle(), dataPackage.getTitle()),
                                                           "رزرو شود",
                                                           "فعال شود",
                                                           null,
                                                           new Runnable() {
                                                               @Override
                                                               public void run() {
                                                                   PackageHistories.deletedReservedPackages();
                                                                   PackageHistories.insert(new PackageHistory(dataPackage.getId(), null, null, null, null, null, PackageHistory.StatusEnum.RESERVED.ordinal()));
                                                                   Intent intent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                                                                   intent.putExtra(PackageSettingsActivity.INIT_MODE_KEY, PackageSettingsActivity.MODE_SETTING_RESERVED);
                                                                   intent.putExtra(PackageSettingsActivity.PACKAGE_ID_KEY, dataPackage.getId());
                                                                   intent.putExtra(PackageSettingsActivity.FORM_MODE_KEY, PackageSettingsActivity.FORM_MODE_NEW);
                                                                   App.currentActivity.startActivity(intent);
                                                                   App.currentActivity.finish();
                                                               }

                                                           }, new Runnable() {
                                        public void run() {
                                            PackageHistories.deletedReservedPackages();
                                            PackageHistories.finishPackageProcess(history, PackageHistory.StatusEnum.CANCELED);
                                            PackageHistories.insert(new PackageHistory(dataPackage.getId(), Helper.getCurrentDateTime(), null, null, null, null, PackageHistory.StatusEnum.ACTIVE.ordinal()));
                                            Intent intent = new Intent(App.currentActivity, PackageSettingsActivity.class);
                                            intent.putExtra(PackageSettingsActivity.INIT_MODE_KEY, PackageSettingsActivity.MODE_SETTING_ACTIVE);
                                            intent.putExtra(PackageSettingsActivity.PACKAGE_ID_KEY, dataPackage.getId());
                                            intent.putExtra(PackageSettingsActivity.FORM_MODE_KEY, PackageSettingsActivity.FORM_MODE_NEW);
                                            App.currentActivity.startActivity(intent);
                                            App.currentActivity.finish();
                                        }
                                    });
                        }
                    }
                });
    }
}
