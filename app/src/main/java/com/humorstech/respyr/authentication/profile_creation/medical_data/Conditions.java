package com.humorstech.respyr.authentication.profile_creation.medical_data;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.HTTP_URLS;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.ProgressDots;
import com.humorstech.respyr.authentication.profile_creation.ProfileCreationSuccess;
import com.humorstech.respyr.utills.ActiveProfile;
import com.humorstech.respyr.utills.LoginUtils;
import com.humorstech.respyr.utills.ProfileCreationData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class Conditions extends AppCompatActivity {

    private CheckBox chkNone, chkDia,chkLipid, chkKidney, chkLiver, chkLungs;

    private Button buttonNext;
    private String bloodTest, profileId, fastingBloodSugar, loginId,name;

    private boolean ch1;
    private boolean ch2;
    private boolean ch3;
    private boolean ch4;
    private boolean ch5;


    StringBuilder stringBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condtions);

        StatusBarColor statusBarColor = new StatusBarColor(Conditions.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));


        SharedPreferences sharedPreferences2 = getSharedPreferences(LoginUtils.LOGIN_TITLE, Context.MODE_PRIVATE);
        loginId = sharedPreferences2.getString(LoginUtils.LOGIN_ID, "0");

        SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);
        profileId = sharedPreferences.getString(ProfileCreationData.PROFILE_ID, "0");
        bloodTest = sharedPreferences.getString(ProfileCreationData.BLOOD_TEST, "0");
        fastingBloodSugar = sharedPreferences.getString(ProfileCreationData.SUGAR_LEVEL, "0");
        name = sharedPreferences.getString(ProfileCreationData.NAME, "0");

        init();
    }


    private void init(){
        initVars();
        checkBoxListener();
        onClicks();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initVars(){
        // dots
        LinearLayout linearLayout =findViewById(R.id.dotLayout);
        ProgressDots.generateDotIndicators(getApplicationContext(),linearLayout, 4,2);

        // checkboxes
        chkNone=findViewById(R.id.chk_none);
        chkDia=findViewById(R.id.chk_dia);
        chkLipid=findViewById(R.id.chk_lungs);
        chkKidney=findViewById(R.id.chk_lipid);
        chkLiver=findViewById(R.id.chk_kidney);
        chkLungs=findViewById(R.id.chk_liver);

        // buttons
        buttonNext=findViewById(R.id.button_next);

    }
    private void checkBoxListener(){

        chkNone.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked){
                chkDia.setChecked(false);
                chkLipid.setChecked(false);
                chkKidney.setChecked(false);
                chkLiver.setChecked(false);
                chkLungs.setChecked(false);

                stringBuilder.setLength(0);
            }else{
                ch1=false;
                ch2=false;
                ch3=false;
                ch4=false;
                ch5=false;
            }

        });


        chkDia.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                chkNone.setChecked(false);
                ch1=true;
            }else{
                ch1=false;
            }
        });

        chkLipid.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                chkNone.setChecked(false);
                ch2=true;
            }else{
                ch2=false;
            }
        });
        chkKidney.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                chkNone.setChecked(false);
                ch3=true;
            }else{
                ch3=false;
            }
        });
        chkLiver.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                chkNone.setChecked(false);
                ch4=true;
            }else{
                ch4=false;
            }
        });
        chkLungs.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                chkNone.setChecked(false);
                ch5=true;
            }else{
                ch5=false;
            }
        });

    }

    private void onClicks(){
        buttonNext.setOnClickListener(v -> {
            stringBuilder.setLength(0);

            if (ch1){
                stringBuilder.append("Diabetes ,");
            }
            if (ch2){
                stringBuilder.append("Lungs Issue ,");
            }
            if (ch3){
                stringBuilder.append("Lipid Issue ,");
            }

            if (ch4){
                stringBuilder.append("Kidney Issue ,");
            }

            if (ch5){
                stringBuilder.append("Liver Issue");
            }

            if (!loginId.equals("0") || !profileId. equals("0")){
                addBloodReportToServer();
            }else{
                Toast.makeText(getApplicationContext(), "not ok", Toast.LENGTH_SHORT).show();
            }

        });
    }




    private void addBloodReportToServer(){

        RequestParams params = new RequestParams();
        params.put("login_id",loginId);
        params.put("profile_id",profileId);
        params.put("diabetic","-");
        params.put("diabetic_values",fastingBloodSugar);
        params.put("report_age",bloodTest);
        params.put("conditions",String.valueOf(stringBuilder));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HTTP_URLS.addBloodReport,params, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialogs.showLoadingDialog(Conditions.this, "Please wait....");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String response = new String(responseBody).trim();
                Dialogs.hideLoadingDialog();
                if (response.equals("inserted")) {

                    SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    SharedPreferences sharedPreferences2 = getSharedPreferences(LoginUtils.LOGIN_TITLE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                    editor2.putString(LoginUtils.LOGIN_ID, String.valueOf(loginId));
                    editor2.putString(LoginUtils.PROFILE_ID, String.valueOf(profileId));
                    editor2.putString(LoginUtils.USER_NAME, String.valueOf(name));
                    editor2.apply();


                    SharedPreferences sharedPreferences3 = getSharedPreferences(ActiveProfile.Title, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor3 = sharedPreferences3.edit();
                    editor3.putString(ActiveProfile.LOGIN_ID, String.valueOf(loginId));
                    editor3.putString(ActiveProfile.PROFILE_ID, String.valueOf(profileId));
                    editor3.putString(ActiveProfile.NAME, String.valueOf(name));
                    editor3.apply();


                    Intent intent = new Intent(getApplicationContext(), ProfileCreationSuccess.class);
                    startActivity(intent);
                    finish();


                }else{
                    Toast.makeText(Conditions.this, response, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Dialogs.hideLoadingDialog();
            }

        });
    }


}