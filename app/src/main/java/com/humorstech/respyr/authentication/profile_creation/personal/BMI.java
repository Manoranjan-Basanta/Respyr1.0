package com.humorstech.respyr.authentication.profile_creation.personal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.humorstech.respyr.Dialogs;
import com.humorstech.respyr.HTTP_URLS;
import com.humorstech.respyr.NetWork;
import com.humorstech.respyr.NetworkErrorSheet;
import com.humorstech.respyr.R;
import com.humorstech.respyr.StatusBarColor;
import com.humorstech.respyr.authentication.profile_creation.lifestyle.Water;
import com.humorstech.respyr.utills.LoginUtils;
import com.humorstech.respyr.utills.ProfileCreationData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.Header;

public class BMI extends AppCompatActivity {

    private TextView txtBmiScore, txtBmiMessage, txtBmiFooterTitle, txtBmiFooterSubTitle;
    private ImageView imgBmiGraph;
    private Button buttonNext;


    String loginId,userPhoneNumber,userName,userEmail,userGender,userDateOfBirth,userAge, userHeight, userWeight;

    private double bmi;
    private ShimmerFrameLayout shimmerFrameLayout;

    String[] bmiTitles = {
            "Strong",
            "Balance",
            "Progress",
            "Transform",
            "Revive"};

    String[] bmiSubTitles = {
            "Track and Achieve Your Ideal BMI",
            "Monitor Your BMI for Optimal Wellness",
            "Track Your BMI and Reach Your Goals",
            "Manage Your BMI and Unlock a Healthier You",
            "Harness the Power of BMI Tracking for Lasting Change"};

    private static final String TAG = "BMI";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        StatusBarColor statusBarColor = new StatusBarColor(BMI.this);
        statusBarColor.setColor(getResources().getColor(R.color.white));


