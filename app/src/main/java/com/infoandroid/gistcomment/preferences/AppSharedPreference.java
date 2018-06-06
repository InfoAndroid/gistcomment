package com.infoandroid.gistcomment.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class AppSharedPreference {

    // SharePrefrence Name
    public static final String BESPOCUT_PREF_NAME = "gistComment";


    /**
     * @param key
     * @param defValue
     * @param context
     * @return
     */
    public static int getInt(String key, int defValue, @NonNull Context context) {
        return getSharedPreferences(context).getInt(key, defValue);
    }

    /**
     * @param key
     * @param defValue
     * @param context
     * @return
     */
    @Nullable
    public static String getString(String key, String defValue, @NonNull Context context) {
        return getSharedPreferences(context).getString(key, defValue);
    }

    public static boolean getBoolean(String key, boolean defValue, @NonNull Context context) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }


    /**
     * @param key
     * @param defValue
     * @param context
     * @return
     */
    public static float getFloat(String key, float defValue, @NonNull Context context) {
        return getSharedPreferences(context).getFloat(key, defValue);
    }

    /**
     * @param key
     * @param value
     * @param context
     */
    public static void putInt(String key, int value, @NonNull Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void putBoolean(String key, boolean value, @NonNull Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    /**
     * @param key
     * @param value
     * @param context
     */
    public static void putFloat(String key, float value, @NonNull Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(key, value);
        editor.commit();
    }


    /**
     * @param key
     * @param defaultValue
     * @param context
     */
    public static void putString(String key, String defaultValue, @NonNull Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, defaultValue);
        editor.commit();
    }

    /**
     * @param context
     * @return
     */
    private static SharedPreferences getSharedPreferences(@NonNull Context context) {
        SharedPreferences pref = context.getSharedPreferences(AppSharedPreference.BESPOCUT_PREF_NAME, Context.MODE_PRIVATE);
        return pref;
    }


    public static void remove(String key, @NonNull Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(key);
        editor.commit();
    }

    public static void clearAllPreferences(@NonNull Context context) {
        SharedPreferences settings = context.getSharedPreferences(BESPOCUT_PREF_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }
}