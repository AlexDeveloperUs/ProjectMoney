package com.flexsoft.cardpassword.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private static String SHARED_PREFERENCES_NAME = "password_name";

    private static String SHARED_PREFERENCES_KEY = "password_key";

    private static String SHARED_PREFERENCES_FIRST_LAUNCH_NAME = "launch_name";

    private static String SHARED_PREFERENCES_FIRST_LAUNCH_KEY = "launch_key";

    private static SharedPreferences sSharedPreferences;

    public static final String FIRST = "first";
    public static final String NOT_FIRST = "notFirst";
    public static final String CURRENT_PASSWORD = "currentPassword";
    public static final String SKIPPED = "skipped";
    public static final String DISABLED = "disabled";
    public static final String EMPTY_STRING = "";
    public static final String DIGITS = "[\\d]+";

    public static final String NAME = "name";
    public static final String NUMBER = "number";
    public static final String CVC = "cvc";
    public static final String VALIDITY = "validity";
    public static final String CARDHOLDER = "cardholder";
    public static final String TYPE = "type";
    public static final String PIN = "pin";

    public SharedPrefs(Context pContext) {
        sSharedPreferences = pContext.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void setFirstLaunch(String pFirstLaunch) {
        sSharedPreferences.edit().putString(SHARED_PREFERENCES_FIRST_LAUNCH_KEY, pFirstLaunch).apply();
    }

    public static String getFirstLaunch() {
        return sSharedPreferences.getString(SHARED_PREFERENCES_FIRST_LAUNCH_KEY, FIRST);
    }

    public static void setCurrentPassword(String pPassword) {
        sSharedPreferences.edit().putString(SHARED_PREFERENCES_KEY, pPassword).apply();
    }

    public static String getCurrentPassword() {
        return sSharedPreferences.getString(SHARED_PREFERENCES_KEY, EMPTY_STRING);
    }

}
