package com.humorstech.respyr;
import android.content.Context;
import android.content.SharedPreferences;

public class PersonalData {
    private static final String PREF_NAME = "ProfilePersonal";
    private SharedPreferences sharedPreferences;


    public PersonalData(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveData(String key, String data) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.apply();
    }

    public String getData(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void clearAll() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

