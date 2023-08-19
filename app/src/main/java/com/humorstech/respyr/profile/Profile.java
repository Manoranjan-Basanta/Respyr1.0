package com.humorstech.respyr.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.humorstech.respyr.HTTP_URLS;
import com.humorstech.respyr.Logout;
import com.humorstech.respyr.MainActivity;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.profile.user.ProfileData;
import com.humorstech.respyr.profile.user.SelectProfile;
import com.humorstech.respyr.reading.CancelTest;
import com.humorstech.respyr.utills.ActiveProfile;
import com.humorstech.respyr.utills.LoginUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class Profile extends AppCompatActivity {

    private static final String TAG = "Profile";
    private ShimmerFrameLayout shimmerFrameLayout;
    private TextView txtLog;
    private String loginId, profileId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        StatusBarColor statusBarColor= new StatusBarColor(  Profile.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));

        init();

    }




    private void init(){
        setReferences();
        txtLog.setText("");
        txtLog.append("Hi"+"\n");
        getActiveProfile();
        setMenuItems();
        onClicks();
    }

    private void setReferences() {
        txtLog=findViewById(R.id.txtLog);
        shimmerFrameLayout=findViewById(R.id.shimmerLayout);
    }

    private void onClicks(){
        ImageButton buttonBack =findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void setMenuItems(){
        LinearLayout llMenu1 =findViewById(R.id.ll_profile_main_menu_1);
        LinearLayout llMenu2 =findViewById(R.id.ll_profile_main_menu_2);
        LinearLayout llMenu3 =findViewById(R.id.ll_profile_main_menu_3);
        LinearLayout llMenu4 =findViewById(R.id.ll_profile_main_menu_4);
        LinearLayout llMenu5 =findViewById(R.id.ll_profile_main_menu_5);
        LinearLayout llSwitchProfile =findViewById(R.id.ll_profile_main_menu_6);
        LinearLayout llLogout =findViewById(R.id.ll_profile_main_menu_logout);
        ImageButton llChangeProfile =findViewById(R.id.button_change_profile);

        llMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileData.class);
                startActivity(intent);
            }
        });
        llMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileData.class);
                startActivity(intent);
            }
        });
        llMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileData.class);
                startActivity(intent);
            }
        });
        llMenu4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileData.class);
                startActivity(intent);
            }
        });
        llMenu5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileData.class);
                startActivity(intent);
            }
        });

        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout.show(getApplicationContext(),Profile.this);
            }
        });


        llSwitchProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SelectProfile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.bottom_to_top,0);
                finish();

            }
        });
        llChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectProfile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.bottom_to_top,0);
                finish();
            }
        });
    }

    private void getActiveProfile(){

        SharedPreferences sharedPreferences2 = getSharedPreferences(LoginUtils.LOGIN_TITLE, Context.MODE_PRIVATE);
        loginId = sharedPreferences2.getString(LoginUtils.LOGIN_ID, null);

        SharedPreferences sharedPreferences = getSharedPreferences(ActiveProfile.Title, Context.MODE_PRIVATE);
        profileId = sharedPreferences.getString(ActiveProfile.PROFILE_ID, null);

        String datPersonalInfo = sharedPreferences.getString(ActiveProfile.DATA_PERSONAL_INFORMATION, null);


        txtLog.append(loginId + "\n");
        txtLog.append(profileId + "\n");
        txtLog.append(datPersonalInfo + "\n");

        if (loginId!=null){

            fetchActiveProfileData(loginId, profileId);
            shimmerFrameLayout.startShimmer();
//            txtLog.append(datPersonalInfo+"\n");
//            if (datPersonalInfo==null){
//                txtLog.append("1"+"\n");
//                shimmerFrameLayout.startShimmer();
//                fetchActiveProfileData(loginId, profileId);
//            }else if(datPersonalInfo==null){
//                txtLog.append("2"+"\n");
//              //  shimmerFrameLayout.startShimmer();
//                decodePersonalData(datPersonalInfo);
//            }
        }else{
            Toast.makeText(getApplicationContext(), "Something went wrong !!", Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    }

    private void fetchActiveProfileData(String loginID, String profileID){

        RequestParams params = new RequestParams();
        params.put("login_id",loginID);
        params.put("profile_id",profileID);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HTTP_URLS.fetchProfileDataByProfileId,params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                txtLog.append("Started" + "\n");
                txtLog.append(params + "\n");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {



                        String response = new String(responseBody);

                        System.out.println("response --->"+response);
                        txtLog.append(response + "\n");
                        saveData(response);
                    }
                },500);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "Something went wrong !!", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void saveData(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONObject dataPersonalInfo = jsonObject.getJSONObject("data_personal_info");
           // JSONObject dataHabitsInfo= jsonObject.getJSONObject("data_habits");
           // JSONObject dataMedicalInfo = jsonObject.getJSONObject("data_blood_report");

            SharedPreferences sharedPreferences = getSharedPreferences(ActiveProfile.Title, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ActiveProfile.DATA_PERSONAL_INFORMATION, String.valueOf(dataPersonalInfo));
         //   editor.putString(ActiveProfile.DATA_HABITS_INFORMATION, String.valueOf(dataHabitsInfo));
          //  editor.putString(ActiveProfile.DATA_MEDICAL_INFORMATION, String.valueOf(dataMedicalInfo));
            editor.apply();



            // set profile
            decodePersonalData(String.valueOf(dataPersonalInfo));

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),String.valueOf(e.getMessage()), Toast.LENGTH_SHORT ).show();

        }
    }

    @SuppressLint("SetTextI18n")
    private void decodePersonalData(String jsonData){
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String phone = jsonObject.getString("phone");
            String name = jsonObject.getString("name");
            String email = jsonObject.getString("email");
            String gender = jsonObject.getString("gender");
            String dob = jsonObject.getString("dob");
            String age = jsonObject.getString("age");
            String height = jsonObject.getString("height");
            String weight = jsonObject.getString("weight");

            SharedPreferences sharedPreferences = getSharedPreferences(ActiveProfile.Title, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(ActiveProfile.PHONE_NUMBER, String.valueOf(phone));
            editor.putString(ActiveProfile.NAME, String.valueOf(name));
            editor.putString(ActiveProfile.EMAIL, String.valueOf(email));
            editor.putString(ActiveProfile.GENDER, String.valueOf(gender));
            editor.putString(ActiveProfile.DOB, String.valueOf(dob));
            editor.putString(ActiveProfile.AGE, String.valueOf(age));
            editor.putString(ActiveProfile.HEIGHT, String.valueOf(height));
            editor.putString(ActiveProfile.WEIGHT, String.valueOf(weight));
            editor.apply();




            TextView txtName =findViewById(R.id.txt_user_name);
            TextView txtUserGenderAge =findViewById(R.id.txt_user_gender_age);
            TextView txtUserEmailPhone =findViewById(R.id.txt_email_phone);
            TextView txtAppVersion =findViewById(R.id.txt_app_version);
            txtName.setText(name);
            txtUserGenderAge.setText(gender +", " + age + " years");
            txtUserEmailPhone.setText("+91 "+ phone);
            setAvatar(gender);

            shimmerFrameLayout.hideShimmer();

        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private void setAvatar(String gender){
        ImageView imgUseAvatar =findViewById(R.id.img_user_avtar);
        int profileColor1 = ContextCompat.getColor(getApplicationContext(), R.color.profile1);
        int profileColor2 = ContextCompat.getColor(getApplicationContext(), R.color.profile2);
        int profileColor3 = ContextCompat.getColor(getApplicationContext(), R.color.profile3);
        int profileColor4 = ContextCompat.getColor(getApplicationContext(), R.color.profile4);

        // Create a color filter with the specified tint color and mode (SRC_ATOP in this case)
        PorterDuffColorFilter colorFilter1 = new PorterDuffColorFilter(profileColor1, PorterDuff.Mode.SRC_ATOP);
        PorterDuffColorFilter colorFilter2 = new PorterDuffColorFilter(profileColor2, PorterDuff.Mode.SRC_ATOP);
        PorterDuffColorFilter colorFilter3 = new PorterDuffColorFilter(profileColor3, PorterDuff.Mode.SRC_ATOP);
        PorterDuffColorFilter colorFilter4 = new PorterDuffColorFilter(profileColor4, PorterDuff.Mode.SRC_ATOP);


        if (gender!=null){
            if (gender.equals("male") || gender.equals("Male")){
                imgUseAvatar.setImageResource(R.drawable.profile_av1);
            }else if (gender.equals("female") || gender.equals("Female")){
                imgUseAvatar.setImageResource(R.drawable.profile_av2);
            }
        }
        if (Objects.equals(profileId, "profile1")){
            imgUseAvatar.getBackground().setColorFilter(colorFilter1);
        }else if(Objects.equals(profileId, "profile2")){
            imgUseAvatar.getBackground().setColorFilter(colorFilter2);
        }else if(Objects.equals(profileId, "profile3")){
            imgUseAvatar.getBackground().setColorFilter(colorFilter3);
        }else if(Objects.equals(profileId, "profile4")){
            imgUseAvatar.getBackground().setColorFilter(colorFilter4);
        }

    }

}