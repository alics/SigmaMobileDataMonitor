package com.zohaltech.app.sigma.classes;

import android.widget.EditText;
import android.widget.Toast;

import com.zohaltech.app.sigma.R;

import widgets.MyToast;

public final class Validator {

    public static boolean validateEditText(EditText editText, String caption) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        } else {
            MyToast.show("لطفا " + caption + " را وارد کنید", Toast.LENGTH_SHORT, R.drawable.ic_warning_white);
            editText.requestFocus();
            return false;
        }
    }
}
