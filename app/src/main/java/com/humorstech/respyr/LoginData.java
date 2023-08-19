package com.humorstech.respyr;

import android.content.Context;
import android.content.SharedPreferences;

import com.humorstech.respyr.utills.ActiveProfile;
import com.humorstech.respyr.utills.LoginUtils;

public class LoginData {
    private Context context;

    public LoginData(Context context) {
        this.context = context;
    }

    public String getLoginId() {
        SharedPreferences loginData = context.getSharedPreferences(LoginUtils.LOGIN_TITLE, Context.MODE_PRIVATE);
        return loginData.getString(LoginUtils.LOGIN_ID, null);
    }

    public String getProfileId() {
        SharedPreferences activeProfileData = context.getSharedPreferences(ActiveProfile.Title, Context.MODE_PRIVATE);
        return activeProfileData.getString(ActiveProfile.PROFILE_ID, null);
    }
}
