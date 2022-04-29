package com.example.dbts;

import android.text.TextUtils;
import android.util.Patterns;


public class DataHandler {
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


}
