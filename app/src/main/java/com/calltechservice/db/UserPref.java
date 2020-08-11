package com.calltechservice.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.calltechservice.model.response.UserModel;
import com.google.gson.Gson;

import javax.inject.Singleton;


@Singleton
public class UserPref {
    private SharedPreferences preferences;
    private Gson gson;

    public UserPref(Context context) {
        preferences = context.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public UserModel getUser() {
        return gson.fromJson(preferences.getString("user", null), UserModel.class);
    }

    public void setUser(UserModel user) {
        Gson gson = new Gson();
        String loginRes = gson.toJson(user);
        preferences.edit().putString("user", loginRes).apply();
    }

    public boolean isLogin() {
        return preferences.getBoolean("isLogin", false);
    }

    public void setLogin(boolean login) {
        preferences.edit().putBoolean("isLogin", login).apply();
    }

    public void clearPref() {
        preferences.edit().clear().apply();
    }


    public boolean isDriverStatus() {
        return preferences.getBoolean("isDuty", false);
    }

    public void setDriverStatus(boolean duty) {
        preferences.edit().putBoolean("isDuty", duty).apply();
    }


    public static void savePreferencesDevice(Context context, String key, String value) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getPreferencesDevice(Context context, String key) {
        SharedPreferences sharedpreferences =context.getSharedPreferences("userPref", Context.MODE_PRIVATE);
        return sharedpreferences.getString(key, "");
    }


    public static void saveBookingId(Context context, String key, String value) {
        SharedPreferences sharedpreferences = context.getSharedPreferences("booking", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getBookingId(Context context, String key) {
        SharedPreferences sharedpreferences =context.getSharedPreferences("booking", Context.MODE_PRIVATE);
        return sharedpreferences.getString(key, "");
    }
}