        init();


    }

    private void init(){
        initVars();
        onClicks();
        getData();
    }

    private void getData(){
        SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);

        userName = sharedPreferences.getString(ProfileCreationData.NAME, "0");
        userEmail = sharedPreferences.getString(ProfileCreationData.EMAIL, "0");
        userGender = sharedPreferences.getString(ProfileCreationData.GENDER, "0");
        userDateOfBirth = sharedPreferences.getString(ProfileCreationData.DOB, "0");
        userAge = sharedPreferences.getString(ProfileCreationData.AGE, "0");
        userHeight = sharedPreferences.getString(ProfileCreationData.HEIGHT, "180.0");
        userWeight = sharedPreferences.getString(ProfileCreationData.WEIGHT, "65");


        SharedPreferences sharedPreferences2 = getSharedPreferences(LoginUtils.LOGIN_TITLE, Context.MODE_PRIVATE);
        loginId = sharedPreferences2.getString(LoginUtils.LOGIN_ID, "0");
        userPhoneNumber = sharedPreferences2.getString(LoginUtils.PHONE_NUMBER, "0");

        System.out.println("User login id: " + loginId);
        System.out.println("User Phone Number: " + userPhoneNumber);
        System.out.println("User Name: " + userName);
        System.out.println("User Email: " + userEmail);
        System.out.println("User Gender: " + userGender);
        System.out.println("User Date of Birth: " + userDateOfBirth);
        System.out.println("User Age: " + userAge);
        System.out.println("User Height: " + userHeight);
        System.out.println("User Weight: " + userWeight);


        shimmerFrameLayout.startShimmer();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                displayBmi();
            }
        },2500);
    }
    private void initVars(){
        txtBmiScore=findViewById(R.id.txt_bmi_score);
        txtBmiMessage=findViewById(R.id.txt_bmi_message);
        imgBmiGraph=findViewById(R.id.img_bmi_graph);
        buttonNext=findViewById(R.id.button_next);
        txtBmiFooterTitle=findViewById(R.id.txt_bmi_footer_title);
        txtBmiFooterSubTitle=findViewById(R.id.txt_bmi_footer_sub_title);
        buttonNext=findViewById(R.id.button_next);
        shimmerFrameLayout=findViewById(R.id.shimmerLayout);
    }

    private void onClicks(){
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bmi!=0){
                    if (NetWork.isNetworkAvailable(BMI.this)){
                        addPersonalInfo();
                    }else{
                        NetworkErrorSheet.show(BMI.this, BMI.this, Water.class);
                    }
                }
            }
        });
    }

    private double findBMI(double weight, double height){
        return  weight / ((height/100) * (height/100));
    }

    private void displayBmi(){

        double height = Double.parseDouble(userHeight);
        double weight = Double.parseDouble(userWeight);
        bmi = findBMI(weight,height);
        txtBmiScore.setText(String.valueOf((int)roundTwoDecimals(bmi)));

        if (bmi < 18.5){
            txtBmiMessage.setText("Under Weight");
            txtBmiMessage.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.under_weight));
            imgBmiGraph.setImageResource(R.drawable.bmi1);
            txtBmiMessage.setTextColor(getResources().getColor(R.color.white));
            txtBmiFooterTitle.setText(bmiTitles[0]);
            txtBmiFooterSubTitle.setText(bmiSubTitles[0]);

        }else if (bmi >= 18.5 && bmi <= 24.9 ){
            txtBmiMessage.setText("Normal");
            txtBmiMessage.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.normal));
            imgBmiGraph.setImageResource(R.drawable.bmi2);
            txtBmiMessage.setTextColor(getResources().getColor(R.color.white));
            txtBmiFooterTitle.setText(bmiTitles[1]);
            txtBmiFooterSubTitle.setText(bmiSubTitles[1]);
        }else if(bmi >= 25 && bmi <= 29.9){
            txtBmiMessage.setText("Over Weight");
            txtBmiMessage.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.overweight));
            imgBmiGraph.setImageResource(R.drawable.bmi3);
            txtBmiMessage.setTextColor(getResources().getColor(R.color.white));
            txtBmiFooterTitle.setText(bmiTitles[2]);
            txtBmiFooterSubTitle.setText(bmiSubTitles[2]);

        }else  if(bmi >= 30 && bmi <= 39.9) {
            txtBmiMessage.setText("Obese");
            txtBmiMessage.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.obese));
            imgBmiGraph.setImageResource(R.drawable.bmi4);
            txtBmiMessage.setTextColor(getResources().getColor(R.color.white));
            txtBmiFooterTitle.setText(bmiTitles[3]);
            txtBmiFooterSubTitle.setText(bmiSubTitles[3]);

        }else if(bmi > 40) {
            txtBmiMessage.setText("Morbidly Obese");
            txtBmiMessage.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.morbidly_obese));
            imgBmiGraph.setImageResource(R.drawable.bmi5);
            txtBmiMessage.setTextColor(getResources().getColor(R.color.white));
            txtBmiFooterTitle.setText(bmiTitles[4]);
            txtBmiFooterSubTitle.setText(bmiSubTitles[4]);
        }

        shimmerFrameLayout.hideShimmer();

    }

    double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("##.#");
        return Double.valueOf(twoDForm.format(d));
    }

    private void addPersonalInfo(){
        RequestParams params = new RequestParams();
        params.put("login_id", loginId);
        params.put("phone", userPhoneNumber);
        params.put("name", userName);
        params.put("email", userEmail);
        params.put("gender", userGender);
        params.put("dob", userDateOfBirth);
        params.put("age", userAge);
        params.put("height", userHeight);
        params.put("weight", userWeight);
        params.put("bmi", roundTwoDecimals(bmi));


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(HTTP_URLS.insertNewProfile,params,new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                Dialogs.showLoadingDialog(BMI.this, "Loading.....");


                System.out.println("Params" + params);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dialogs.hideLoadingDialog();
                        String response = new String(responseBody).trim();

                        if (response.contains("created")){

                            /// split string by $
                            String[] parts = response.split("\\$");

                            String action = parts[0];
                            String profileId = parts[1];


                            if(action.equals("created")){
                                try {

                                    // store gender id for profile creation
                                    SharedPreferences sharedPreferences = getSharedPreferences(ProfileCreationData.TITLE, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(ProfileCreationData.PROFILE_ID, String.valueOf(profileId));
                                    editor.apply();


                                    Intent intent = new Intent(getApplicationContext(), Water.class);
                                    startActivity(intent);

                                }catch (Exception e){
                                    Log.d(TAG, e.getMessage());
                                }
                            }
                        }

                        else{
                            Toast.makeText(BMI.this, "Something went wrong../nPlease try again later.", Toast.LENGTH_SHORT).show();
                        }

                    }
                },2000);



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }


            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });




    }

}