package com.humorstech.respyr.authentication.login;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.humorstech.respyr.Dashboard;
import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.HTTP_URLS;
import com.humorstech.respyr.MainActivity;
import com.humorstech.respyr.ProfileGridAdapter;
import com.humorstech.respyr.R;
import com.humorstech.respyr.home.Home;
import com.humorstech.respyr.reading.GridAdapter;
import com.humorstech.respyr.reading.ProfileItem;
import com.humorstech.respyr.reading.SelectUser;
import com.humorstech.respyr.utills.ActiveProfile;
import com.humorstech.respyr.utills.LoginUtils;
import com.humorstech.respyr.utills.ProfileUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class SelectProfile extends AppCompatActivity {


    private GridView gridView;
    private ProfileGridAdapter profileGridAdapter;
    private List<Map<String, Object>> profilesDataList;
    private String loginId;

    private ShimmerFrameLayout shimmerFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_profile);

        init();

        SharedPreferences sharedPreferences3 = getSharedPreferences(LoginUtils.LOGIN_TITLE, Context.MODE_PRIVATE);
        loginId = sharedPreferences3.getString(LoginUtils.LOGIN_ID, "");
        getProfileDataById(loginId);

    }

    private void init(){
        initVars();
        onClicks();
    }

    private void initVars(){
        gridView = findViewById(R.id.profileGridView);
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
    }

    private void onClicks(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> profile = profilesDataList.get(position);
                boolean profileHav = (boolean) profile.get("profile_hav");
                String name = (String) profile.get("name");
                String profile_id = (String) profile.get("profile_id");
                String gender = (String) profile.get("gender");
                String age = (String) profile.get("age");
                String height = (String) profile.get("height");
                String weight = (String) profile.get("weight");


                if (profileHav) {
                    SharedPreferences preferences = getSharedPreferences(ActiveProfile.Title, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();

                    SharedPreferences sharedPreferences3 = getSharedPreferences(ActiveProfile.Title, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = sharedPreferences3.edit();
                    editor3.putString(ActiveProfile.PROFILE_ID, String.valueOf(profile_id));
                    editor3.putString(ActiveProfile.LOGIN_ID, String.valueOf(loginId));
                    editor3.putString(ActiveProfile.NAME, String.valueOf(name));
                    editor3.putString(ActiveProfile.GENDER, String.valueOf(gender));
                    editor3.putString(ActiveProfile.AGE, String.valueOf(age));
                    editor3.putString(ActiveProfile.HEIGHT, String.valueOf(height));
                    editor3.putString(ActiveProfile.WEIGHT, String.valueOf(weight));
                    editor3.apply();

                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(intent);

                }
            }
        });
    }



    private void getProfileDataById(String loginId1){
        RequestParams params = new RequestParams();
        params.put("login_id", loginId1);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HTTP_URLS.fetchProfilesOfLogin,params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                shimmerFrameLayout.startShimmer();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String response = new String(responseBody);
                        fetchProfileId(response);
                    }
                },1500);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Toast.makeText(SelectProfile.this, "Something went wrong ! please try again", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });
    }
    private void fetchProfileId(String jsonData){
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            Iterator<String> keys = jsonObject.keys();
            profilesDataList = new ArrayList<>();

            while (keys.hasNext()) {
                String key = keys.next();
                JSONObject profiles = jsonObject.getJSONObject(key);
                Iterator<String> profileKeys = profiles.keys();

                while (profileKeys.hasNext()) {
                    String profileKey = profileKeys.next();
                    JSONObject profileObject = profiles.getJSONObject(profileKey);

                    // Convert JSON object to Map
                    Map<String, Object> profileMap = new HashMap<>();
                    Iterator<String> profileDataKeys = profileObject.keys();
                    while (profileDataKeys.hasNext()) {
                        String profileDataKey = profileDataKeys.next();
                        profileMap.put(profileDataKey, profileObject.get(profileDataKey));
                    }

                    profilesDataList.add(profileMap);
                }
                shimmerFrameLayout.hideShimmer();
                profileGridAdapter = new ProfileGridAdapter(this, profilesDataList);
                gridView.setAdapter(profileGridAdapter);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}