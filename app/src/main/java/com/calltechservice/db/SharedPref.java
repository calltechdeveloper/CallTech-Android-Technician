package com.calltechservice.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import com.calltechservice.R;


public class SharedPref {

    public static void savePreferencesDevice(Context context, String key, String value) {
        SharedPreferences sharedpreferences =context.getSharedPreferences(context.getString(R.string.app_prf_device), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getPreferencesDevice(Context context, String key) {
        SharedPreferences sharedpreferences =context.getSharedPreferences(context.getString(R.string.app_prf_device), Context.MODE_PRIVATE);
        return sharedpreferences.getString(key, "");
    }

    public static void savePreferencesString(Context context, String key, String value) {
        SharedPreferences sharedpreferences =context.getSharedPreferences(context.getString(R.string.app_prf), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getPreferencesString(Context context, String key) {
        SharedPreferences sharedpreferences =context.getSharedPreferences(context.getString(R.string.app_prf), Context.MODE_PRIVATE);
        return sharedpreferences.getString(key, "");
    }

    public static void savePreferenceBoolean(Context context, String key, boolean b) {
        SharedPreferences sharedpreferences =context.getSharedPreferences(context.getString(R.string.app_prf), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean(key, b);
        editor.apply();
    }

    public static boolean getPreferenceBoolean(Context context, String key) {
        SharedPreferences sharedpreferences =context.getSharedPreferences(context.getString(R.string.app_prf), Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean(key, false);
    }

    public static void removePreference(Context context, String key) {
        SharedPreferences sharedpreferences =context.getSharedPreferences(context.getString(R.string.app_prf), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedpreferences.edit();
        editor.putBoolean(key,false);
        editor.apply();
    }


    public static void savePreferencesLat(Context context, String key, Location location) {
        SharedPreferences sharedpreferences =context.getSharedPreferences(context.getString(R.string.app_prf), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putLong(key, Double.doubleToLongBits(location.getLatitude()));
        editor.apply();
    }


    public static Double getPreferencesLat(Context context, String key) {
        SharedPreferences sharedpreferences =context.getSharedPreferences(context.getString(R.string.app_prf), Context.MODE_PRIVATE);
        return Double.longBitsToDouble(sharedpreferences.getLong(key, 0));
    }

    public static void savePreferencesLong(Context context, String key, Location location) {
        SharedPreferences sharedpreferences =context.getSharedPreferences(context.getString(R.string.app_prf), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putLong(key, Double.doubleToLongBits(location.getLongitude()));
        editor.apply();
    }


    public static Double getPreferencesLong(Context context, String key) {
        SharedPreferences sharedpreferences =context.getSharedPreferences(context.getString(R.string.app_prf), Context.MODE_PRIVATE);
        return Double.longBitsToDouble(sharedpreferences.getLong(key, 0));
    }

    public static void removePreference(Context mContext) {
        SharedPreferences sharedpreferences =mContext.getSharedPreferences(mContext.getString(R.string.app_prf), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }
}
