package com.humorstech.respyr.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveProfileId {
    private static final String PREF_NAME = "MyPreferences";
    private static SharedPreferences sharedPreferences;

    private SaveProfileId() {
        // Private constructor to prevent instantiation
    }

    public static void initialize(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void saveProfileId(String profileId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ProfileId", profileId);
        editor.apply();
    }

    public static String getProfileId() {
        return sharedPreferences.getString("ProfileId", null);
    }

    public static void removeKey() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("profileId");
        editor.apply();
    }

    public static void clearAll() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

//   SharedPreferencesManager.initialize(getApplicationContext());
//
//           // Use the SharedPreferencesManager methods as needed
//           SharedPreferencesManager.saveString("name", "John Doe");
//           String savedName = SharedPreferencesManager.getString("name", "");
//           SharedPreferencesManager.removeKey("name");
//           SharedPreferencesManager.clearAll();

